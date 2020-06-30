package com.example.proiect_licenta;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.view.HomeScreenClientActivity;
import com.example.proiect_licenta.view.LoginActivity;
import com.example.proiect_licenta.view.SignUpClientActivity;
import com.example.proiect_licenta.view.SignUpServiceActivity;
import com.example.proiect_licenta.view.UploadImageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.net.InetAddress;


public class MainActivity extends AppCompatActivity implements LocationListener {


    TextView signUpClient;
    TextView signUpService;
    String TAG="Main Activity";

    CardView logIn;
    EditText email;
    TextView forgetPass;
    EditText password;
    FirebaseAuth mFirebaseAuth;
    CheckBox checkedTextView;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    LocationManager locationManager;
    String provider;

    public static final String PREFS_NAME = "Login";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


  //firebase

    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isNetworkConnected() && !isInternetAvailable() ){
            Toast.makeText(MainActivity.this,"Momentan nu aveti acces la internet. Incercati mai tarziu",Toast.LENGTH_SHORT).show();

        }else {


            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            provider = locationManager.getBestProvider(new Criteria(), false);


            logIn = (CardView) findViewById(R.id.CardView_btnlogin);
            email = (EditText) findViewById(R.id.et_login_mail);
            password = (EditText) findViewById(R.id.et_login_pass);
            forgetPass = (TextView) findViewById(R.id.TWforgetpass);

            checkedTextView = (CheckBox) findViewById(R.id.checkedTextView);

            forgetPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("Doriti sa resetati parola?");

                    // set dialog message
                    alertDialogBuilder

                            .setCancelable(false)
                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String editEmail = email.getText().toString();
                                    if (editEmail != null) {
                                        FirebaseFunctions.forgetPass(editEmail);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Email necompletat!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            })
                            .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            });


            SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String username = pref.getString(PREF_USERNAME, null);
            String pass = pref.getString(PREF_PASSWORD, null);

            if (username != null || pass != null) {
                email.setText(username);
                password.setText(pass);
                checkedTextView.setChecked(true);


            }


         email.setText("gscservice@mail.com");
         password.setText("parola1234");

//            email.setText("client@mail.com");
//            password.setText("parola");




            mFirebaseAuth = FirebaseAuth.getInstance();


            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                    FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                    if (mFirebaseUser != null) {
                        Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, HomeScreenClientActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            logIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String emailS = email.getText().toString();
                    String pwd = password.getText().toString();
                    if (emailS.isEmpty()) {
                        email.setError("Please enter email id");
                        email.requestFocus();
                    } else if (pwd.isEmpty()) {
                        password.setError("Please enter your password");
                        password.requestFocus();
                    } else if (emailS.isEmpty() && pwd.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                    } else if (!(emailS.isEmpty() && pwd.isEmpty())) {
                        mFirebaseAuth.signInWithEmailAndPassword(emailS, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (checkedTextView.isChecked()) {
                                        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                                                .edit()
                                                .putString(PREF_USERNAME, email.getText().toString())
                                                .putString(PREF_PASSWORD, password.getText().toString())
                                                .commit();
                                    }
                                    goToHomeScreenClient();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                    }

                }

            });


            checkLocationPermission();
        }


        signUpClient = (TextView) findViewById(R.id.BTNsignUpClient);
        signUpService = (TextView) findViewById(R.id.BTNsignUpService);

        signUpService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSIGNupService();
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



    public void goToSIGNup() {
        Intent it = new Intent(this, SignUpClientActivity.class);
        startActivity(it);
    }

    public void goToSIGNupService() {
        Intent it = new Intent(this, SignUpServiceActivity.class);
        startActivity(it);
    }


    public void goToHomeScreenClient() {
        Intent it = new Intent(this, HomeScreenClientActivity.class);
        startActivity(it);
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permisiune locatie")
                        .setMessage("Permiteti apliocatiei sa foloseasca locatia dispozitiviului dmv?")
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        if(provider==null){
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            provider = locationManager.getBestProvider(new Criteria(), false);
                        }
                        locationManager.requestLocationUpdates(provider, 400, 1,MainActivity.this);
                    }

                } else {
                    //checkLocationPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(provider, 400, 1, MainActivity.this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
