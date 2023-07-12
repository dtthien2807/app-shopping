package com.example.flowerapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.Order;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailOrderActivity extends AppCompatActivity {
    DatabaseReference databaseorder;
    TextView orderID, createdAt, statusOrder, nameUser, phoneUser, addressUser, dateOrder, dateShip, sumBill, noteBill;
    Button btnUpdateBill;
    CheckBox option0, option1, option2, option3, option4;
    private String id_order;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        init();
        databaseorder = FirebaseDatabase.getInstance().getReference("Order");
        btnUpdateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdateBill();
            }
        });
    }


    public void init()
    {
        orderID = findViewById(R.id.orderID);
        createdAt = findViewById(R.id.createdAt);
        statusOrder = findViewById(R.id.statusOrder);
        nameUser = findViewById(R.id.nameUser);
        phoneUser = findViewById(R.id.phoneUser);
        addressUser = findViewById(R.id.addressUser);
        dateOrder = findViewById(R.id.dateOrder);
        dateShip = findViewById(R.id.dateShip);
        sumBill = findViewById(R.id.sumBill);
        noteBill = findViewById(R.id.noteBill);
        btnUpdateBill = findViewById(R.id.btnUpdateBill);
    }
    public void openDialogUpdateBill() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_edit_status_order);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_edit = dialog.findViewById(R.id.btn_edit);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        option0 = dialog.findViewById(R.id.option0);
        option1 = dialog.findViewById(R.id.option1);
        option2 = dialog.findViewById(R.id.option2);
        option3 = dialog.findViewById(R.id.option3);
        option4 = dialog.findViewById(R.id.option4);

        databaseorder.child(id_order).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int status = snapshot.child("status").getValue(Integer.class);
                switch (status)
                {
                    case 0:
                    {
                        option0.setChecked(true);
                        break;
                    }
                    case 1:
                    {
                        option1.setChecked(true);
                        break;
                    }
                    case 2:
                    {
                        option2.setChecked(true);
                        break;
                    }
                    case 3:
                    {
                        option3.setChecked(true);
                        break;
                    }
                    case 4:
                    {
                        option4.setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        option0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option1.setChecked(false);
                    option2.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });
        option1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option2.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });

        option2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option1.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });
        option3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option1.setChecked(false);
                    option2.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });
        option4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option1.setChecked(false);
                    option2.setChecked(false);
                    option3.setChecked(false);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (option0.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option1.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option2.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option3.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(3).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option4.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(4).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });
        dialog.show();
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        id_order = intent.getStringExtra("id_order");
        super.onStart();
        databaseorder.child(id_order).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    orderID.setText(snapshot.child("id_order").getValue(String.class));
                    createdAt.setText(snapshot.child("create_at").getValue(String.class));
                    int checkStatus = snapshot.child("status").getValue(Integer.class);
                    switch (checkStatus)
                    {
                        case 0:
                        {
                            statusOrder.setText("Chờ xét duyệt");
                            statusOrder.setBackgroundColor(0x56E81C0A);
                            statusOrder.setTextColor(Color.parseColor("#5A182B"));
                            break;
                        }
                        case 1:
                        {
                            statusOrder.setText("Chờ giao hàng");
                            statusOrder.setBackgroundColor(0x56ACACA8);
                            statusOrder.setTextColor(Color.parseColor("#757574"));
                            break;
                        }
                        case 2:
                        {
                            statusOrder.setText("Đang giao hàng");
                            statusOrder.setBackgroundColor(0x787C05);
                            statusOrder.setTextColor(Color.parseColor("#787C05"));
                            break;
                        }
                        case 3:
                        {
                            statusOrder.setText("Giao hàng thành công");
                            statusOrder.setBackgroundColor(0x564CAF50);
                            statusOrder.setTextColor(Color.parseColor("#185A1B"));
                            break;
                        }
                        case 4:
                        {
                            statusOrder.setText("Giao hàng không thành công");
                            statusOrder.setBackgroundColor(0xF80E0E0E);
                            statusOrder.setTextColor(Color.parseColor("#F1F4F1"));
                            break;
                        }
                    }
                    nameUser.setText("Khách hàng: "+snapshot.child("name_user").getValue(String.class));
                    phoneUser.setText("Số điện thoại: " +snapshot.child("number_phone").getValue(String.class));
                    addressUser.setText("Địa chỉ: " +snapshot.child("address_user").getValue(String.class));
                    dateOrder.setText("Ngày yêu cầu giao: " +snapshot.child("order_ship_date").getValue(String.class));
                    dateShip.setText("Ngày giao: "+snapshot.child("ship_date").getValue(String.class));
                    sumBill.setText(String.valueOf(snapshot.child("total_bill").getValue(Float.class)));
                    noteBill.setText(snapshot.child("note").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
