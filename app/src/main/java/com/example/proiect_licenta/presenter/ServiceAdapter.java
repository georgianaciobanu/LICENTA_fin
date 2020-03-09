package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ServiceDataModel;

import java.util.ArrayList;


public class ServiceAdapter extends ArrayAdapter<ServiceDataModel> {

    public ServiceAdapter(Context context, ArrayList<ServiceDataModel> serviceList) {
        super(context, 0, serviceList);
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
                    R.layout.searched_service_row_item, parent, false
            );
        }

        ImageView imageViewService = convertView.findViewById(R.id.item_info);
        ImageView loc = convertView.findViewById(R.id.loc_info);
        TextView textViewName = convertView.findViewById(R.id.servicename);
        MultiAutoCompleteTextView adresa = convertView.findViewById(R.id.adresa_bd);
        ServiceDataModel currentItem = getItem(position);

        if (currentItem != null) {

            textViewName.setText(currentItem.getName());
            adresa.setText(currentItem.getAdresa());

        }

        return convertView;
    }
}
