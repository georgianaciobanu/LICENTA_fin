package com.example.proiect_licenta.view;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ServiceAdapter;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import java.util.ArrayList;

public class SearchedServiceActivity extends AppCompatActivity implements OnMapReadyCallback {

    ArrayList<ServiceDataModel> dataModels;
    ListView listView;
    private static ServiceAdapter adapter;
    OnGetDataListener listenerService;
    OnGetDataListener listenerServiceToSend;
    Service service;
    Service serviceToSend = new Service();
    ArrayList<Service> services = new ArrayList<>();
    GoogleMap mGoogleMap;
    String TAG = "SearchedServiceActivity";
    SupportMapFragment mapFragment;
    LatLng currentLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = true;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    ArrayList<String> produseSelectate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_service);

        Intent i=getIntent();
        produseSelectate=(ArrayList<String>)i.getSerializableExtra("produseSelectate");

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapClient);
        mapFragment.getMapAsync(this);

        getDeviceLocation();


        service = new Service();
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

                        if (serviceToSend != null) {
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
                            if(produseSelectate!=null ) {
                                if (service != null ) {
                                   boolean ok=false;
                                    for(Serviciu s: service.getSevicii()){
                                        if(produseSelectate.contains(s.getProdus()))
                                            ok=true;

                                    }
                                    if(ok) {

                                        services.add(service);
                                        dataModels.add(new ServiceDataModel(service.getNumeService(), service.getLoc().getAdresa()));
                                    }
                                }
                            }
                        }
                        if (dataModels.size() > 0) {
                            adapter = new ServiceAdapter(getApplicationContext(), dataModels);

                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ServiceDataModel serviceDataModel = dataModels.get(position);
                                    FirebaseFunctions.getServiceFirebase("numeService", serviceDataModel.getName(), listenerServiceToSend);

                                }


                            });


                            if (services.size() > 0) {

                                for (Service s : services) {
                                    LatLng serviceMarker = new LatLng(s.getLoc().getLatitudine(), s.getLoc().getLogitudine());
                                    mGoogleMap.addMarker(new MarkerOptions().position(serviceMarker)
                                            .title(s.getLoc().getAdresa()));
                                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(serviceMarker));
                                }



                            }

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

    public void goToAboutService() {
        Intent itServ = new Intent(getApplicationContext(), AboutServiceActivity.class);
        itServ.putExtra("CurrentService", serviceToSend);
        startActivity(itServ);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;


    }

    private void getDeviceLocation() {
        /*
            getting the device current location
         */
        Log.d(TAG, "getDeviceLocation");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLoc = (Location) task.getResult();
                            currentLocation=new LatLng(currentLoc.getLatitude(),currentLoc.getLongitude());
                            mGoogleMap.addMarker(new MarkerOptions().position(currentLocation)
                                    .title("current location"));

                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    // map initialisation
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(SearchedServiceActivity.this);
                }
            }
        }
    }
}