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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.flowerapp.Adapter.UserAdapter;
import com.example.flowerapp.Entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAdminActivity extends AppCompatActivity {
    DatabaseReference databaseuser;
    ListView userListView;
    List<User> userList;
    ImageView home_ad, goods, oder, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        userListView = findViewById(R.id.user_list);
        userList = new ArrayList<>();
        databaseuser = FirebaseDatabase.getInstance().getReference("User");

        loadMenu();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Query userQuery = databaseuser.orderByChild("created_at");
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();  // because everytime when data updates in your firebase database it creates the list with updated items
                // so to avoid duplicate fields we clear the list everytime
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot studentsnapshot : dataSnapshot.getChildren())
                    {
                        Boolean checkRole = studentsnapshot.child("role").getValue(Boolean.class);
                        Boolean checkStatus = studentsnapshot.child("status").getValue(Boolean.class);
                        if(!checkRole && checkStatus)
                        {
                            User user = studentsnapshot.getValue(User.class);
                            userList.add(user);
                        }
                    }
                    Collections.reverse(userList);
                    UserAdapter adaptor = new UserAdapter(UserAdminActivity.this, userList, new UserAdapter.IClickListener() {
                        @Override
                        public void onClickLockUser(User user) {
                            openDialogLockUser(user);
                        }
                    });
                    adaptor.notifyDataSetChanged();
                    userListView.setAdapter(adaptor);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void openDialogLockUser(User user) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_lock_user);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_lock = dialog.findViewById(R.id.btn_lock);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("User");
                user.setStatus(false);
                reference.child(String.valueOf(user.getNumberphone())).updateChildren(user.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(UserAdminActivity.this, "You have locked this user!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
                Intent ih= new Intent(UserAdminActivity.this, HomeAdminActivity.class);
                startActivity(ih);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ig= new Intent(UserAdminActivity.this, CategoryAdminActivity.class);
                startActivity(ig);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io= new Intent(UserAdminActivity.this, OrderAdminActivity.class);
                startActivity(io);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iu= new Intent(UserAdminActivity.this, UserAdminActivity.class);
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