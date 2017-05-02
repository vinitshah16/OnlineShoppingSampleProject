package com.example.vinitshah.onlineshoppingsampleproject.Model;

/**
 * Created by vinit.shah on 26/04/2017.
 */

public class CartModel {
    Double pno,qty,total,price;
    String pName,imageUrl;
    Integer orderNo;

    public CartModel(Integer orderNo,Double pno, Double qty, Double total, Double price, String pName, String imageUrl) {
        this.orderNo = orderNo;
        this.pno = pno;
        this.qty = qty;
        this.total = total;
        this.price = price;
        this.pName = pName;
        this.imageUrl = imageUrl;
    }

    public CartModel() {

    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Double getPno() {
        return pno;
    }

    public void setPno(Double pno) {
        this.pno = pno;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
