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

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class HomeScreenServiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawer;

    OnGetDataListener listener;
    TextView usernameClient;
    TextView emailClient;

    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    Service currentService;
    ArrayList<Service> services= new ArrayList<>();

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_service);

       LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       final String currentUserEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
       LinearLayout rl = (LinearLayout) findViewById(R.id.menu_layout);
       View vi = inflater.inflate(R.layout.nav_header, null);

       usernameClient=(TextView)vi.findViewById(R.id.tw_usernameMenu) ;
       emailClient=(TextView)vi.findViewById(R.id.tw_emailMenu) ;

       //TODO: AFISEAZA IN MENIU INFO USERULUI/SERVICE

       usernameClient.setText("blabla");
       emailClient.setText("emailbla");

       listener=new OnGetDataListener() {
           @Override
           public void onStartFirebaseRequest() {
               Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_LONG).show();
           }

           @Override
           public void onSuccess(DataSnapshot data) {
               for(DataSnapshot singleSnapshot : data.getChildren()){
                   currentService = singleSnapshot.getValue(Service.class);
                   if(currentService!=null){
                       services.add(currentService);
                       if(currentService.getEmail().equals(currentUserEmail)){
                           usernameClient.setText(currentService.getUsername());
                           emailClient.setText(currentService.getEmail());
                       }
                   }
               }


           }

           @Override
           public void onFailed(DatabaseError databaseError) {
               Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
           }
       };


       toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

       drawer = findViewById(R.id.drawerLayoutservice);
       NavigationView navigationView = findViewById(R.id.nav_viewService);
       navigationView.setNavigationItemSelectedListener(this);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
               R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.addDrawerListener(toggle);
       toggle.syncState();

       if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   new ServiceProfileFragment()).commit();
           navigationView.setCheckedItem(R.id.it_serviceProfile);
       }

       FirebaseFunctions.getServiceFirebase("email",currentUserEmail,listener);
   }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_istoricCereri:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RequestsFragment()).commit();
                break;
            case R.id.it_programari:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BookingFragment()).commit();
                break;
            case R.id.it_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SetariFragment()).commit();
                break;
            case R.id.it_reviews:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReviewsFragment()).commit();
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
       //MainActivity
      Intent it = new Intent(this, AboutServiceActivity.class);
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
