package com.example.vinitshah.onlineshoppingsampleproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vinitshah.onlineshoppingsampleproject.Model.ProductModel;
import com.example.vinitshah.onlineshoppingsampleproject.Utilities.DBHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
        //ConnectivityReceiver.ConnectivityReceiverListener {

    List<String> name, price, imageUrl, category;
    private DBHelper myDb;
    Bundle bundle;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DBHelper(this);
        bundle = new Bundle();
        fm=getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shopping_cart));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CartInformationActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(isNetworkAvailable()) {
            new fetchData().execute();
            bundle.putString("category","All");
            Products_ListFragment list = new Products_ListFragment();
            list.setArguments(bundle);
            clearFragmentStack();
            fm.beginTransaction()
                    .replace(R.id.content_main,list)
                    .addToBackStack(getSupportFragmentManager().toString())
                    .commit();
        }
        else
            showSnack(false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class fetchData extends AsyncTask<String,String,String>{

        StringBuilder sb;
        String line;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            name = new ArrayList<String>();
            price = new ArrayList<String>();
            imageUrl = new ArrayList<String>();
            category = new ArrayList<String>();
            sb = new StringBuilder();
        }

        @Override
        protected String doInBackground(String... params) {
            try
            {
                URL url = new URL("https://salty-plateau-1529.herokuapp.com/product_list.php");
                URLConnection conn = url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = rd.readLine()) != null) {
                    if(!(line.trim().toString()=="")){
                        JSONArray obj=new JSONArray(line);
                        for (int i=0;i<obj.length();i++)
                        {
                            JSONObject ob = obj.getJSONObject(i);
                            name.add(ob.get("name").toString());
                            price.add(ob.get("price").toString());
                            imageUrl.add(ob.get("imageUrl").toString());
                            category.add(ob.get("category").toString());
                        }
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return line;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            myDb.deleteData("Product");
            myDb.insertProduct(name,price,imageUrl,category);

            List<ProductModel> listModel = myDb.getAllProduct("Furniture");
            for (ProductModel list : listModel)
                Log.d("After USERLIST",list.getPno()+" : "+list.getName()+" : "+list.getPrice());

        }
    }
    
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_furniture) {
            Log.d("After Furniture Clicked","Furniture");
            bundle.putString("category","Furniture");
        } else if (id == R.id.nav_electronics) {
            bundle.putString("category","Electronics");
        } else if (id == R.id.nav_all) {
            bundle.putString("category","All");
        }
        Products_ListFragment list = new Products_ListFragment();
        list.setArguments(bundle);
        clearFragmentStack();
        fm.beginTransaction()
                .replace(R.id.content_main,list)
                .addToBackStack(getSupportFragmentManager().toString())
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clearFragmentStack(){
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private boolean showSnack(boolean isConnected) {
        if (!isConnected) {
            String message = "Sorry! Not connected to internet";
            int color = Color.RED;

            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
