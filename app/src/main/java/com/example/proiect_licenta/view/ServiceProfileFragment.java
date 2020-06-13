package com.example.proiect_licenta.view;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.Queue;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class ServiceProfileFragment extends Fragment {
    Intent it;
    OnGetDataListener listenerServ;
    Service service;
    FirebaseUser firebaseUser;
    Service currentService= new Service();
    ImageButton chatButtonServ;
    FirebaseDatabase database;
    DatabaseReference reference;
    Request request= new Request();
    int id;
    String lastChildKey;
    String currentUserEmail;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reference = database.getInstance().getReference().child("Request");

        view=inflater.inflate(R.layout.fragment_service_profile, container, false);
        service=new Service();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUserEmail= firebaseUser.getEmail();
        chatButtonServ=(ImageButton)view.findViewById(R.id.chatButtonServ);

//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists()){
//                    id = (int)dataSnapshot.getChildrenCount();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//
//            reference.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    request = dataSnapshot.getValue(Request.class);
//
//                    if (request.getService().getEmail().equals(currentUserEmail)) {
//
//                        lastChildKey = dataSnapshot.getKey();
//
//                    }
//
//
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        if(lastChildKey!=null) {
//            Query query = reference.orderByKey().startAt(lastChildKey);
//            query.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    request = dataSnapshot.getValue(Request.class);
//
//                    if (request.getService().getEmail().equals(currentUserEmail)) {
//
//                        notification();
//
//                    }
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }


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



    private void notification(){


        String message = " Aveti o cerere noua!";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = (NotificationManager)getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(),"n")
                .setContentText("Code Sphere")
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setAutoCancel(true)
                .setContentText( message);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(view.getContext());
        managerCompat.notify(999,builder.build());
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(lastChildKey!=null) {
//            Query query = reference.orderByKey().startAt(lastChildKey);
//            query.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    request = dataSnapshot.getValue(Request.class);
//
//                    if (request.getService().getEmail().equals(currentUserEmail)) {
//
//                        notification();
//
//                    }
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
       // }

    //}


}
