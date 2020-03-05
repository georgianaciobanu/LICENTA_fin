package com.example.proiect_licenta.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.BookingAdapter;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BookingFragment extends Fragment {

    ArrayList<Request> bookingList;
    ListView listView;
    private  BookingAdapter adapter;
    Boolean ok = true;
    String TAG = "BookingFragmentActivity";
    Service currentService;
    OnGetDataListener listenerService;
    OnGetDataListener listenerRequest;
    Request request = new Request();
    View view;
    ArrayList<Service> services;
    User user;
    FirebaseUser firebaseUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_programari, container, false);


        currentService = new Service();
        services = new ArrayList<>();
        bookingList = new ArrayList<>();
        user=new User();
        final String currentUserEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        listView = (ListView) view.findViewById(R.id.list_booking);

        listenerRequest = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    request = singleSnapshot.getValue(Request.class);
                    if (request != null && request.getClient().getEmail().equals(currentUserEmail)) {
                        try {
                            bookingList.add(request);
                        } catch (Exception e) {
                            Log.i(TAG, "Exception add request:" + e.toString());
                        }
                    }
                }
                if (bookingList.size() > 0){ //&& ok) {
                    adapter = new BookingAdapter(view.getContext(), bookingList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent itReq = new Intent(view.getContext(), RequestDetailsActivity.class);
                            startActivity(itReq);
                        }
                    });
               // } else if (ok == false) {
                   // ok = true;
                } else {
                    Log.i(TAG, "Err updatin");
                }

                Log.i(TAG, bookingList.size() + " requests");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        };
//        listenerService = new
//
//                OnGetDataListener() {
//                    @Override
//                    public void onStartFirebaseRequest() {
//                        Toast.makeText(getContext(), "Loading...", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onSuccess(DataSnapshot data) {
//                        for (DataSnapshot singleSnapshot : data.getChildren()) {
//                            currentService = singleSnapshot.getValue(Service.class);
//                            if (currentService != null) {
//                                services.add(currentService);
//                            }
//                        }
//
//                        if (services.size() > 0 && ok) {
//                            adapter = new BookingAdapter (view.getContext(), bookingList);
//                            listView.setAdapter(adapter);
//                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    Intent itReq = new Intent(view.getContext(), RequestDetailsActivity.class);
//                                    startActivity(itReq);
//                                }
//                            });
//                        } else if (ok == false) {
//                            ok = true;
//                        } else {
//                            Log.i(TAG, "Err updatin");
//                        }
//
//                        Log.i(TAG, services.size() + " services");
//                    }
//
//                    @Override
//                    public void onFailed(DatabaseError databaseError) {
//                        Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
//                    }
//                };

        //FirebaseFunctions.findService("Service", listenerService);

        FirebaseFunctions.getBookingFirebase(listenerRequest);


        return view;



    }






}