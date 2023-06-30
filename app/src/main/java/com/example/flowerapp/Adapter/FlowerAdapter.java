package com.example.flowerapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.R;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class FlowerAdapter  extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder>{
    private List<Flower> lst_Product;
    private Context context;
    public FlowerAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Flower> lstProduct)
    {
        this.lst_Product=lstProduct;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa,parent,false);
        return new FlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        Flower product= lst_Product.get(position);
      if(product==null)
          return;
      //gắn dữ liệu url cho img trong items
        Resources resources = context.getResources();
        String imageResourceName = product.getUrl();
        int imageResourceId = resources.getIdentifier(imageResourceName, "drawable", context.getPackageName());

        holder.imgFlower.setImageResource(imageResourceId);
        holder.title.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        if(lst_Product !=null)
            return lst_Product.size();
        return 0;
    }

    public class FlowerViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgFlower;
        private TextView title;
        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFlower= itemView.findViewById(R.id.img_flower);
            title= itemView.findViewById(R.id.tv_itemhoa);
        }
    }
}
