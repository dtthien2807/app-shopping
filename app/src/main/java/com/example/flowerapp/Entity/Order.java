package com.example.flowerapp.Entity;

import java.util.Date;
import java.util.List;

public class Order {

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Date getOrder_ship_date() {
        return order_ship_date;
    }

    public void setOrder_ship_date(Date order_ship_date) {
        this.order_ship_date = order_ship_date;
    }

    public Date getShip_date() {
        return ship_date;
    }

    public void setShip_date(Date ship_date) {
        this.ship_date = ship_date;
    }

    public List<Flower> getLstItemsGiohang() {
        return items;
    }

    public void setLstItemsGiohang(List<Flower> lstItemsGiohang) {
        this.items = lstItemsGiohang;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    private String id_order;

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    private String Note;
    private float price;
    private boolean status;
    private Date create_at;
    private Date order_ship_date;
    private Date ship_date;
    private List<Flower> items;
    private int id_user;

    public float getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(float total_bill) {
        this.total_bill = total_bill;
    }

    private float total_bill;
}
