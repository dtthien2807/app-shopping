package com.example.flowerapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flowerapp.Entity.Stories;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Item_Detail_Fragment extends AppCompatActivity {
    ImageView img;
    TextView name;
    TextView description;
    TextView people;
    TextView occa;
    TextView habitat;
    ImageView back;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item__detail_);
        init();
        showDescription();
        imgViewClick();
    }
    public void showDescription(){
        Intent intent=getIntent();
        String st_id= intent.getStringExtra("id_story");
        mDatabase= FirebaseDatabase.getInstance().getReference("flower_story/"+st_id);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Resources resources = getResources();
                String imageResourceName =snapshot.child("img").getValue(String.class);
                int imageResourceId = resources.getIdentifier(imageResourceName, "drawable", getPackageName());
                img.setImageResource(imageResourceId);
                name.setText(snapshot.child("tenImg").getValue(String.class));
                description.setText(snapshot.child("mota").getValue(String.class));
                people.setText(snapshot.child("doituong").getValue(String.class));
                occa.setText(snapshot.child("dip").getValue(String.class));
                habitat.setText(snapshot.child("moitruong").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void imgViewClick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Item_Detail_Fragment.this, StoriesActivity.class);
                startActivity(i);
            }
        });
    }
    public void init(){
        img= findViewById(R.id.item_img);
        name= findViewById(R.id.item_name);
        description= findViewById(R.id.item_description);
        people= findViewById(R.id.item_people);
        occa= findViewById(R.id.item_occa);
        habitat= findViewById(R.id.item_habitat);
        back= findViewById(R.id.img_btn_back);
    }
}