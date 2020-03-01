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
        listenerReqest = new

                OnGetDataListener() {
                    @Override
                    public void onStartFirebaseRequest() {
                        Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        for (DataSnapshot singleSnapshot : data.getChildren()) {
                            readRequest = singleSnapshot.getValue(Request.class);

                        }

                        if(readRequest!=null){
                            tw_dataProgramarii.setText(readRequest.getDataProgramare().toString());
                            tw_status.setText(readRequest.getStatus());
                            tw_clientBD.setText(readRequest.getClient().getUsername());
                            tw_detalii.setText(readRequest.getDetalii());

                            ServicesListAdapter adapterServ = new ServicesListAdapter(getApplicationContext(),readRequest.getServicii());
                            listView_servicii.setAdapter(adapterServ);
                        }

                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                }

        ;


        listView_servicii=(ListView)findViewById(R.id.listView_servicii);
        btn_Resping=(Button)findViewById(R.id.btn_Resping);
        btn_Confirma=(Button)findViewById(R.id.btn_Confirma);
        tw_pretTotalCalc=(TextView)findViewById(R.id.tw_pretTotalCalc);
        tw_dataProgramarii=(TextView)findViewById(R.id.tw_dataProgramarii);
        tw_status=(TextView)findViewById(R.id.tw_status);
        tw_clientBD=(TextView)findViewById(R.id.tw_clientBD);
        tw_detalii=(TextView)findViewById(R.id.tw_detalii);


        readRequest=new Request();
        readRequest("Request",listenerReqest);



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



    public void readRequest(String child, final OnGetDataListener listener) {

        listenerReqest.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child(child).orderByChild("requestId").equalTo("-M0nFBg1cckdaSl6Qj9O").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


}
