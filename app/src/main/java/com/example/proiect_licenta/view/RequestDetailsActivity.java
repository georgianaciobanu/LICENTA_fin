package com.example.proiect_licenta.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ServicesListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.DateFormat;


public class RequestDetailsActivity extends AppCompatActivity  {

    ListView listView_servicii;
    Button btn_Resping;
    TextView tw_pretTotalCalc;
    TextView tw_dataProgramarii;
    TextView tw_status;
    TextView tw_clientBD;
    TextView tw_detalii;
    TextView btn_Confirma;
    TextView tw_telefon;
    TextView tw_email;
    Button btn_Anuleaza;
    Request readRequest;
    Integer userOrService=0;
    Service service;
    AlertDialog.Builder builder;
    ImageButton imageButtonChat;




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
        tw_telefon=(TextView)findViewById(R.id.tw_nrtelefonclient);
        tw_email=(TextView)findViewById(R.id.tw_emailClient);
        btn_Anuleaza = (Button) findViewById(R.id.btn_Anulare);
        imageButtonChat=(ImageButton)findViewById(R.id.ButtonChat);




        if (userOrService == 1) {
            btn_Confirma.setVisibility(View.INVISIBLE);
            btn_Resping.setVisibility(View.INVISIBLE);
            btn_Anuleaza.setVisibility(View.VISIBLE);

            tw_clientBD.setText(readRequest.getService().getUsername());
            tw_telefon.setText(readRequest.getService().getTelefon());
            tw_email.setText(readRequest.getService().getEmail());

            imageButtonChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("messageFor", readRequest.getService().getEmail());
                    startActivity(intent);
                }
            });


        } else if (userOrService == 2) {
            btn_Confirma.setVisibility(View.VISIBLE);
            btn_Resping.setVisibility(View.VISIBLE);
            btn_Anuleaza.setVisibility(View.INVISIBLE);



            tw_clientBD.setText(readRequest.getClient().getUsername());
            tw_telefon.setText(readRequest.getClient().getTelefon());
            tw_email.setText(readRequest.getClient().getEmail());

            imageButtonChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("messageFor", readRequest.getClient().getEmail());
                    startActivity(intent);
                }
            });

        }




        if (readRequest != null) {
            String dateToDisplay= DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(readRequest.getDataProgramare());

            tw_dataProgramarii.setText(dateToDisplay);

            if(userOrService==1 && (readRequest.getStatus().equals("confirmata") || readRequest.getStatus().equals("respinsa"))) {
                btn_Anuleaza.setVisibility(View.INVISIBLE);
            }
//
                tw_status.setText(readRequest.getStatus());
//            }

            tw_detalii.setText(readRequest.getDetalii());
            double sum=0;
            for(Serviciu s: readRequest.getServicii()){
                sum=sum+s.getPret();
            }
            tw_pretTotalCalc.setText(String.valueOf(sum)+ "RON");


            if(readRequest.getStatus().equals("confirmata")) {

                btn_Confirma.setVisibility(View.INVISIBLE);
            }

            if(readRequest.getStatus().equals("respinsa")){

                btn_Resping.setVisibility(View.INVISIBLE);
            }

            if(readRequest.getStatus().equals("anulata")){

                btn_Resping.setVisibility(View.INVISIBLE);
                btn_Anuleaza.setVisibility(View.INVISIBLE);
                btn_Confirma.setVisibility(View.INVISIBLE);
            }

            ServicesListAdapter adapterServ = new ServicesListAdapter(getApplicationContext(), readRequest.getServicii(),null,1,0);
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
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{readRequest.getClient().getEmail()});
                                i.putExtra(Intent.EXTRA_SUBJECT, "Cererea nr. "+ readRequest.getRequestId());
                                i.putExtra(Intent.EXTRA_TEXT   , "Ne cerem scuze dar, cererea dumneavoastra din data de "+ readRequest.getDataTrimiterii()+" a fost respinsa. Din pacate, data programarii din "+readRequest.getDataProgramare()+" nu este disponibila. Va rog sa ne contactati pentru a stabili o alta programare. "+" Multumim si va asteptam! Service "+ readRequest.getService().getNumeService());
                                try {
                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(RequestDetailsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                }
                                tw_status.setText("respinsa");
                                btn_Resping.setVisibility(View.INVISIBLE);
                                btn_Confirma.setVisibility(View.VISIBLE);
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
                                btn_Anuleaza.setVisibility(View.INVISIBLE);
                                tw_status.setText("Anulata");
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
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{readRequest.getClient().getEmail()});
                                i.putExtra(Intent.EXTRA_SUBJECT, "Cererea nr. "+ readRequest.getRequestId());
                                i.putExtra(Intent.EXTRA_TEXT   , "Cererea dumneavoastra din data de "+ readRequest.getDataTrimiterii()+" a fost confirmata. Data programarii a fost stabilita la: "+readRequest.getDataProgramare()+" desfasurata la sediul nostru din "+readRequest.getService().getLoc().getAdresa()+" Multumim si va asteptam! Service "+ readRequest.getService().getNumeService());
                                try {
                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(RequestDetailsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                }
                                tw_status.setText("confirmata");
                                btn_Confirma.setVisibility(View.INVISIBLE);
                                btn_Resping.setVisibility(View.VISIBLE);
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
