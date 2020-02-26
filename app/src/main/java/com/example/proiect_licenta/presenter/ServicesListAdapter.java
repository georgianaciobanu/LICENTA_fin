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
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServicesListAdapter extends ArrayAdapter<Serviciu> {

    int rImages[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};


    public ServicesListAdapter(Context context, ArrayList<Serviciu> servicesList) {
        super(context, 0, servicesList);
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
                    R.layout.row, parent, false
            );
        }

        ImageView images = convertView.findViewById(R.id.image);
        TextView myTitle = convertView.findViewById(R.id.textView1);
        TextView myDescription = convertView.findViewById(R.id.textView2);
        TextView myDetails = convertView.findViewById(R.id.textView3);
        Serviciu currentItem = getItem(position);

        if (currentItem != null) {

            myTitle.setText(currentItem.getDenumire());
            myDescription.setText(currentItem.getPret().toString());
            myDetails.setText(currentItem.getDetalii());
            if (currentItem.getProdus() == "ELECTROCASNICE")
                images.setImageResource(rImages[0]);
            if (currentItem.getProdus() == "TELEFON/TABLETA")
                images.setImageResource(rImages[1]);
            if (currentItem.getProdus() == "MASINA")
                images.setImageResource(rImages[2]);
            if (currentItem.getProdus() == "PC/LAPTOP")
                images.setImageResource(rImages[3]);
        }


        return convertView;
    }
}

