package com.example.proiect_licenta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.view.LoginActivity;
import com.example.proiect_licenta.view.SignUpClientActivity;
import com.example.proiect_licenta.view.SignUpServiceActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button logIn;
    Button signUpClient;
    Button signUpService;
    private DatabaseReference mDatabase;
    FirebaseFirestore db;
    String TAG="Main Activity";

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

        connectionFirebase();
    }

    public void connectionFirebase(){

        Log.i(TAG,"connection Firebase");

        User c= new User();
        c.setEmail("mailClient@mail.ro");
        c.setUsername("clientusername");
        c.setPass("clientpass");
        c.setTelefon("0785699021");
         mDatabase = FirebaseDatabase.getInstance().getReference();
         mDatabase.child("users").child("user_0").setValue(c);

        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        db = FirebaseFirestore.getInstance();
// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


    }
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
