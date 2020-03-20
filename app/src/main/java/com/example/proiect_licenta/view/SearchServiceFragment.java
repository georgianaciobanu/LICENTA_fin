package com.example.proiect_licenta.view;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.view.SearchedServiceActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchServiceFragment extends Fragment {
    Button cauta;
    ListView listView;
    ArrayList<String> produseSelectate;
    CheckBox cb_pc;
    CheckBox cb_elc;
    CheckBox cb_mas;
    CheckBox cb_tel;
    Location currentLoc;
    View view;
    String TAG = "SearchServiceFragment";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = true;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_search, container, false);
        listView = (ListView) view.findViewById(R.id.listViewService);
        cauta = (Button) view.findViewById(R.id.btn_search);
        cb_pc=(CheckBox)view.findViewById(R.id.Cbox_PC);
        cb_elc=(CheckBox)view.findViewById(R.id.Cbox_Electrocasnice);
        cb_mas=(CheckBox)view.findViewById(R.id.Cbox_Masina);
        cb_tel=(CheckBox)view.findViewById(R.id.Cbox_Telefon);
        produseSelectate= new ArrayList<>();

        getDeviceLocation();

        cauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_pc.isChecked())
                    produseSelectate.add(cb_pc.getText().toString());
                if(cb_elc.isChecked())
                    produseSelectate.add(cb_elc.getText().toString());
                if(cb_mas.isChecked())
                    produseSelectate.add(cb_mas.getText().toString());
                if(cb_tel.isChecked())
                    produseSelectate.add(cb_tel.getText().toString());

                goToServicesList();
            }
        });








        return view;
    }

    //TODO: cauta in functie de produse si distanta


    public void goToServicesList() {
        Intent it = new Intent(getActivity(), SearchedServiceActivity.class);
        it.putExtra("produseSelectate",produseSelectate);
        it.putExtra("currentLocationLat",currentLoc.getLatitude());
        it.putExtra("currentLocationLong",currentLoc.getLongitude());
        startActivity(it);
    }


    private void getDeviceLocation() {
        /*
            getting the device current location
         */
        Log.d(TAG, "getDeviceLocation");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.getContext());

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                             currentLoc = (Location) task.getResult();
                            //currentLocation=new LatLng(currentLoc.getLatitude(),currentLoc.getLongitude());





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

                }
            }
        }
    }
}
