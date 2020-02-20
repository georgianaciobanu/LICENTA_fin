package com.example.proiect_licenta;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.proiect_licenta.model.Service;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

       User c= new User();

        c.setEmail("mailClient@mail.ro");
        c.setUsername("clientusername");
        c.setPass("clientpass");
        c.setTelefon("0785699021");

        User c2= new User();

        c2.setEmail("client@mail.ro");
        c2.setUsername("username");
        c2.setPass("pass22");
        c2.setTelefon("0785699021");

        //userRegisterFirebase(c2);

         Service serv= new Service();
         serv.setUsername("serverUsername");
         serv.setEmail("server@email.ro");
         serv.setPass("serverPass");
         serv.setTelefon("0789640032");
         serv.setDescriere("descrierea service-ului de masini");
         serv.setProgram("10-19");
         serv.setNumeService("service masini");
         serv.setProprietar("vasile masina");


        serviceRegisterFirebase(serv);
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent intent= new Intent(MainActivity.this, HomeScreenClientActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void userRegisterFirebase(final User user){
        fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser= fAuth.getCurrentUser();
                    String userid=firebaseUser.getUid();

                    reference= FirebaseDatabase.getInstance().getReference("User").child(userid);

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap. put("username",user.getUsername());
                    hashMap.put("phoneNumber",user.getTelefon());
                    hashMap.put("email",user.getEmail());
                    hashMap.put("password",user.getPass());



                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                              Log.i(TAG,"user inserted");
                        }
                    });
                }
                else{
                    Log.i(TAG,"error user");
                }
            }
        });

    }

    public void serviceRegisterFirebase(final Service service){
        fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(service.getEmail(),service.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser= fAuth.getCurrentUser();
                    String userid=firebaseUser.getUid();

                    reference= FirebaseDatabase.getInstance().getReference("Service").child(userid);

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap. put("username",service.getUsername());
                    hashMap.put("phoneNumber",service.getTelefon());
                    hashMap.put("email",service.getEmail());
                    hashMap.put("password",service.getPass());
                    hashMap.put("program",service.getProgram());
                    hashMap.put("owner",service.getProprietar());
                    hashMap.put("description",service.getDescriere());
                    hashMap.put("name",service.getNumeService());




                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Log.i(TAG,"service inserted");
                        }
                    });
                }
                else{
                    Log.i(TAG,"error service");
                }
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
