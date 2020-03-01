package com.example.proiect_licenta.view;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.presenter.ServiceAdapter;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchedServiceActivity extends AppCompatActivity {

    ArrayList<ServiceDataModel> dataModels;
    ListView listView;
    private static ServiceAdapter adapter;
    OnGetDataListener listenerService;
    Service service;
    ArrayList<Service> services= new ArrayList<>();
    Boolean ok=false;
    String TAG="SearchedServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_service);
        service=new Service();
        listView = (ListView) findViewById(R.id.list_searchedService);

        dataModels = new ArrayList<>();


        listenerService = new

                OnGetDataListener() {
                    @Override
                    public void onStartFirebaseRequest() {
                        Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        for (DataSnapshot singleSnapshot : data.getChildren()) {
                            service = singleSnapshot.getValue(Service.class);
                            if (service != null) {
                                services.add(service);
                                dataModels.add(new ServiceDataModel(service.getNumeService(), service.getLoc().getAdresa()));
                            }
                        }
                        if (dataModels.size() > 0 ) {
                            adapter = new ServiceAdapter(getApplicationContext(), dataModels);

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

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                };



        findService("Service",listenerService);

//        Service currentService = new Service();
//
//        currentService.setDescriere("Service GSM.info promoveaza servicii si solutii de intretinere, reparatie si reconditionare, pentru dizpozitivele smartphone, telefoane GSM, tablete GPS-uri si alte echipamente mobile.");
//        currentService.setEmail("servicegsm@gmil.com");
//        currentService.setNumeService("Service GSM.info");
//        currentService.setPass("passgsm1234");
//        currentService.setProprietar("Vasile Mircea");
//        currentService.setProgram("luni-vineri: 10-18");
//        currentService.setTelefon("0723.50.30.50");
//        currentService.setUsername("servicegsm");
//
//        final PhysicalLocation serviceLocation = new PhysicalLocation();
//
//        serviceLocation.setAdresa("Bulevardul Regina Elisabeta Nr.35, Ap.6, Et.1, Interfon 006, Sector 5, Bucuresti");
//        serviceLocation.setLatitudine(44.434744);
//        serviceLocation.setLogitudine(26.0954323);




    }

    public void findService(String child, final OnGetDataListener listener) {

        listenerService.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child(child).addListenerForSingleValueEvent(new ValueEventListener() {
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
