package com.example.flowerapp.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Flower implements Serializable {
    private String url;
    private  String name;
    private String description;
    private  float price;
    private float quantity;
    private String created_at;

    public String getId_flower() {
        return id_flower;
    }

    public void setId_flower(String id_flower) {
        this.id_flower = id_flower;
    }

    private String id_flower;

    public Flower(String id_flower, String url, String name, String description, float price, float quantity, String created_at, boolean status) {
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
        this.id_flower="";
        this.url = "";
        this.name = "name";
        this.description = "description";
        this.price = 0;
        this.quantity = 0;
        this.created_at = "" ;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
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
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("quantity", quantity);
        result.put("price", price);
        result.put("status", status);
        result.put("url", url);
        return result;
    }
}
