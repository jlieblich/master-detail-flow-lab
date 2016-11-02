package ly.generalassemb.drewmahrt.shoppinglistdetailview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements DetailFragment.ShoppingItemListener{

    public static final String ITEM_ID_KEY = "itemIdKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int selectedItemId = getIntent().getIntExtra(ITEM_ID_KEY, -1);
        if(selectedItemId == -1) {
            finish();
        }

        DetailFragment fragment = DetailFragment.newInstance(selectedItemId);
        getSupportFragmentManager().beginTransaction().add(R.id.detail_fragment_container, fragment).commit();
    }

    @Override
    public void shoppingItemInteraction(int itemId) {
    }
}
