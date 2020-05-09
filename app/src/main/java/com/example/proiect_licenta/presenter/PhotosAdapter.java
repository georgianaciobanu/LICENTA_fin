package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Upload;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotosAdapter extends ArrayAdapter<Upload> {

    public PhotosAdapter(Context context, ArrayList<Upload> uploads) {
        super(context, 0, uploads);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.view_custom, parent, false
            );
            viewHolder=new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.myImage);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder ) convertView.getTag();
        }


        Upload currentItem = getItem(position);

        if (currentItem != null) {
            Picasso.get().load(Uri.parse(currentItem.getImageUrl())).into(viewHolder.imageView  );
        }
        return convertView;
    }

    static class ViewHolder {
        private  ImageView imageView;
    }
}

