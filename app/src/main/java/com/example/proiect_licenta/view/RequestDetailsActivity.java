package com.example.proiect_licenta.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ServicesListAdapter;


public class RequestDetailsActivity extends AppCompatActivity  {

    ListView listView_servicii;
    Button btn_Resping;
    TextView tw_pretTotalCalc;
    TextView tw_dataProgramarii;
    TextView tw_status;
    TextView tw_clientBD;
    TextView tw_detalii;
    TextView btn_Confirma;
    Button btn_Anuleaza;
    Request readRequest;
    OnGetDataListener listenerReqest;
    Integer userOrService=0;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cerere);


        readRequest = new Request();
        Intent i = getIntent();

        readRequest = (Request) i.getSerializableExtra("currentReq");
        userOrService = (Integer) i.getSerializableExtra("userOrService");

        builder = new AlertDialog.Builder(this);

        listView_servicii = (ListView) findViewById(R.id.listView_servicii);
        btn_Resping = (Button) findViewById(R.id.btn_Resping);
        btn_Confirma = (Button) findViewById(R.id.btn_Confirma);
        tw_pretTotalCalc = (TextView) findViewById(R.id.tw_pretTotalCalc);
        tw_dataProgramarii = (TextView) findViewById(R.id.tw_dataProgramarii);
        tw_status = (TextView) findViewById(R.id.tw_status);
        tw_clientBD = (TextView) findViewById(R.id.tw_clientBD);
        tw_detalii = (TextView) findViewById(R.id.tw_detalii);
        btn_Anuleaza = (Button) findViewById(R.id.btn_Anulare);

        if (userOrService == 1) {
            btn_Confirma.setVisibility(View.INVISIBLE);
            btn_Resping.setVisibility(View.INVISIBLE);
            btn_Anuleaza.setVisibility(View.VISIBLE);

        } else if (userOrService == 2) {
            btn_Confirma.setVisibility(View.VISIBLE);
            btn_Resping.setVisibility(View.VISIBLE);
            btn_Anuleaza.setVisibility(View.INVISIBLE);

        }


        if (readRequest != null) {
            tw_dataProgramarii.setText(readRequest.getDataProgramare().toString());
            tw_status.setText(readRequest.getStatus());
            tw_clientBD.setText(readRequest.getClient().getUsername());
            tw_detalii.setText(readRequest.getDetalii());

            if(readRequest.getStatus().equals("validat")){
                btn_Confirma.setEnabled(false);
                btn_Confirma.setClickable(false);
            } else{
                btn_Confirma.setEnabled(true);
                btn_Confirma.setClickable(true);
            }
            if(readRequest.getStatus().equals("anulata")){
                btn_Resping.setEnabled(false);
                btn_Resping.setClickable(false);
            }else{
                btn_Resping.setEnabled(true);
                btn_Resping.setClickable(true);
            }

            ServicesListAdapter adapterServ = new ServicesListAdapter(getApplicationContext(), readRequest.getServicii(),null);
            listView_servicii.setAdapter(adapterServ);
        }


        btn_Resping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        RequestDetailsActivity.this);

                // set title
                alertDialogBuilder.setTitle("Urmeaza sa respingeti cererea. Continuati?");

                // set dialog message
                alertDialogBuilder

                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFunctions.updateRequest(readRequest, "respinsa");
                                tw_status.setText("respinsa");
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
        btn_Anuleaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        RequestDetailsActivity.this);

                // set title
                alertDialogBuilder.setTitle("Urmeaza sa anulati cererea. Continuati?");

                // set dialog message
                alertDialogBuilder

                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFunctions.updateRequest(readRequest, "anulata");
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



        btn_Confirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        RequestDetailsActivity.this);

                // set title
                alertDialogBuilder.setTitle("Urmeaza sa confirmati cererea. Continuati?");

                // set dialog message
                alertDialogBuilder

                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFunctions.updateRequest(readRequest, "confirmata");
                                tw_status.setText("confirmata");
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






    }


}
