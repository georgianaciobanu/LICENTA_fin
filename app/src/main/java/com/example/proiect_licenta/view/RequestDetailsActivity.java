package com.example.proiect_licenta.view;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
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
    ArrayList<Serviciu> servicii= new ArrayList<>();

    String mTitle[] = {"Electrocasnice serviciu ", "Telefon/Tableta serviciu", "Masina serviciu", "PC/Laprop serviciu"};
    Float mPrice[] = {21f, 56f, 80f, 70.6f};
    int images[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cerere);

        Date currentTime = Calendar.getInstance().getTime();

        listView_servicii=(ListView)findViewById(R.id.listView_servicii);
        btn_Resping=(Button)findViewById(R.id.btn_Resping);
        btn_Confirma=(Button)findViewById(R.id.btn_Confirma);
        tw_pretTotalCalc=(TextView)findViewById(R.id.tw_pretTotalCalc);
        tw_dataProgramarii=(TextView)findViewById(R.id.tw_dataProgramarii);
        tw_status=(TextView)findViewById(R.id.tw_status);
        tw_clientBD=(TextView)findViewById(R.id.tw_clientBD);
        tw_detalii=(TextView)findViewById(R.id.tw_detalii);

        Serviciu serv= new Serviciu();
        serv.setDenumire("Reparatie display telefon");
        serv.setDetalii("samsung s9 - 3 zile");
        serv.setPret(67.5);
        serv.setProdus("TELEFON/TABLETA");

        Serviciu serv2= new Serviciu();
        serv2.setDenumire("Schimb cuva masina de spalat");
        serv2.setDetalii("marca bosh - 2 zile - deplasare la domiciliu");
        serv2.setPret(145.6);
        serv2.setProdus("ELECTROCASNICE");

        Serviciu serv3= new Serviciu();
        serv3.setDenumire("Reparatie touchscreen tableta");
        serv3.setDetalii("lenovo - 2 zile");
        serv3.setPret(44.7);
        serv3.setProdus("TELEFON/TABLETA");

        Serviciu serv4= new Serviciu();
        serv4.setDenumire("Distributie");
        serv4.setDetalii("schimb ulei, placute");
        serv4.setPret(168d);
        serv4.setProdus("MASINA");

        Serviciu serv5= new Serviciu();
        serv5.setDenumire("Montare placa video");
        serv5.setDetalii("nvidia geforce 940mx");
        serv5.setPret(98.3);
        serv5.setProdus("PC/LAPTOP");

        servicii.add(serv);
        servicii.add(serv2);
        servicii.add(serv3);
        servicii.add(serv4);
        servicii.add(serv5);

        User user= new User();
        user.setUsername("Clientul servicelui");

        Request req= new Request();
        req.setDataProgramare(currentTime);
        req.setDetalii("detalii acestei cereri sunt aici");
        req.setStatus("trimisa spre validare");
        req.setClient(user);


        req.setServicii(servicii);

        readRequest=new Request();
        readRequest();

        tw_dataProgramarii.setText(req.getDataProgramare().toString());
        tw_status.setText(readRequest.getStatus());
        tw_clientBD.setText(req.getClient().getUsername());
        tw_detalii.setText(readRequest.getDetalii());

        ServicesListAdapter adapterServ = new ServicesListAdapter(getApplicationContext(),req.getServicii());
        listView_servicii.setAdapter(adapterServ);

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

    public void readRequest(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Request");

        Query phoneQuery = ref.orderByChild("requestId").equalTo("-M0nDJVGE2DgrY9zKmOw");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    readRequest = singleSnapshot.getValue(Request.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
