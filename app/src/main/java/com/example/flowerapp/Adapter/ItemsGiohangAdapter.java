package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Entity.Order;
import com.example.flowerapp.R;

import java.util.List;
public class ItemsGiohangAdapter extends ArrayAdapter {
    private Activity context;
    private List<ItemsGiohang> itemsGiohangList;
    int lastPosition = -1;
    public ItemsGiohangAdapter(@NonNull Activity context, List<ItemsGiohang> lstItemGiohang) {
        super(context, R.layout.item_giohang,lstItemGiohang);
        this.itemsGiohangList = lstItemGiohang;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View ListItem = inflater.inflate(R.layout.item_giohang,null,true);
        TextView tv_nameflower= ListItem.findViewById(R.id.tv_namesp);
        TextView tv_soluong = ListItem.findViewById(R.id.tv_soluongitems);
        ImageView tv_url = ListItem.findViewById(R.id.img_itemsgiohang);

        ItemsGiohang Giohang = itemsGiohangList.get(position);
        tv_nameflower.setText(Giohang.getNameflower());
        tv_soluong.setText(String.valueOf(Giohang.getSoluongmuahang()));
        Resources resources = context.getResources();
        String imageResourceName = Giohang.getImgFlower();
//        int imageResourceId = resources.getIdentifier(imageResourceName, "drawable", context.getPackageName());
//        tv_url.setImageResource(imageResourceId);

        Glide.with(context).load(imageResourceName).into(tv_url);

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_left);
        ListItem.startAnimation(animation);

        return  ListItem;
    }
}