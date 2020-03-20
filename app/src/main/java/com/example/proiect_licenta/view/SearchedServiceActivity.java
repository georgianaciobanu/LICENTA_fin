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
import com.google.protobuf.DescriptorProtos;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SearchedServiceActivity extends AppCompatActivity implements OnMapReadyCallback {

    ArrayList<ServiceDataModel> dataModels;
    ListView listView;
    private static ServiceAdapter adapter;
    OnGetDataListener listenerService;
    Service service;
    ArrayList<Service> services = new ArrayList<>();
    ArrayList<Service> servicesToAdapter = new ArrayList<>();
    GoogleMap mGoogleMap;
    String TAG = "SearchedServiceActivity";
    SupportMapFragment mapFragment;
    Double distance;
   Double Latitude;
   Double Longitude;
    LatLng deviceLoc;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = true;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    ArrayList<String> produseSelectate;
    Map< Double,Service> servicesTolist ;

    Comparator<Double> comp = new Comparator<Double>() {
        @Override
        public int compare(Double a, Double b) {
            return a.compareTo(b);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_service);
        servicesTolist = new HashMap<>();
        Intent i=getIntent();
        produseSelectate=(ArrayList<String>)i.getSerializableExtra("produseSelectate");
        Latitude=(Double) i.getSerializableExtra("currentLocationLat");
        Longitude=(Double) i.getSerializableExtra("currentLocationLong");
        deviceLoc=new LatLng(Latitude,Longitude);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapClient);
        mapFragment.getMapAsync(this);






        service = new Service();
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
                            if(produseSelectate!=null ) {
                                if (service != null ) {
                                   boolean ok=false;
                                    for(Serviciu s: service.getSevicii()){
                                        if(produseSelectate.contains(s.getProdus()))
                                            ok=true;

                                    }
                                    if(ok) {

                                        services.add(service);

                                    }
                                }
                            }



                        }

                        if (services.size() > 0) {
                            mGoogleMap.addMarker(new MarkerOptions().position(deviceLoc).title("current location"));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(deviceLoc));

                            for (Service s : services) {
                                LatLng serviceMarker = new LatLng(s.getLoc().getLatitudine(), s.getLoc().getLogitudine());
                                if (deviceLoc != null) {
                                    distance = CalculationByDistance(deviceLoc, serviceMarker);
                                    servicesTolist.put(distance,s);

                                }
                                mGoogleMap.addMarker(new MarkerOptions().position(serviceMarker)
                                        .title(s.getLoc().getAdresa()));

                            }
                            ArrayList<Double> sortedDistance=new ArrayList<Double>(servicesTolist.keySet());
                            Collections.sort(sortedDistance,comp);

                            for(double d: sortedDistance){
                                Log.i(TAG, " distance "+ " " +d);
                                servicesToAdapter.add(servicesTolist.get(d));
                                dataModels.add(new ServiceDataModel(servicesTolist.get(d).getNumeService(), servicesTolist.get(d).getLoc().getAdresa()));
                            }
                            if (dataModels.size() > 0) {
                                adapter = new ServiceAdapter(getApplicationContext(), dataModels);
                                listView.setAdapter(adapter);


                            }



                        }




                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                };



        FirebaseFunctions.getServicesFirebase(listenerService);



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;


    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d(TAG, "onRequestPermissionsResult");
//        mLocationPermissionsGranted = false;
//
//        switch (requestCode) {
//            case LOCATION_PERMISSION_REQUEST_CODE: {
//                if (grantResults.length > 0) {
//                    for (int i = 0; i < grantResults.length; i++) {
//                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                            mLocationPermissionsGranted = false;
//                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
//                            return;
//                        }
//                    }
//                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
//                    mLocationPermissionsGranted = true;
//                    // map initialisation
//                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//                    mapFragment.getMapAsync(SearchedServiceActivity.this);
//                }
//            }
//        }
//    }



    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


}