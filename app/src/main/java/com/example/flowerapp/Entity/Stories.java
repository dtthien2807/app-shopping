package com.example.flowerapp.Entity;

import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;

public class Stories {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTenImg() {
        return tenImg;
    }

    public void setTenImg(String tenImg) {
        this.tenImg = tenImg;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDoituong() {
        return doituong;
    }

    public void setDoituong(String doituong) {
        this.doituong = doituong;
    }

    public String getDip() {
        return dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }

    public String getMoitruong() {
        return moitruong;
    }

    public void setMoitruong(String moitruong) {
        this.moitruong = moitruong;
    }

    private String id;
    private String img;
    private String tenImg;
    private String mota;
    private String doituong;
    private String dip;
    private String moitruong;

    public Stories(){}

    public Stories(String id, String img, String tenImg, String mota, String doituong, String dip, String moitruong){
        this.id= id;
        this.img= img;
        this.tenImg= tenImg;
        this.mota= mota;
        this.doituong= doituong;
        this.dip= dip;
        this.moitruong= moitruong;
    }
}
