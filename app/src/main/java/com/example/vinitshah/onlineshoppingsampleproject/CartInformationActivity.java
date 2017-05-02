package com.example.vinitshah.onlineshoppingsampleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vinitshah.onlineshoppingsampleproject.Adapters.Cart_Adapter;
import com.example.vinitshah.onlineshoppingsampleproject.Adapters.Products_Adapter;
import com.example.vinitshah.onlineshoppingsampleproject.Model.CartModel;
import com.example.vinitshah.onlineshoppingsampleproject.Model.ProductModel;
import com.example.vinitshah.onlineshoppingsampleproject.Utilities.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class CartInformationActivity extends AppCompatActivity {

    ListView cartListView;
    Cart_Adapter adapt;
    TextView txtMessage;
    TextView txtSumOfAllProduct;
    Double value = 0.0;

    private DBHelper myDb;
    private List<CartModel> cartModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_information);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Cart");

        cartListView = (ListView) findViewById(R.id.list_cart);
        txtMessage = (TextView) findViewById(R.id.txtMessaage);
        txtSumOfAllProduct = (TextView) findViewById(R.id.txtTotal);
        myDb = new DBHelper(this);
        showOrder();
    }

    private void showOrder() {
        cartModelList = myDb.getAllOrder();

        if (cartModelList != null) {
            Log.d("After Cart Model Size", "" + cartModelList.size());
            adapt = new Cart_Adapter(this, cartModelList);
            cartListView.setAdapter(adapt);
            adapt.setListener(new Cart_Adapter.Listener() {
                @Override
                public void onClick(int position) {
                    myDb.deleteOrder(position);
                    cartModelList.clear();
                    cartModelList.addAll(myDb.getAllOrder());
                    adapt.notifyDataSetChanged();
                    txtSumOfAllProduct.setText("Total Amount is : " + myDb.getTotal());
                }

                @Override
                public void onClick(String pno, String pname, double price, String image, double qty) {
//                    Intent i = new Intent(getApplicationContext(), ProductInformation.class);
//
//                Log.d("After CLICKED",""+pname);
//                i.putExtra("pno",pno);
//                i.putExtra("name", pname);
//                i.putExtra("price", price);
//                i.putExtra("image",image);
//                i.putExtra("Qty",qty);
//
//
//                    startActivity(i);
                }
            });

            if (cartModelList.size() == 0) {
                txtMessage.setVisibility(View.VISIBLE);
                cartListView.setVisibility(View.GONE);
                txtSumOfAllProduct.setVisibility(View.GONE);
            } else {
                txtSumOfAllProduct.setVisibility(View.VISIBLE);
                cartListView.setVisibility(View.VISIBLE);
                txtMessage.setVisibility(View.GONE);
                txtSumOfAllProduct.setText("Total Amount is : " + myDb.getTotal());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapt.notifyDataSetChanged();
    }
}
