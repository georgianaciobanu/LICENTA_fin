package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.view.ServicesListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookingAdapter extends ArrayAdapter<Request> {



    public BookingAdapter(Context context, ArrayList<Request> bookingList) {
        super(context, 0, bookingList);
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
                    R.layout.booking_item_list, parent, false
            );
        }



       final TextView tw_status = convertView.findViewById(R.id.tw_status);
        TextView tw_dataProgramarii = convertView.findViewById(R.id.tw_dataProgramarii);
        TextView tw_clientBD = convertView.findViewById(R.id.tw_clientBD);
        TextView tw_detalii = convertView.findViewById(R.id.tw_detalii);
        Button btn_anulare=convertView.findViewById(R.id.btn_anulareProg);
        ListView listView_servicii=convertView.findViewById(R.id.listView_servicii);


        Request currentItem = getItem(position);



        ServicesListAdapter adapterServ = new ServicesListAdapter(getContext(),currentItem.getServicii() );


        if (currentItem != null) {


            listView_servicii.setAdapter(adapterServ);
            tw_status.setText(currentItem.getStatus());
            tw_dataProgramarii.setText(currentItem.getDataProgramare().toString());
            tw_clientBD.setText(currentItem.getClient().getUsername());
            tw_detalii.setText(currentItem.getDetalii());
        }
        btn_anulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_status.setText("Anulata");
            }
        });
        return convertView;
    }



}
