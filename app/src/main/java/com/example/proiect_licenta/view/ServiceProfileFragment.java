package com.example.proiect_licenta.view;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ChatMessage;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class ServiceProfileFragment extends Fragment {
    Intent it;
    OnGetDataListener listenerServ;
    Service service;
    FirebaseUser firebaseUser;

    Service currentService= new Service();
   // ImageView chatButtonServ;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference messageReference;
    Request request= new Request();
    ChatMessage chatMessage= new ChatMessage();
    int id;
    String lastChildKey;
    String currentUserEmail;
    TextView tw_chatservice;



    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reference = database.getInstance().getReference().child("Request");
        messageReference = database.getInstance().getReference().child("ChatMessage");
        view=inflater.inflate(R.layout.fragment_service_profile, container, false);
        service=new Service();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUserEmail= firebaseUser.getEmail();
        //chatButtonServ=(ImageView)view.findViewById(R.id.chatButtonServ);
        tw_chatservice=(TextView) view.findViewById(R.id.tw_chatservice);

        String time=Calendar.getInstance().getTime().toString();
        final SharedPreferences prefs = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastTime", time);
        editor.commit();




        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(getActivity()!=null) {
                SharedPreferences prefs1 = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);

                    String date = prefs1.getString("lastTime", null);

                    Date lastTime = null;
                    try {
                        lastTime = stringToDate(date, "MMM d, yyyy HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        request = child.getValue(Request.class);

                        if (request.getService().getEmail().equals(currentUserEmail) && request.getDataTrimiterii().getTime() > lastTime.getTime() && request.getDataTrimiterii().getTime() <= Calendar.getInstance().getTime().getTime()) {
                            if (request.getStatus().equals("anulata")) {
                                notification(request.getClient().getUsername() + " a anulat programarea.");

                            } else {
                                notification(request.getClient().getUsername() + " a trimis o cerere noua!");
                            }
                        }

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(getActivity()!=null) {
                    SharedPreferences prefs1 = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);

                    String date = prefs1.getString("lastTime", null);

                    Date lastTime = null;
                    try {
                        lastTime = stringToDate(date, "MMM d, yyyy HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        chatMessage = child.getValue(ChatMessage.class);

                        if (chatMessage.getMessageFor().equals(currentUserEmail) && chatMessage.getMessageTime().getTime() > lastTime.getTime() && chatMessage.getMessageTime().getTime() <= Calendar.getInstance().getTime().getTime()) {

                                notification(chatMessage.getMessageUser() + " v-a trimis un mesaj nou!");
                            }


                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

                    }
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        };

        FirebaseFunctions.getServiceFirebase("email",currentUserEmail,listenerServ);


        tw_chatservice.setOnClickListener(new View.OnClickListener() {
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



    private void notification(String message){



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

        String currentTime=Calendar.getInstance().getTime().toString();
        SharedPreferences prefs = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastTime", currentTime);
        editor.commit();
    }

    public void onDestroy() {

        super.onDestroy();
        String currentTime=Calendar.getInstance().getTime().toString();
        SharedPreferences prefs = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastTime", currentTime);
        editor.commit();

    }
    public void onPause() {
        super.onPause();
        String currentTime=Calendar.getInstance().getTime().toString();
        SharedPreferences prefs = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastTime", currentTime);
        editor.commit();
    }

    public void onStop() {
        super.onStop();
        String currentTime=Calendar.getInstance().getTime().toString();
        SharedPreferences prefs = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastTime", currentTime);
        editor.commit();
    }


    private Date stringToDate(String aDate,String aFormat) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        Date parsedDate = sdf.parse(aDate);
       return parsedDate;




    }
}
