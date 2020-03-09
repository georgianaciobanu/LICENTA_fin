package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.presenter.ServiceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class RequestsFragment extends Fragment {
    ArrayList<Request> requests;
    ListView listView;
    private static RequestsAdapter adapter;
    String TAG = "RequestFragmentActivity";
    Service currentService;
    OnGetDataListener listenerService;
    OnGetDataListener listenerRequest;
    Request request = new Request();
    View view;
    User currentClient;
    Integer userOrService=2;
    // 1- user 2- service
    ArrayList<Service> services;//services = new ArrayList<>();

    public static RequestsFragment newInstanceServ(Service serv) {
        RequestsFragment fragment = new RequestsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Service", serv);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static RequestsFragment newInstanceClient(User client) {
        RequestsFragment fragment = new RequestsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Client", client);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cereri, container, false);
        currentService = new Service();
        services = new ArrayList<>();
        requests = new ArrayList<>();



        listView = (ListView) view.findViewById(R.id.list_requests);
        final String currentUserEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        currentService = (Service) getArguments().getSerializable(
                "Service");
        if(currentService==null) {
           currentClient = (User) getArguments().getSerializable(
            "Client");
           userOrService=1;
           }

        listenerRequest = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    request = singleSnapshot.getValue(Request.class);
                    if (currentService == null) {
                        if (request != null && request.getClient().getEmail().equals(currentUserEmail)) {
                            try {
                                requests.add(request);
                            } catch (Exception e) {
                                Log.i(TAG, "Exception add request:" + e.toString());
                            }
                        }
                    } else if (currentClient == null) {
                        if (request != null && request.getService().getEmail().equals(currentUserEmail)) {
                            try {
                                requests.add(request);
                            } catch (Exception e) {
                                Log.i(TAG, "Exception add request:" + e.toString());
                            }
                        }
                    }
                }
                if (requests.size() > 0) {
                    adapter = new RequestsAdapter(view.getContext(), requests);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Request req= requests.get(position);
                            Intent itReq = new Intent(view.getContext(), RequestDetailsActivity.class);
                            itReq.putExtra("currentReq",req);
                            itReq.putExtra("userOrService",userOrService);
                            startActivity(itReq);
                        }
                    });

                } else {
                    Log.i(TAG, "Err updatin");
                }

                Log.i(TAG, requests.size() + " requests");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        };


        FirebaseFunctions.getRequestsFirebase( listenerRequest );


        return view;
    }


}