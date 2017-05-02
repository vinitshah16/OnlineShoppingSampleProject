package com.example.vinitshah.onlineshoppingsampleproject.Model;

import android.text.TextUtils;

/**
 * Created by vinit.shah on 24/04/2017.
 */
public class ProductModel {
    String name, imageUrl, category;
    String pno, price;

    public ProductModel(String pno, String name, String price, String imageUrl, String category) {
        this.pno = pno;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public ProductModel() {
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public double getPriceInDouble() {
        try {
            return Float.parseFloat(price);
        } catch (Exception e) {
            return 0;
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
