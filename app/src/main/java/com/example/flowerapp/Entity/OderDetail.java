package com.example.flowerapp.Entity;

public class OderDetail {
    public int getId_orderdetail() {
        return id_orderdetail;
    }

    public void setId_orderdetail(int id_orderdetail) {
        this.id_orderdetail = id_orderdetail;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private int id_orderdetail;
    private int id_order;
    private int id_product;
    private int quantity;
    private float price;
    private String note;
}
