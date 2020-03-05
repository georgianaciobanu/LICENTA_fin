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
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.ServicesListAdapter;
import com.example.proiect_licenta.presenter.TrimiteCerereActivity;

import java.util.ArrayList;

public class ChooseServicesActivity extends AppCompatActivity {
    ArrayList<Serviciu> serviciiSelectate= new ArrayList<>();
    ArrayList<Serviciu> serviciiSelectateCerere= new ArrayList<>();
    ArrayList<Serviciu> listaServicii= new ArrayList<Serviciu>();
    ListView listView;
    ServicesListAdapter adapter;
    Button btnBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_services);
        Intent i = getIntent();

        serviciiSelectateCerere=(ArrayList<Serviciu>)i.getSerializableExtra("ListaServiciiSelectate");
        if(serviciiSelectateCerere!=null){
            serviciiSelectate=serviciiSelectateCerere;
        }
        listaServicii = (ArrayList<Serviciu>) i.getSerializableExtra("ListaServicii");


        btnBack=(Button)findViewById(R.id.btn_chooseServicesList);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itReq = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
                itReq.putExtra("ListaServiciiSelectate",serviciiSelectate);
                startActivityForResult(itReq,0);
            }
        });



        listView = findViewById(R.id.listView);


        adapter = new ServicesListAdapter(getApplicationContext(), listaServicii);

        listView.setAdapter(adapter);






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View vw = listView.getChildAt(position);


                int desiredBackgroundColor = Color.BLUE;

                ColorDrawable viewColor = (ColorDrawable) vw.getBackground();

                if (viewColor == null) {
                    vw.setBackgroundColor(desiredBackgroundColor);
                    final Serviciu selectedValue = listaServicii.get(position);
                    serviciiSelectate.add(selectedValue);
                    return;
                }

                int currentColorId = viewColor.getColor();

                if (currentColorId == desiredBackgroundColor) {
                    vw.setBackgroundColor(Color.TRANSPARENT);
                    final Serviciu selectedValue = listaServicii.get(position);
                    serviciiSelectate.remove(selectedValue);
                } else {
                    vw.setBackgroundColor(desiredBackgroundColor);
                    final Serviciu selectedValue = listaServicii.get(position);
                    serviciiSelectate.add(selectedValue);

                }


                }

        });


    }
}
