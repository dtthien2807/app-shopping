package com.example.flowerapp.Interface;

import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.Flower;

import java.util.List;

public interface OnGetDataListener {
    void onSuccess(List<Category> lstCate);
    void onFailure(Exception e);
}