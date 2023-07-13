package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.flowerapp.Adapter.AdminCategoryAdapter;
import com.example.flowerapp.Adapter.UserAdapter;
import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdminActivity extends AppCompatActivity {
    DatabaseReference databasecategory;
    ListView categoryListView;
    List<Category> categoryList;
    ImageView home_ad, goods, oder, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin);
        categoryListView = findViewById(R.id.list_category);
        categoryList = new ArrayList<>();
        databasecategory = FirebaseDatabase.getInstance().getReference("Cate_Flower");

        loadMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databasecategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();  // because everytime when data updates in your firebase database it creates the list with updated items
                // so to avoid duplicate fields we clear the list everytime
                if(snapshot.exists()) {
                    for (DataSnapshot categorysnapshot : snapshot.getChildren()) {
                        Category category = categorysnapshot.getValue(Category.class);
                        categoryList.add(category);
                    }
                    AdminCategoryAdapter adaptor = new AdminCategoryAdapter(CategoryAdminActivity.this, categoryList, new AdminCategoryAdapter.IClickListener() {
                        @Override
                        public void onClickEditCategory(Category category) {
                            openDialogEdit(category);
                        }
                    });
                    adaptor.notifyDataSetChanged();
                    categoryListView.setAdapter(adaptor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogEdit(Category category) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_edit_category);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_edit = dialog.findViewById(R.id.btn_edit);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText txtName = dialog.findViewById(R.id.txtName);
        txtName.setText(category.getName_category());
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Cate_Flower");
                String newName = txtName.getText().toString().trim();
                category.setName_category(newName);
                reference.child(String.valueOf(category.getId_category())).updateChildren(category.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(CategoryAdminActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    public void openDialogAddNew(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_category);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_add = dialog.findViewById(R.id.btn_add);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText txtName = dialog.findViewById(R.id.txtName);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasecategory = FirebaseDatabase.getInstance().getReference("Cate_Flower");
                String key = databasecategory.push().getKey();
                databasecategory.child(key).removeValue();
                String newName = txtName.getText().toString().trim();
                databasecategory.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Category category = new Category();
                        category.setId_category(key);
                        category.setName_category(newName);
                        databasecategory.child(key).setValue(category);
                        Toast.makeText(CategoryAdminActivity.this, "You have add successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        dialog.show();

    }
    public void loadMenu(){
        init();
        home_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(CategoryAdminActivity.this, HomeAdminActivity.class);
                startActivity(ih);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ig= new Intent(CategoryAdminActivity.this, CategoryAdminActivity.class);
                startActivity(ig);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io= new Intent(CategoryAdminActivity.this, OrderAdminActivity.class);
                startActivity(io);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iu= new Intent(CategoryAdminActivity.this, UserAdminActivity.class);
                startActivity(iu);
            }
        });
    }

    public void init(){
        home_ad= findViewById(R.id.home_ad);
        goods= findViewById(R.id.goods_ad);
        oder= findViewById(R.id.oder_ad);
        user= findViewById(R.id.user_ad);
    }
}