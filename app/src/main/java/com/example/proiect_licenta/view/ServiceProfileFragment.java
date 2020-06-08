package com.example.proiect_licenta.view;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class ServiceProfileFragment extends Fragment {
    Intent it;
    OnGetDataListener listenerServ;
    Service service;
    FirebaseUser firebaseUser;
    Service currentService= new Service();
    ImageButton chatButtonServ;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_service_profile, container, false);
        service=new Service();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
       final String currentUserEmail= firebaseUser.getEmail();
        chatButtonServ=(ImageButton)view.findViewById(R.id.chatButtonServ);


        listenerServ=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    service = singleSnapshot.getValue(Service.class);
                    if(service.getEmail().equals(currentUserEmail)){
                        currentService=service;

//                        it = new Intent(view.getContext(), AboutServiceActivity.class);
//                        it.putExtra("CurrentService",currentService);
//                        startActivity(it);
                    }
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        };

        FirebaseFunctions.getServiceFirebase("email",currentUserEmail,listenerServ);


        chatButtonServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChat(view);
            }
        });
        return view;
    }

    public void goToChat(View view){
        Intent intent = new Intent(view.getContext(), ChatConversationActivity.class);
        startActivity(intent);
    }
}
