package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.view.AboutServiceActivity;

import java.util.ArrayList;

public class FavServicesAdapter extends ArrayAdapter<Service> {

    public FavServicesAdapter(Context context, ArrayList<Service> countryList) {
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
                    R.layout.fav_services, parent, false
            );
        }

        ImageView imageViewService = convertView.findViewById(R.id.item_info);
        ImageView loc = convertView.findViewById(R.id.loc_info);
        TextView textViewName = convertView.findViewById(R.id.servicename);
        TextView adresa = convertView.findViewById(R.id.adresa_bd);
        TextView program=convertView.findViewById(R.id.programservice);




        final Service currentItem = getItem(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAboutService(currentItem);

            }
        });

        if (currentItem != null) {
            textViewName.setText(currentItem.getNumeService());
            adresa.setText(currentItem.getLoc().getAdresa());
            program.setText(currentItem.getProgram());
        }
        return convertView;
    }

    public void goToAboutService(Service currentItem)
    {


        Intent intent = new Intent();
        intent.setClass(getContext(), AboutServiceActivity.class);
        intent.putExtra("CurrentService",currentItem );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);


    }
}