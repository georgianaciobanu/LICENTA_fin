package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.view.ChatActivity;
import com.example.proiect_licenta.view.ServicesListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class BookingAdapter extends ArrayAdapter<Request> {


    Date currentTime = Calendar.getInstance().getTime();
    Context con;

    public BookingAdapter(Context context, ArrayList<Request> bookingList) {
        super(context, 0, bookingList);
        con=context;
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

        final String currentUserEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.booking_item_list, parent, false
            );
        }



       final TextView tw_status = convertView.findViewById(R.id.tw_status);
        ImageButton imageButtonChat=convertView.findViewById(R.id.imageButtonChat);
        TextView tw_dataProgramarii = convertView.findViewById(R.id.tw_dataProgramarii);
        TextView tw_clientBD = convertView.findViewById(R.id.tw_clientBD);
        TextView tw_detalii = convertView.findViewById(R.id.tw_detalii);
        TextView tw_nrTel = convertView.findViewById(R.id.tw_nrtelefonServ);
        TextView tw_email = convertView.findViewById(R.id.tw_emailServ);
        TextView tw_cl= convertView.findViewById(R.id.tw_client);
        final Button btn_anulare=convertView.findViewById(R.id.btn_anulareProg);
        TextView tw_pretTotalCalc=convertView.findViewById(R.id.tw_pretTotalCalc);
        final ListView listView_serviciiBook=convertView.findViewById(R.id.listView_serviciiBook);


        final Request currentItem = getItem(position);




        if (currentItem != null) {

            ServicesListAdapter adapterServ = new ServicesListAdapter( convertView.getContext(),currentItem.getServicii(),null,1 ,0);
            listView_serviciiBook.setAdapter(adapterServ);
            Utility.setListViewHeightBasedOnItems(listView_serviciiBook);



            if (System.currentTimeMillis() > currentItem.getDataProgramare().getTime()){
                tw_status.setText("EFECTUATA");
        }
           else if(System.currentTimeMillis() < currentItem.getDataProgramare().getTime()){
                tw_status.setText("IN PROGRES");
            }



            if(currentItem.getStatus().equals("anulata")){
                if(currentItem.getClient().getEmail().equals(currentUserEmail)) {
                    tw_status.setText("Anulata de catre: " + currentItem.getClient().getUsername());
                }else if(currentItem.getService().getEmail().equals(currentUserEmail)) {
                    tw_status.setText("Anulata de catre: " + currentItem.getService().getUsername());
                    tw_status.setText("ANULATA");
                }
                btn_anulare.setVisibility(View.INVISIBLE);
            }
            String dateToDisplay= DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(currentItem.getDataProgramare());


            if(currentItem.getClient().getEmail().equals(currentUserEmail)){
                tw_nrTel.setText(currentItem.getService().getTelefon());
                tw_email.setText(currentItem.getService().getEmail());
                tw_clientBD.setText(currentItem.getService().getUsername());
                tw_cl.setText("Service: ");

                imageButtonChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("messageFor", currentItem.getService().getEmail());
                        con.startActivity(intent);
                    }
                });

            }else if(currentItem.getService().getEmail().equals(currentUserEmail)){
                tw_nrTel.setText(currentItem.getClient().getTelefon());
                tw_email.setText(currentItem.getClient().getEmail());
                tw_clientBD.setText(currentItem.getClient().getUsername());

                imageButtonChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("messageFor", currentItem.getClient().getEmail());
                        con.startActivity(intent);
                    }
                });
            }
            tw_dataProgramarii.setText(dateToDisplay);
            tw_detalii.setText(currentItem.getDetalii());

            double sum=0;
            for(Serviciu s: currentItem.getServicii()){
                sum=sum+s.getPret();
            }
                tw_pretTotalCalc.setText(String.valueOf(sum)+ "RON");

            if(tw_status.getText()=="EFECTUATA"){
                btn_anulare.setVisibility(View.INVISIBLE);
            }

            if(tw_status.getText()=="IN PROGRES"){
                btn_anulare.setVisibility(View.VISIBLE);
            }



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
                                if(currentItem.getClient().getEmail().equals(currentUserEmail)) {
                                    tw_status.setText("Anulata de catre: " + currentItem.getClient().getUsername());
                                }else if(currentItem.getService().getEmail().equals(currentUserEmail)) {
                                    tw_status.setText("Anulata de catre: " + currentItem.getService().getUsername());

                                }
                                btn_anulare.setVisibility(View.INVISIBLE);
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
