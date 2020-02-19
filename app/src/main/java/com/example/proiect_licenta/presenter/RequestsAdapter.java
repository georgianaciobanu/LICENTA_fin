package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.ServiceDataModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RequestsAdapter extends ArrayAdapter<Request> {

    public RequestsAdapter(Context context, ArrayList<Request> requestsList) {
        super(context, 0, requestsList);
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
                    R.layout.requests_item_list, parent, false
            );
        }

        ImageView image_produs = convertView.findViewById(R.id.image_produs);
        TextView tw_status = (TextView)convertView.findViewById(R.id.tw_status);
        TextView tw_numeServiciu = (TextView)convertView.findViewById(R.id.tw_numeServiciu);
        TextView tw_dataTrimiterii = (TextView)convertView.findViewById(R.id.tw_dataTrimiterii);
        Request currentItem = getItem(position);

        if (currentItem != null) {

            tw_numeServiciu.setText(currentItem.getServiciu().getDenumire());
            tw_status.setText(currentItem.getStatus());
            tw_dataTrimiterii.setText(currentItem.getDataTrimiterii().toString());

        }

        return convertView;
    }

}
