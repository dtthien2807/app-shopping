package com.example.flowerapp.Entity;

import java.io.Serializable;

public class ItemsGiohang implements Serializable {
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
}
