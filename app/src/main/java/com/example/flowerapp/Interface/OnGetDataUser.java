package com.example.flowerapp.Interface;

import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Entity.User;

import java.util.List;

public interface OnGetDataUser {
    void onSuccess(List<ItemsGiohang> user);
    void onFailure(Exception e);
}