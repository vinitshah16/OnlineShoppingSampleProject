package com.example.vinitshah.onlineshoppingsampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinitshah.onlineshoppingsampleproject.Utilities.DBHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ProductInformation extends AppCompatActivity {

    String productName;
    String pno;
    double price;
    String imageUrl;
    String category;
    ImageView imageView;
    TextView txtPrice,txtTotal,txtProductValue;
    EditText etQty;
    Button btnAddToCart;
    private DBHelper myDb;
    double qty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Product Information");

        myDb = new DBHelper(this);
        pno = this.getIntent().getStringExtra("pno");
        productName = this.getIntent().getExtras().getString("name");
        price = this.getIntent().getDoubleExtra("price", 0);
        imageUrl = this.getIntent().getExtras().getString("image");
        qty = getIntent().getDoubleExtra("Qty",1.0);

        //category = this.getIntent().getExtras().getString("category");

        imageView = (ImageView) findViewById(R.id.productImage);
        txtPrice = (TextView) findViewById(R.id.txtProductPrice);
        etQty = (EditText) findViewById(R.id.etProductQty);
        txtTotal = (TextView) findViewById(R.id.txtProductTotal);
        txtProductValue = (TextView) findViewById(R.id.txtProductValue);
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shopping_cart));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CartInformationActivity.class);
                startActivity(i);
            }
        });

        final View getFocus = findViewById(R.id.getFocus);
        getFocus.requestFocus();

        txtPrice.setText("" + price);
        txtProductValue.setText(productName);
        etQty.setText(""+qty);
        double total = price * qty;
        txtTotal.setText("" + total);

        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.no_product)
                .error(R.drawable.no_product)
                .into(imageView);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etQty.getText().length()!=0) {
                    myDb.insertOrder(Double.parseDouble(pno), Double.parseDouble(etQty.getText().toString()),
                            Double.parseDouble(txtTotal.getText().toString()));

                    Snackbar.make(v, "Added to cart", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                    Snackbar.make(v, "Please Enter Quantity", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
        etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etQty.getText().length() == 0) {
//                    etQty.setText("");
                    txtTotal.setText("0");
                }
                else
                    txtTotal.setText(""+Float.valueOf(etQty.getText().toString())* Float.valueOf(txtPrice.getText().toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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

    private boolean loadImage() {
        Picasso.with(getApplicationContext())
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)//user this for offline support
                .placeholder(R.drawable.no_product)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext())
                                .load(imageUrl)
                                .placeholder(R.drawable.no_product)
                                .error(android.R.drawable.stat_notify_error)
                                .networkPolicy(NetworkPolicy.OFFLINE)//user this for offline support
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        //get error if image not loaded
                                    }
                                });
                    }
                });
        return true;
    }
}
