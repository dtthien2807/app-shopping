package com.example.flowerapp.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    private String id_category;
    private String name_category;
    private List<Flower> lstFlower;
    public Category() {}
    public Category(String id, String name, List<Flower> lstFlower) {
        this.id_category = id;
        this.name_category = name;
        this.lstFlower = lstFlower;
    }
    public List<Flower> getLstImage() {
        return lstFlower;
    }

    public void setLstImage(List<Flower> lstFlower) {
        this.lstFlower = lstFlower;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public String getId_category(){return id_category;}
    public void setId_category(String id_category) {
        this.id_category = id_category;
    }
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name_category", name_category);

        return result;
    }

}
