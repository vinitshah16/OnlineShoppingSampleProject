package com.example.vinitshah.onlineshoppingsampleproject.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vinitshah.onlineshoppingsampleproject.Model.CartModel;
import com.example.vinitshah.onlineshoppingsampleproject.Model.ProductModel;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinit.shah on 28/02/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Assignment";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE if not exists Product " +
            "(PNo double NOT NULL PRIMARY KEY, PName varchar(100) ,Price double ,ImageUrl varchar(500) ," +
            "Category varchar(50))";

    private static final String CREATE_TABLE_ORDER = "CREATE TABLE if not exists Orders (OrderNo integer PRIMARY KEY" +
            " AutoIncrement, PNo double,Qty double, Total double)";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertProduct(List<String> name, List<String> price, List<String> imageUrl, List<String> category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i=0;i<name.size();i++)
        {
            //Log.d("After INSIDE LOOP",per_no[i]+" - "+per_name[i]);
            contentValues.put("PNo",i+1);
            contentValues.put("PName", name.get(i));
            contentValues.put("Price", price.get(i));
            contentValues.put("ImageUrl", imageUrl.get(i));
            contentValues.put("Category", category.get(i));
//            contentValues.put("Cart", 0 );
            db.insertWithOnConflict("Product", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        return true;
    }

    public boolean insertOrder(Double pno, Double qty, Double total)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
            //contentValues.put("OrderNo",i+1);
            contentValues.put("Pno", pno);
            contentValues.put("Qty", qty);
            contentValues.put("Total", total);
            db.insertWithOnConflict("Orders", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }

    public List<ProductModel> getAllProduct(String category)
    {
        List<ProductModel> list = new ArrayList<ProductModel>();
        String selectQuery="";
        if(category == "All")
            selectQuery = "Select * from Product";
        else
            selectQuery = "Select * from Product where Category ='"+category+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setPno(c.getString(0));
                productModel.setName(c.getString(1));
                productModel.setPrice(c.getString(2));
                productModel.setImageUrl(c.getString(3));
                productModel.setCategory(c.getString(4));
//                productModel.setCart(c.getString(5));
                list.add(productModel);
            }while (c.moveToNext());
        }
        return list;
    }

    public List<CartModel> getAllOrder()
    {
        List<CartModel> list = new ArrayList<CartModel>();
        String selectQuery="";
        selectQuery = "Select o.OrderNo,p.PNo, p.PName,p.Price,p.ImageUrl,o.Qty,o.total from Orders o, Product p where o.PNo = p.PNo";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                CartModel cartModel = new CartModel();
                cartModel.setOrderNo(c.getInt(0));
                cartModel.setPno(c.getDouble(1));
                cartModel.setpName(c.getString(2));
                cartModel.setPrice(c.getDouble(3));
                cartModel.setImageUrl(c.getString(4));
                cartModel.setQty(c.getDouble(5));
                cartModel.setTotal(c.getDouble(6));
                list.add(cartModel);
            }while (c.moveToNext());
        }
        return list;
    }

    public ProductModel getSelectedData(String name, String dob) {
        ProductModel productModel = new ProductModel();
        //Log.d("Exception OCcured",dob);
        String query = "SELECT * FROM Product WHERE PName = '"+name+"')";
        //Log.d("Exception Query",query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        if(c!=null) {
            c.moveToFirst();
            productModel.setName(c.getString(0));
            productModel.setPrice(c.getString(1));
            productModel.setImageUrl(c.getString(2));
            productModel.setCategory(c.getString(3));
        }
        return productModel;
    }

    public boolean deleteData(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name,null,null);
        //Log.d("After Data deleted","DELETED");
        return true;
    }

    public void deleteOrder(Integer orderNo) {
        //Log.d("After INSIDE delete order",""+pno+"-"+qty+"-"+total);
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM Orders WHERE orderNo ="+orderNo);
        //database.close();
    }

    public Integer getTotal() {
        String query = "SELECT SUM(Total) FROM Orders ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
//        Cursor cur = db.rawQuery("SELECT SUM(myColumn) FROM myTable", null);
        if(c.moveToFirst())
        {
            return c.getInt(0);
        }
        return 0;
    }
}
