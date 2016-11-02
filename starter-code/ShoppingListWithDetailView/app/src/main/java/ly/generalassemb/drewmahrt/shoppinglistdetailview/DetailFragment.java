package ly.generalassemb.drewmahrt.shoppinglistdetailview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private int mShoppingId;

    private ShoppingItemListener mListener;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(int mShoppingId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, mShoppingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShoppingId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ShoppingSQLiteOpenHelper helper = ShoppingSQLiteOpenHelper.getInstance(getContext());
        ShoppingItem item = helper.getShoppingItemById(mShoppingId);

        TextView nameView = (TextView)view.findViewById(R.id.detail_name);
        TextView descView = (TextView)view.findViewById(R.id.detail_description);
        TextView priceView = (TextView)view.findViewById(R.id.detail_price);
        TextView itemType = (TextView)view.findViewById(R.id.detail_category);

        String name = item.getName();
        String desc = item.getDescription();
        String price = item.getPrice();
        String type = item.getType();

        nameView.setText(name);
        descView.setText(desc);
        priceView.setText(price);
        itemType.setText(type);

        mListener.shoppingItemInteraction(mShoppingId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShoppingItemListener) {
            mListener = (ShoppingItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ShoppingItemListener {
        void shoppingItemInteraction(int itemId);
    }
}
