package com.example.proiect_licenta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
    ArrayList<Serviciu> serviciiSelectateNou= new ArrayList<>();
    ArrayList<Serviciu> serviciiSelectate= new ArrayList<>();
    ArrayList<Serviciu> listaServiciiService= new ArrayList<Serviciu>();
    ListView listView;
    Request currentRequest;
    ServicesListAdapter adapter;
    Button btnBack;
    String TAG="ChooseServicesActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_services);
        Intent i = getIntent();

        currentRequest = (Request) i.getSerializableExtra("request");

        btnBack=(Button)findViewById(R.id.btn_chooseServicesList);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itReq = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
                currentRequest.setServicii(serviciiSelectate);
                itReq.putExtra("requestBack",currentRequest);
                Log.i(TAG," DIN CHOOSE SPRE TRIMITE : "+currentRequest.getServicii().size());
                startActivityForResult(itReq,0);
            }
        });


        listaServiciiService=currentRequest.getService().getSevicii();
        if(currentRequest.getServicii()!=null) {
            serviciiSelectate = currentRequest.getServicii();
        }
        listView = findViewById(R.id.listView);
        adapter = new ServicesListAdapter(getApplicationContext(), listaServiciiService,serviciiSelectate,1);
        listView.setAdapter(adapter);







        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View vw = listView.getChildAt(position);


                int desiredBackgroundColor = Color.BLUE;

                ColorDrawable viewColor = (ColorDrawable) vw.getBackground();

                if (viewColor == null) {
                    vw.setBackgroundColor(desiredBackgroundColor);
                    final Serviciu selectedValue = listaServiciiService.get(position);
                    serviciiSelectate.add(selectedValue);
                    return;
                }

                int currentColorId = viewColor.getColor();

                if (currentColorId == desiredBackgroundColor) {
                    vw.setBackgroundColor(Color.TRANSPARENT);
                    final Serviciu selectedValue = listaServiciiService.get(position);
                    serviciiSelectate.remove(selectedValue);
                } else {
                    vw.setBackgroundColor(desiredBackgroundColor);
                    final Serviciu selectedValue = listaServiciiService.get(position);
                    serviciiSelectate.add(selectedValue);

                }


                }

        });


    }
}
