package com.example.proiect_licenta;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.proiect_licenta.view.LoginActivity;
import com.example.proiect_licenta.view.SignUpClientActivity;
import com.example.proiect_licenta.view.SignUpServiceActivity;
import com.example.proiect_licenta.view.UploadImageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
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
        //Intent it = new Intent(this, ServiceDetailsActivity.class);
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

    public void  goToImageUpload(){
        Intent it = new Intent(this, UploadImageActivity.class);
        it.putExtra("ServiceEmail","ricaaa@mail.com" );
        startActivity(it);

    }
}
