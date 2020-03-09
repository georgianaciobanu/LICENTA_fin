package com.example.proiect_licenta.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

    User currentClient;
    FirebaseUser firebaseUser;
    View view2;


    public static BookingFragment newInstanceServ(Service serv) {
        BookingFragment fragment = new BookingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Service", serv);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static BookingFragment newInstanceClient(User client) {
        BookingFragment fragment = new BookingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Client", client);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_programari, container, false);
        view2 = inflater.inflate(R.layout.booking_item_list, container, false);

        currentService = (Service) getArguments().getSerializable(
                "Service");
        if(currentService==null) {
            currentClient = (User) getArguments().getSerializable(
                    "Client");
        }



        services = new ArrayList<>();
        bookingList = new ArrayList<>();

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
                    if (currentClient == null) {
                        if (request != null && request.getService().getEmail().equals(currentUserEmail)) {
                            try {
                                bookingList.add(request);
                            } catch (Exception e) {
                                Log.i(TAG, "Exception add request:" + e.toString());
                            }
                        }
                    }
                    else{
                        if (request != null && request.getClient().getEmail().equals(currentUserEmail)) {
                            try {
                                bookingList.add(request);
                            } catch (Exception e) {
                                Log.i(TAG, "Exception add request:" + e.toString());
                            }
                        }
                    }
                }
                if (bookingList.size() > 0){
                    adapter = new BookingAdapter(view.getContext(), bookingList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });

                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        };

        FirebaseFunctions.getBookingFirebase(listenerRequest);





        return view;



    }








}