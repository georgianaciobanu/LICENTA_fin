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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.proiect_licenta.MainActivity;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class HomeScreenClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    String TAG="HomeScreenClientActivity";

    TextView usernameClient;
    TextView emailClient;

    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_client);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout rl = (LinearLayout) findViewById(R.id.menu_layout);
        View vi = inflater.inflate(R.layout.nav_header, null);
        usernameClient=(TextView)vi.findViewById(R.id.tw_usernameMenu) ;
        emailClient=(TextView)vi.findViewById(R.id.tw_emailMenu) ;





        drawer = findViewById(R.id.drawerLayoutclient);
        NavigationView navigationView = findViewById(R.id.nav_viewClient);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SearchServiceFragment()).commit();
            navigationView.setCheckedItem(R.id.it_search);
        }

        String currentUserEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        readUserFirebase(currentUserEmail);

    }
    public void readUserFirebase(final String email){

        DatabaseReference mDatabaseRef =FirebaseDatabase.getInstance().getReference("User");

        Query query=mDatabaseRef.orderByChild("email").equalTo(email);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){


                    User models=data.getValue(User.class);
                    Log.i(TAG, "User details: " + models.getUsername());
                    usernameClient.setText(models.getUsername());
                    emailClient.setText(models.getEmail());



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
            case R.id.it_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchServiceFragment()).commit();
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