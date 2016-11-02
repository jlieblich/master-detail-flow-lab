package ly.generalassemb.drewmahrt.shoppinglistdetailview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ly.generalassemb.drewmahrt.shoppinglistdetailview.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity implements ShoppingListAdapter.OnShoppingItemSelected,
        DetailFragment.ShoppingItemListener {
    private ShoppingSQLiteOpenHelper mHelper;
    private List<ShoppingItem> mShoppingItems;
    private ShoppingListAdapter mAdapter;
    private boolean mUseTwoPanes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mHelper = ShoppingSQLiteOpenHelper.getInstance(this);
        mShoppingItems = mHelper.getShoppingList();

        //Check if layout contains detail fragment
        mUseTwoPanes = (findViewById(R.id.detail_fragment_container)!= null);
        //Setup the RecyclerView
        RecyclerView shoppingListRecyclerView = (RecyclerView) findViewById(R.id.shopping_list_recyclerview);


        shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ShoppingListAdapter(mShoppingItems, this);
        shoppingListRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void itemSelected(int id) {
        if(mUseTwoPanes) {
            DetailFragment fragment = DetailFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, fragment).commit();
        } else {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.ITEM_ID_KEY, id);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShoppingItems.clear();
        mShoppingItems.addAll(mHelper.getShoppingList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void shoppingItemInteraction(int itemId) {
        mAdapter.notifyDataSetChanged();
    }
}
