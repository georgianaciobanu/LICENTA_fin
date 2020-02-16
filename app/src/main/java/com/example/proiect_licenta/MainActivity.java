package com.example.proiect_licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proiect_licenta.view.LoginActivity;
import com.example.proiect_licenta.view.SearchLocationActivity;
import com.example.proiect_licenta.view.SignUpClientActivity;
import com.example.proiect_licenta.view.SignUpServiceActivity;

public class MainActivity extends AppCompatActivity {

    Button logIn;
    Button signUpClient;
    Button signUpService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //TODO:
        // delete this. Just for testing MAPS
        Intent it = new Intent(this, SearchLocationActivity.class);
        startActivity(it);

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
