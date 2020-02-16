package com.example.proiect_licenta.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.presenter.ServiceAdapter;
import com.example.proiect_licenta.model.ServiceDataModel;

import java.util.ArrayList;

public class SearchedServiceActivity extends AppCompatActivity {

    ArrayList<ServiceDataModel> dataModels;
    ListView listView;
    private static ServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_service);


        listView=(ListView)findViewById(R.id.list_searchedService);

        dataModels= new ArrayList<>();

        Service currentService= new Service();

        currentService.setDescriere("Service GSM.info promoveaza servicii si solutii de intretinere, reparatie si reconditionare, pentru dizpozitivele smartphone, telefoane GSM, tablete GPS-uri si alte echipamente mobile.");
        currentService.setEmail("servicegsm@gmil.com");
        currentService.setNumeService("Service GSM.info");
        currentService.setPass("passgsm1234");
        currentService.setProprietar("Vasile Mircea");
        currentService.setProgram("luni-vineri: 10-18");
        currentService.setTelefon("0723.50.30.50");
        currentService.setUsername("servicegsm");

        final PhysicalLocation serviceLocation= new PhysicalLocation();

        serviceLocation.setAdresa("Bulevardul Regina Elisabeta Nr.35, Ap.6, Et.1, Interfon 006, Sector 5, Bucuresti");
        serviceLocation.setLatitudine(44.434744);
        serviceLocation.setLogitudine(26.0954323);

        dataModels.add(new ServiceDataModel(currentService.getNumeService(), serviceLocation.getAdresa()));
        dataModels.add(new ServiceDataModel("SERVICE MASINI", "Android 1.1"));
        dataModels.add(new ServiceDataModel("SERVICE ELECTROCASNICE", "Android 1.5"));
        dataModels.add(new ServiceDataModel("SERVICE LAPTOP","Android 1.6"));


        adapter= new ServiceAdapter(getApplicationContext(),dataModels);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent itServ = new Intent(getApplicationContext(), AboutServiceActivity.class);

                    startActivity(itServ);

                }


        });


    }



}
