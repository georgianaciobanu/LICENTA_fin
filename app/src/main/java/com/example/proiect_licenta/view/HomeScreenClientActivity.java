package com.example.proiect_licenta.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.MainActivity;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeScreenClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    String TAG="HomeScreenClientActivity";

    OnGetDataListener listenerService;
    OnGetDataListener listenerClient;
    TextView usernameClient;
    TextView emailClient;
    ActionBarDrawerToggle toggle;
    Boolean okServ=false;
    Boolean okUser=false;

    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    Fragment fragmentServ;
    Fragment fragmentClient;
    User currentUser;
    ArrayList<User> users= new ArrayList<>();

    Service currentService;
    ArrayList<Service> services= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_client);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final String currentUserEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        LinearLayout rl = (LinearLayout) findViewById(R.id.menu_layout);
        View vi = inflater.inflate(R.layout.nav_header, null);
        usernameClient=(TextView)vi.findViewById(R.id.tw_usernameMenu) ;
        emailClient=(TextView)vi.findViewById(R.id.tw_emailMenu) ;

        //TODO: AFISEAZA IN MENIU INFO USERULUI/SERVICE

        usernameClient.setText("blabla");
        emailClient.setText("emailbla");

        listenerClient=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {
                Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentUser = singleSnapshot.getValue(User.class);
                }
                    if(currentUser!=null && okUser ) {
                        fragmentClient = RequestsFragment.newInstanceClient(currentUser);
                        if (currentUser.getEmail().equals(currentUserEmail)) {
                            usernameClient.setText(currentUser.getUsername());
                            emailClient.setText(currentUser.getEmail());

                        }
                    }else if(currentUser!=null && okUser==false){
                        okUser=true;
                    }
                    else if(currentUser==null && okUser){
                        FirebaseFunctions.getServiceFirebase("email",currentUserEmail,listenerService);
                    }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };

        listenerService=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {
                Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for(DataSnapshot singleSnapshot : data.getChildren()){
                    currentService = singleSnapshot.getValue(Service.class);
                    if(currentService!=null && okServ){
                        if(currentService.getEmail().equals(currentUserEmail)){
                            usernameClient.setText(currentService.getUsername());
                            emailClient.setText(currentService.getEmail());

                             fragmentServ = RequestsFragment.newInstanceServ(currentService);
                        }

                }else if(currentService!=null && okServ==false){
                    okServ=true;
                }
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };

        FirebaseFunctions.getUserFirebase(listenerClient, currentUserEmail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);








        drawer = findViewById(R.id.drawerLayoutHomeScreen);
        NavigationView navigationView = findViewById(R.id.nav_viewhomescreen);
        if(currentService==null && okServ) {
            navigationView.inflateMenu(R.menu.drawermenu);


            navigationView.setNavigationItemSelectedListener(this);

             toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchServiceFragment()).commit();
                navigationView.setCheckedItem(R.id.it_search);
            }
        }
        else   {
            navigationView.inflateMenu(R.menu.drawermenuservice);
            navigationView.setNavigationItemSelectedListener(this);

             toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ServiceProfileFragment()).commit();
                navigationView.setCheckedItem(R.id.it_serviceProfile);
        }
        }








    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_istoricCereri:
                if(currentService!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragmentServ).commit();}
                else if(currentUser!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragmentClient).commit();
                }
                break;
            case R.id.it_programari:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BookingFragment()).commit();
                break;
                //TODO: FRAGMENT BOOKING
            case R.id.it_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SetariFragment()).commit();
                break;
            case R.id.it_reviews:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReviewsFragment()).commit();
                break;

            case R.id.it_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchServiceFragment()).commit();
                break;
            case R.id.it_serviceProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ServiceProfileFragment()).commit();
                break;
            case R.id.it_logout:
                goToMainPage();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void  goToMainPage(){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}