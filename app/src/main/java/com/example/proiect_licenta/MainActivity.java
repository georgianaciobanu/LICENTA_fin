package com.example.proiect_licenta;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.view.HomeScreenClientActivity;
import com.example.proiect_licenta.view.LoginActivity;
import com.example.proiect_licenta.view.SignUpClientActivity;
import com.example.proiect_licenta.view.SignUpServiceActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button logIn;
    Button signUpClient;
    Button signUpService;
    String TAG="Main Activity";


  //firebase

    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIn = (Button) findViewById(R.id.BTNlogin);
        signUpClient = (Button) findViewById(R.id.BTNsignUpClient);
        signUpService = (Button) findViewById(R.id.BTNsignUpService);

        signUpService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSIGNupService();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();

            }
        });
        signUpClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSIGNup();

            }
        });


    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        if(firebaseUser!=null){
//            Intent intent= new Intent(MainActivity.this, HomeScreenClientActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    public void goToLogin() {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }

    public void goToSIGNup() {
        Intent it = new Intent(this, SignUpClientActivity.class);
        startActivity(it);
    }

    public void goToSIGNupService() {
        Intent it = new Intent(this, SignUpServiceActivity.class);
        startActivity(it);
    }
}
