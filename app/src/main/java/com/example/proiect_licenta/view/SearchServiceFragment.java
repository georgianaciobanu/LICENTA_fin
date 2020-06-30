package com.example.proiect_licenta.view;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ChatMessage;
import com.example.proiect_licenta.view.SearchedServiceActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchServiceFragment extends Fragment {
    ImageButton cauta;
    ListView listView;
    ArrayList<String> produseSelectate;
    CheckBox cb_pc;
    CheckBox cb_elc;
    CheckBox cb_mas;
    CheckBox cb_tel;
    Location currentLoc;
    ChatMessage chatMessage= new ChatMessage();
    FirebaseDatabase database;
    DatabaseReference messageReference;
    View view;
    String currentUserEmail;
    FirebaseUser firebaseUser;
    String TAG = "SearchServiceFragment";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = true;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_search, container, false);
        listView = (ListView) view.findViewById(R.id.listViewService);
        cauta = (ImageButton) view.findViewById(R.id.btn_search);
        cb_pc=(CheckBox)view.findViewById(R.id.Cbox_PC);
        cb_elc=(CheckBox)view.findViewById(R.id.Cbox_Electrocasnice);
        cb_mas=(CheckBox)view.findViewById(R.id.Cbox_Masina);
        cb_tel=(CheckBox)view.findViewById(R.id.Cbox_Telefon);
        produseSelectate= new ArrayList<>();
        messageReference = database.getInstance().getReference().child("ChatMessage");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUserEmail= firebaseUser.getEmail();

        getDeviceLocation();


        String time=Calendar.getInstance().getTime().toString();
        final SharedPreferences prefs = getActivity().getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastTime", time);
        editor.commit();

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




    public void goToServicesList() {
        Intent it = new Intent(getActivity(), SearchedServiceActivity.class);
        it.putExtra("produseSelectate",produseSelectate);
        it.putExtra("currentLocationLat",currentLoc.getLatitude());
        it.putExtra("currentLocationLong",currentLoc.getLongitude());
        startActivity(it);
    }


    private void getDeviceLocation() {

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
                .setContentText( message)
                .setOngoing(true);

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
