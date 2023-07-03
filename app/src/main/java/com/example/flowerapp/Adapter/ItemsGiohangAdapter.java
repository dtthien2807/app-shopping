package com.example.flowerapp.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.R;

import java.util.List;

public class ItemsGiohangAdapter extends RecyclerView.Adapter<ItemsGiohangAdapter.ItemsViewHolder> {
    private List<ItemsGiohang> lst_Flower;
    private Context context;
    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new ItemsViewHolder(view);
    }

    public void setData(List<ItemsGiohang> lstProduct)
    {
        this.lst_Flower=lstProduct;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        ItemsGiohang product= lst_Flower.get(position);
        if(product==null)
            return;
        //gắn dữ liệu url cho img trong items
        Resources resources = context.getResources();
        String imageResourceName = product.getImgFlower();
        int imageResourceId = resources.getIdentifier(imageResourceName, "drawable", context.getPackageName());
        holder.imgFlower.setImageResource(imageResourceId);
        holder.nameflower.setText(product.getNameflower());
        holder.soluongmuahang.setText(String.valueOf(product.getSoluongmuahang()));
    }

    @Override
    public int getItemCount() {
        if(lst_Flower!=null)
        {
            return lst_Flower.size();
        }
        return 0;
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgFlower;
        private TextView nameflower;
        private TextView soluongmuahang;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFlower= itemView.findViewById(R.id.img_itemsgiohang);
            nameflower= itemView.findViewById(R.id.tv_namesp);
            soluongmuahang=itemView.findViewById(R.id.tv_soluongitems);
        }
    }
}
