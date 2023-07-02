package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flowerapp.Entity.Image;
import com.example.flowerapp.Entity.User;
import com.example.flowerapp.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Activity context;
    private List<User> userList;
    private IClickListener mIClickListener;
    public interface IClickListener {
        void onClickLockUser(User user);
    }
    public UserAdapter(Activity context, List<User> userList, IClickListener iClickListener)
    {
        super(context,R.layout.activity_listview_user,userList);
        this.context = context;
        this.userList = userList;
        this.mIClickListener = iClickListener;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListItem = inflater.inflate(R.layout.activity_listview_user,null,true);
        TextView txtFulname = ListItem.findViewById(R.id.txtFullname);
        TextView txtNumberphone = ListItem.findViewById(R.id.txtNumberphone);
        TextView txtUsername = ListItem.findViewById(R.id.txtUsername);
        TextView txtAddress = ListItem.findViewById(R.id.txtAddress);
        TextView txtCreatedAt = ListItem.findViewById(R.id.txtCreateAt);
        ImageButton btn_lock = ListItem.findViewById(R.id.btnLockUser);

        User user = userList.get(position);

        txtFulname.setText(user.getFullname());
        txtNumberphone.setText(user.getNumberphone());
        txtUsername.setText(user.getUsername());
        txtAddress.setText(user.getAddress());
        txtCreatedAt.setText(user.getCreated_at());
        btn_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickLockUser(user);
            }
        });
        return ListItem;
    }
}
