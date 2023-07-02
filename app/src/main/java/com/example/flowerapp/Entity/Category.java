package com.example.flowerapp.Entity;

import java.util.List;

public class Category {
    public Category(String name_catetory, List<Flower> lstFlower) {
        this.name_catetory = name_catetory;
        this.lstFlower = lstFlower;
    }
    public Category() {
        this.name_catetory = "demo";
        this.lstFlower = lstFlower;
    }
    private String name_catetory;
        private List<Flower> lstFlower;
    public List<Flower> getLstImage() {
        return lstFlower;
    }

    public void setLstImage(List<Flower> lstFlower) {
        this.lstFlower = lstFlower;
    }



        public String getName_catetory() {
            return name_catetory;
        }

        public void setName_catetory(String name_catetory) {
            this.name_catetory = name_catetory;
        }

}
