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
import com.example.proiect_licenta.presenter.FirebaseFunctions;
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
    OnGetDataListener listenerServiceToSend;
    Service service;
    Service serviceToSend= new Service();
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
        listenerServiceToSend = new

                OnGetDataListener() {
                    @Override
                    public void onStartFirebaseRequest() {
                        Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        for (DataSnapshot singleSnapshot : data.getChildren()) {
                            serviceToSend = singleSnapshot.getValue(Service.class);

                        }

                        if(serviceToSend!=null){
                            goToAboutService();
                        }

                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                };


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
                                   ServiceDataModel serviceDataModel= dataModels.get(position);
                                   FirebaseFunctions.getServiceFirebase("numeService",serviceDataModel.getName(),listenerServiceToSend);

                                }


                            });
                        }

                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                };



        FirebaseFunctions.getServicesFirebase(listenerService);

        //TODO: HARTA + get current client location
    }

public void goToAboutService(){
    Intent itServ = new Intent(getApplicationContext(), AboutServiceActivity.class);
    itServ.putExtra("CurrentService",serviceToSend);
    startActivity(itServ);
}

}
