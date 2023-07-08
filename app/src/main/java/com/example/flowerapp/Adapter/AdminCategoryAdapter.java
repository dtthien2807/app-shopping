package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.OptionAddActivity;
import com.example.flowerapp.ProductAdminActivity;
import com.example.flowerapp.R;

import java.util.List;

public class AdminCategoryAdapter extends ArrayAdapter<Category> {
    private Activity context;
    private List<Category> categoryList;
    private IClickListener mIClickListener;
    public interface IClickListener {
        void onClickEditCategory(Category category);
    }

    public AdminCategoryAdapter(Activity context, List<Category> categoryList, IClickListener iClickListener) {
        super(context, R.layout.activity_listview_category, categoryList);
        this.context = context;
        this.categoryList = categoryList;
        this.mIClickListener = iClickListener;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListItem = inflater.inflate(R.layout.activity_listview_category,null,true);
        TextView txtId = ListItem.findViewById(R.id.idCategory);
        TextView txtName = ListItem.findViewById(R.id.nameCategory);
        ImageButton btn_edit = ListItem.findViewById(R.id.btnEdit);
        LinearLayout item = ListItem.findViewById(R.id.itemCategory);
        ImageView img = ListItem.findViewById(R.id.imgCategory);

        Category category = categoryList.get(position);

        txtId.setText("ID: "+category.getId_category());
        txtName.setText("Tên loại: "+category.getName_category());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickEditCategory(category);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductAdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id_category",category.getId_category());
                context.startActivity(intent);
            }
        });

        return ListItem;
    }

}
