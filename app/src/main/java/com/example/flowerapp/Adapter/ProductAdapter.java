package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter {
    private Activity context;
    private List<Flower> flowerList;
    private IClickListener mIClickListener;
    public interface IClickListener {
        void onClickEditProduct(Flower flower);
        void onClickDeleteProduct(Flower flower);
    }
    public ProductAdapter(@NonNull Activity context, List<Flower> flowerList, IClickListener iClickListener) {
        super(context, R.layout.activity_listview_product, flowerList);
        this.context = context;
        this.flowerList = flowerList;
        this.mIClickListener = iClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListItem = inflater.inflate(R.layout.activity_listview_product,null,true);
        TextView txtId = ListItem.findViewById(R.id.txtID);
        TextView txtName = ListItem.findViewById(R.id.txtNameProduct);
        ImageView imgFlower = ListItem.findViewById(R.id.imgProduct);
        TextView txtDescription = ListItem.findViewById(R.id.txtDescription);
        TextView txtQuality = ListItem.findViewById(R.id.txtQuality);
        TextView txtPrice = ListItem.findViewById(R.id.txtPrice);
        TextView txtDate = ListItem.findViewById(R.id.txtDate);
        Button btn_edit = ListItem.findViewById(R.id.btnEdit);
        Button btn_del = ListItem.findViewById(R.id.btnDel);

        Flower flower = flowerList.get(position);

        txtId.setText(flower.getId_flower());
        txtName.setText(flower.getName());
        txtDescription.setText("Mô tả: "+flower.getDescription());
        txtQuality.setText("Số lượng: "+String.valueOf(flower.getQuantity()));
        txtPrice.setText("Gía tiền: "+String.valueOf(flower.getPrice()));
        txtDate.setText("Date: "+flower.getCreated_at());
        // Load the image using Glide
        Glide.with(context).load(flower.getUrl()).into(imgFlower );

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickEditProduct(flower);
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickDeleteProduct(flower);
            }
        });

        return ListItem;
    }
}
