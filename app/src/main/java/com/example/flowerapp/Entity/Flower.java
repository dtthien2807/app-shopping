package com.example.flowerapp.Entity;

import java.io.Serializable;
import java.util.Date;

public class Flower implements Serializable {
    private String url;
    private  String name;
    private String description;
    private  float price;
    private float quantity;
    private Date created_at;

    public String getId_flower() {
        return id_flower;
    }

    public void setId_flower(String id_flower) {
        this.id_flower = id_flower;
    }

    private String id_flower;

    public Flower(String id_flower, String url, String name, String description, float price, float quantity, Date created_at, boolean status) {
        this.id_flower = id_flower;
        this.url = url;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.status = status;
    }
    public Flower() {
        this.url = "";
        this.name = "name";
        this.description = "description";
        this.price = 0;
        this.quantity = 0;
        this.created_at = new Date() ;
        this.status = true;
    }
    private boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
