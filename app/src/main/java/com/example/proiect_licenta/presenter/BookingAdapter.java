package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.view.ServicesListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class BookingAdapter extends ArrayAdapter<Request> {


    Date currentTime = Calendar.getInstance().getTime();
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
        ListView listView_serviciiBook=convertView.findViewById(R.id.listView_serviciiBook);


        final Request currentItem = getItem(position);



        ServicesListAdapter adapterServ = new ServicesListAdapter(getContext(),currentItem.getServicii() );


        if (currentItem != null) {


            listView_serviciiBook.setAdapter(adapterServ);
            if (System.currentTimeMillis() > currentItem.getDataProgramare().getTime()){
                tw_status.setText("EFECTUATA");
        }
           else if(System.currentTimeMillis() < currentItem.getDataProgramare().getTime()){
                tw_status.setText("IN PROGRES");
            }
            else if(currentItem.getStatus().equals("ANULATA")){
                tw_status.setText("ANULATA");
            }
            tw_dataProgramarii.setText(currentItem.getDataProgramare().toString());
            tw_clientBD.setText(currentItem.getClient().getUsername());
            tw_detalii.setText(currentItem.getDetalii());
        }



        btn_anulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set title
                alertDialogBuilder.setTitle("Urmeaza sa anulati rezervarea. Continuati?");

                // set dialog message
                alertDialogBuilder

                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFunctions.updateRequest(currentItem, "anulata");
                                tw_status.setText("anulata");
                            }
                        })
                        .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


        return convertView;
    }



}
