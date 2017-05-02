package com.example.vinitshah.onlineshoppingsampleproject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vinitshah.onlineshoppingsampleproject.Adapters.Products_Adapter;
import com.example.vinitshah.onlineshoppingsampleproject.Model.ProductModel;
import com.example.vinitshah.onlineshoppingsampleproject.Utilities.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Products_ListFragment extends Fragment {

    ListView productListView;
    Products_Adapter adapt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String category = getArguments().getString("category");
        getActivity().setTitle(category);
        View rootView= inflater.inflate(R.layout.activity_products_list, container, false);

        productListView = (ListView)rootView.findViewById(R.id.list_product);
        myDb = new DBHelper(this.getActivity());
        showProducts(category);

        return rootView;
    }

    private List<ProductModel> productModelList;
    private DBHelper myDb;

    private void showProducts(String cat) {
        productModelList = myDb.getAllProduct(cat);
        if(productModelList != null) {
            adapt = new Products_Adapter(this.getActivity(), productModelList);
            productListView.setAdapter(adapt);
        }
    }
}
