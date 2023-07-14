package com.example.flowerapp.Entity;

import java.io.Serializable;

public class ItemsGiohang implements Serializable {
    public ItemsGiohang()
    {
        this.soluongmuahang= Integer.valueOf(30);
        this.imgFlower="hoahuongduong";
        this.nameflower="hoa hướng dương";
        this.fDongiamua=Float.valueOf(300000);
        this.id_flower="3234";
    }
    public int getSoluongmuahang() {
        return soluongmuahang;
    }

    public void setSoluongmuahang(int soluongmuahang) {
        this.soluongmuahang = soluongmuahang;
    }

    public String getNameflower() {
        return nameflower;
    }

    public void setNameflower(String nameflower) {
        this.nameflower = nameflower;
    }

    public String getImgFlower() {
        return imgFlower;
    }

    public void setImgFlower(String imgFlower) {
        this.imgFlower = imgFlower;
    }

    private int soluongmuahang;
    private String nameflower;
    private String imgFlower;

    public Float getfDongiamua() {
        return fDongiamua;
    }

    public void setfDongiamua(Float fDongiamua) {
        this.fDongiamua = fDongiamua;
    }

    private  Float fDongiamua;
    public String getId_flower() {
        return id_flower;
    }

    public void setId_flower(String id_flower) {
        this.id_flower = id_flower;
    }

    private  String id_flower;
}
