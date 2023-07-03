package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flowerapp.Entity.Category;
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

        Category category = categoryList.get(position);

        txtId.setText("ID: "+category.getId_category());
        txtName.setText("Tên loại: "+category.getName_category());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickEditCategory(category);
            }
        });

        return ListItem;
    }

}
