package com.example.flowerapp.Entity;

public class Image {
    public int getId_Image() {
        return id_Image;
    }

    public void setId_Image(int id_Image) {
        this.id_Image = id_Image;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private int id_Image;
    private int target_id;
    private String url;
}
