package com.example.flowerapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flowerapp.Entity.Stories;
import com.example.flowerapp.Item_Detail_Fragment;
import com.example.flowerapp.R;

import java.util.List;

public class StoriesAdapter extends ArrayAdapter<Stories> {
    private Context context;
    private List<Stories> imageList;

    public StoriesAdapter(Context context, List<Stories> imageList) {
        super(context,R.layout.item_hoa,imageList);
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Stories getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_hoa, null);

            holder = new ViewHolder();
            holder.imageView = view.findViewById(R.id.img_flower);
            holder.textView = view.findViewById(R.id.tv_itemhoa);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Stories item = imageList.get(position);
        Resources resources = context.getResources();
        String imageResourceName = String.valueOf(item.getImg());
        int imageResourceId = resources.getIdentifier(imageResourceName, "drawable", context.getPackageName());
        holder.imageView.setImageResource(imageResourceId);
        holder.textView.setText(item.getTenImg());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Item_Detail_Fragment.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id_story", item.getId());
                context.startActivity(i);
            }
        });
        return view;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
