package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.flowerapp.Entity.Order;

import java.util.List;

public class ItemsOrderAdapter extends ArrayAdapter {
    private Activity context;
    private List<Order> orderList;
    public ItemsOrderAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
    }
}
