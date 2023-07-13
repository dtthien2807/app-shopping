package com.example.flowerapp.Entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getOrder_ship_date() {
        return order_ship_date;
    }

    public void setOrder_ship_date(String order_ship_date) {
        this.order_ship_date = order_ship_date;
    }

    public String getShip_date() {
        return ship_date;
    }

    public void setShip_date(String ship_date) {
        this.ship_date = ship_date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    private String id_order;

    public String getNote() {
        return Note;
    }

    public void setItems(List<ItemsGiohang> items) {
        Items = items;
    }

    public List<ItemsGiohang> getItems() {
        return Items;
    }

    public void setNote(String note) {
        Note = note;
    }

    private String Note;
    private float price;
    private int status;
    private String create_at;
    private String order_ship_date;
    private String ship_date;
    private List<ItemsGiohang> Items;
    private String id_user;
    private String name_user;
    private String number_phone;
    private String address_user;
    public Feedback feedbacks;

    public float getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(float total_bill) {
        this.total_bill = total_bill;
    }

    private float total_bill;

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }

    public String getAddress_user() {
        return address_user;
    }

    public void setAddress_user(String address_user) {
        this.address_user = address_user;
    }

    public Feedback getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Feedback feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Order(String id_order, String name_user, String number_phone, String note, String id_user,
                 String address_user, String order_ship_date, String ship_date, Float total_bill, int status,
                 String create_at, Feedback feedbacks, List<ItemsGiohang> items){
        this.address_user = address_user;
        this.id_order = id_order;
        this.name_user = name_user;
        this.number_phone = number_phone;
        this.Note = note;
        this.id_user = id_user;
        this.order_ship_date = order_ship_date;
        this.ship_date = ship_date;
        this.total_bill = total_bill;
        this.status = status;
        this.create_at = create_at;
        this.feedbacks= feedbacks;
        this.Items = items;
    }
    public Order(){}

    public int getStatus() {
        return status;
    }
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", status);
        return result;
    }
}
