package com.example.proiect_licenta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.ServicesListAdapter;
import com.example.proiect_licenta.presenter.TrimiteCerereActivity;

import java.util.ArrayList;

public class ChooseServicesActivity extends AppCompatActivity {
    ArrayList<Serviciu> servicii= new ArrayList<>();
    ArrayList<Serviciu> listaServicii= new ArrayList<Serviciu>();
    ListView listView;
    ServicesListAdapter adapter;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_services);

        Intent i = getIntent();
        listaServicii = (ArrayList<Serviciu>) i.getSerializableExtra("ListaServicii");


        btnBack=(Button)findViewById(R.id.btn_chooseServicesList);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itReq = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
                itReq.putExtra("ListaServicii",listaServicii);
                startActivity(itReq);
            }
        });
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

        if (listaServicii == null) {
            listaServicii = new ArrayList<Serviciu>();
        }
        else {

            for (int s = 0; s < listView.getChildCount(); s++) {
                final Serviciu selectedValue = servicii.get(s);
                if (listaServicii.contains(selectedValue)) {
                    listView.getChildAt(s).setBackgroundColor(Color.BLUE);
                }
            }
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View vw = listView.getChildAt(position);


                int desiredBackgroundColor = Color.BLUE;

                ColorDrawable viewColor = (ColorDrawable) vw.getBackground();

                if (viewColor == null) {
                    vw.setBackgroundColor(desiredBackgroundColor);
                    final Serviciu selectedValue = servicii.get(position);
                    listaServicii.add(selectedValue);
                    return;
                }

                int currentColorId = viewColor.getColor();

                if (currentColorId == desiredBackgroundColor) {
                    vw.setBackgroundColor(Color.TRANSPARENT);
                    final Serviciu selectedValue = servicii.get(position);
                    listaServicii.remove(selectedValue);
                } else {
                    vw.setBackgroundColor(desiredBackgroundColor);
                    final Serviciu selectedValue = servicii.get(position);
                    listaServicii.add(selectedValue);

                }


                }

        });


    }
}
