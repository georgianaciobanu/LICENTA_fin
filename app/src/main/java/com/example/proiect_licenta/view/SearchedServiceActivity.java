package com.example.proiect_licenta.view;


import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ServiceSwipeMapAdapter;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.MapMaker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.DescriptorProtos;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class SearchedServiceActivity extends AppCompatActivity implements OnMapReadyCallback {

    ArrayList<ServiceDataModel> dataModels;
    ViewPager viewPager;
    private static ServiceSwipeMapAdapter adapter;
    OnGetDataListener listenerService;
    Service service;
    ArrayList<Service> services = new ArrayList<>();
    ArrayList<Service> servicesToAdapter = new ArrayList<>();
    GoogleMap mGoogleMap;
    String TAG = "SearchedServiceActivity";
    SupportMapFragment mapFragment;
    Double distance;
    PhysicalLocation currentLocation;
   Double Latitude;
   Double Longitude;
    OnGetDataListener listenerLocation;
    LatLng deviceLoc;
    DatabaseReference reference;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
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
        Intent i = getIntent();
        produseSelectate = (ArrayList<String>) i.getSerializableExtra("produseSelectate");
        Latitude = (Double) i.getSerializableExtra("currentLocationLat");
        Longitude = (Double) i.getSerializableExtra("currentLocationLong");

        final PhysicalLocation deviceLocattion=new PhysicalLocation();
        deviceLocattion.setAdresa("Your Location");
        deviceLocattion.setLatitudine(Latitude);
        deviceLocattion.setLogitudine(Longitude);

        final String currentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        deviceLocattion.setId_corespondent(currentUserId);
        //locationInsertFirebase(deviceLocattion);

        listenerLocation=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentLocation = singleSnapshot.getValue(PhysicalLocation.class);
                    if(currentLocation.getId_corespondent().equals(currentUserId)){
                        singleSnapshot.getRef().removeValue();
                    }


                }

                locationInsertFirebase(deviceLocattion);



            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };


        FirebaseFunctions.getLocationFirebase("id_corespondent",deviceLocattion.getId_corespondent(),listenerLocation);

        deviceLoc = new LatLng(Latitude, Longitude);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapClient);
        mapFragment.getMapAsync(this);


        service = new Service();
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        dataModels = new ArrayList<>();


        viewPager.setPadding(50, 0, 100, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.aquamarine),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorBlue),
                getResources().getColor(R.color.colorAccent)
        };

        colors = colors_temp;

        viewPager.setBackgroundColor(getResources().getColor(R.color.Transparent));

//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
//                    viewPager.setBackgroundColor(
//
//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
//                    );
//                }
//
//                else {
//                    viewPager.setBackgroundColor(colors[colors.length - 1]);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


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
                            if (produseSelectate != null) {
                                if (service != null) {
                                    boolean ok = false;
                                    for (Serviciu s : service.getSevicii()) {
                                        if (produseSelectate.contains(s.getProdus()))
                                            ok = true;

                                    }
                                    if (ok) {

                                        services.add(service);

                                    }
                                }
                            }


                        }

                        if (services.size() > 0) {
                            mGoogleMap.addMarker(new MarkerOptions().position(deviceLoc).title("current location").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_maps_1)));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(deviceLoc));

                            for (Service s : services) {
                                LatLng serviceMarker = new LatLng(s.getLoc().getLatitudine(), s.getLoc().getLogitudine());
                                if (deviceLoc != null) {
                                    distance = CalculationByDistance(deviceLoc, serviceMarker);
                                    servicesTolist.put(distance, s);

                                }
                                mGoogleMap.addMarker(new MarkerOptions().position(serviceMarker)
                                        .title(s.getLoc().getAdresa()).icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_maps_1)));

                            }
                            ArrayList<Double> sortedDistance = new ArrayList<Double>(servicesTolist.keySet());
                            Collections.sort(sortedDistance, comp);

                            for (double d : sortedDistance) {
                                Log.i(TAG, " distance " + " " + d);
                                servicesToAdapter.add(servicesTolist.get(d));
                                dataModels.add(new ServiceDataModel(servicesTolist.get(d).getNumeService(), servicesTolist.get(d).getLoc().getAdresa(), servicesTolist.get(d).getProgram(), servicesTolist.get(d).getLoc()));
                            }
                            if (dataModels.size() > 0) {
                                adapter = new ServiceSwipeMapAdapter(dataModels, getApplicationContext());
                                viewPager.setAdapter(adapter);

                                PhysicalLocation s = dataModels.get(viewPager.getCurrentItem()).getLocatie();
                                LatLng curentServiceLoc = new LatLng(s.getLatitudine(), s.getLogitudine());

                                mGoogleMap.addMarker(new MarkerOptions().position(curentServiceLoc)
                                        .title(s.getAdresa()).icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_maps_1)));
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(curentServiceLoc));


                            }


                        }


                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                };


        FirebaseFunctions.getServicesFirebase(listenerService);

viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        PhysicalLocation s = dataModels.get(viewPager.getCurrentItem()).getLocatie();
        LatLng curentServiceLoc = new LatLng(s.getLatitudine(), s.getLogitudine());

        MarkerOptions nou=new MarkerOptions()
                .position(curentServiceLoc)
                .title(s.getAdresa())
                .snippet("Population: 4,627,300")
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_map_2));

        mGoogleMap.addMarker(nou);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(curentServiceLoc));
        float zoomLevel = (float) 15.0;
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curentServiceLoc, zoomLevel));
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;


    }




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


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vector= ContextCompat.getDrawable(context,vectorResId);
        vector.setBounds(0,0,vector.getIntrinsicWidth(),vector.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(vector.getIntrinsicWidth(),vector.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        vector.draw(canvas);
        return  BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void locationInsertFirebase(final PhysicalLocation location) {

        reference= FirebaseDatabase.getInstance().getReference("Location");
        String key = reference.push().getKey();
        reference.child(key).setValue(location);




    }

}