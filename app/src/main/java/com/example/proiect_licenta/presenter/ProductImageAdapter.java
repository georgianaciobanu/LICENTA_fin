package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect_licenta.model.ProductsItem;
import com.example.proiect_licenta.R;

import java.util.ArrayList;


public class ProductImageAdapter extends ArrayAdapter<ProductsItem> {

    public ProductImageAdapter(Context context, ArrayList<ProductsItem> countryList) {
        super(context, 0, countryList);
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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.categorii_produse_row2, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_prod);


        ProductsItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getIcon());

        }
        return convertView;
    }
}