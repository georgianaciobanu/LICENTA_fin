package com.example.proiect_licenta.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.presenter.ServicesListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RequestDetailsActivity extends AppCompatActivity {

    ListView listView_servicii;
    Button btn_Resping;
    TextView tw_pretTotalCalc;
    TextView tw_dataProgramarii;
    TextView tw_status;
    TextView tw_clientBD;
    TextView tw_detalii;
    TextView btn_Confirma;
    Request readRequest;
    OnGetDataListener listenerReqest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cerere);

        readRequest=new Request();
        Intent i = getIntent();

        readRequest=(Request) i.getSerializableExtra("currentReq");



        listView_servicii=(ListView)findViewById(R.id.listView_servicii);
        btn_Resping=(Button)findViewById(R.id.btn_Resping);
        btn_Confirma=(Button)findViewById(R.id.btn_Confirma);
        tw_pretTotalCalc=(TextView)findViewById(R.id.tw_pretTotalCalc);
        tw_dataProgramarii=(TextView)findViewById(R.id.tw_dataProgramarii);
        tw_status=(TextView)findViewById(R.id.tw_status);
        tw_clientBD=(TextView)findViewById(R.id.tw_clientBD);
        tw_detalii=(TextView)findViewById(R.id.tw_detalii);





        if(readRequest!=null){
                            tw_dataProgramarii.setText(readRequest.getDataProgramare().toString());
                            tw_status.setText(readRequest.getStatus());
                            tw_clientBD.setText(readRequest.getClient().getUsername());
                            tw_detalii.setText(readRequest.getDetalii());

                            ServicesListAdapter adapterServ = new ServicesListAdapter(getApplicationContext(),readRequest.getServicii());
                            listView_servicii.setAdapter(adapterServ);
                        }

        btn_Resping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_status.setText("respinsa");
            }
        });

        btn_Confirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_status.setText("confirmata");
            }
        });
    }
// TODO: update requests db





}
