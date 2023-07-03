package com.example.flowerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.R;

import java.util.List;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context context;
    private List<Category> lstCategory;
    public CategoryAdapter(Context context)
    {
        this.context=context;
    }
    public void setData(List<Category> lstCategory)
    {
        this.lstCategory=lstCategory;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category=lstCategory.get(position);
        if(category==null)
        {
            return;
        }
        //gán tiêu đề tương ứng
        holder.tvCategory.setText(category.getName_category());
        //Tạo danh sách theo chiều ngang
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        //Gán vào RecyleView mình đã custom
        holder.rcyFlower.setLayoutManager(linearLayoutManager);
        //Gán dữ liệu cho danh sách
        FlowerAdapter flowerAdapter= new FlowerAdapter(context.getApplicationContext());
        flowerAdapter.setData(category.getLstImage());
        holder.rcyFlower.setAdapter(flowerAdapter);
    }

    @Override
    public int getItemCount() {
        if(lstCategory!=null)
        {
            return lstCategory.size();
        }
        else
        {
            return 0;
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCategory;
        private RecyclerView rcyFlower;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory=itemView.findViewById(R.id.tv_category);
            rcyFlower=itemView.findViewById(R.id.rcyFlower);
        }
    }
}
