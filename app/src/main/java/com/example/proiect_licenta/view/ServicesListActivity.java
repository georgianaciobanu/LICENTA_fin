package com.example.proiect_licenta.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.presenter.ServicesListAdapter;

import java.util.ArrayList;

public class ServicesListActivity extends AppCompatActivity {

   ArrayList<Serviciu> servicii= new ArrayList<>();

    ListView listView;
   ServicesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicii);

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


        listView = findViewById(R.id.listView);


        adapter = new ServicesListAdapter(getApplicationContext(), servicii);

        listView.setAdapter(adapter);



    }
}



