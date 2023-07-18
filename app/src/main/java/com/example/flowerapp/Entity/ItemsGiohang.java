package com.example.flowerapp.Entity;

import java.io.Serializable;

public class ItemsGiohang implements Serializable {
    public ItemsGiohang()
    {
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

    public int soluongmuahang;
    public String nameflower;
    public String imgFlower;
    public String id_flower;
    public float price;

    public String getId_flower() {
        return id_flower;
    }

    public void setId_flower(String id_flower) {
        this.id_flower = id_flower;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public ItemsGiohang(String id_flower, String nameflower, int soluongmuahang, float price, String imgFlower)
    {
        this.id_flower = id_flower;
        this.nameflower = nameflower;
        this.soluongmuahang = soluongmuahang;
        this.price = price;
        this.imgFlower = imgFlower;
    }
}
