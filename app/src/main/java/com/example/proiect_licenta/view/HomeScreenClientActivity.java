package com.example.proiect_licenta.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    ActionBarDrawerToggle toggle;


    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    Fragment fragmentServ;
    Fragment fragmentClient;
    Fragment fragmentServBook;
    Fragment fragmentClientBook;
    User currentUser;
    Context context;
    ArrayList<User> users= new ArrayList<>();
    Service currentService;
    ProgressDialog sProgressDialog;
    View mHeaderView;
    TextView textViewUsername;
    TextView textViewEmail;
    ArrayList<Service> services= new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_client);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context=this;

        final String currentUserEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        LinearLayout rl = (LinearLayout) findViewById(R.id.menu_layout);
        View vi = inflater.inflate(R.layout.nav_header, null);





        listenerClient=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

                if (sProgressDialog == null) {
                    sProgressDialog = new ProgressDialog(context);
                    sProgressDialog.setMessage("Loading");
                    sProgressDialog.setIndeterminate(true);
                }

                sProgressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                drawer = findViewById(R.id.drawerLayoutHomeScreen);
                NavigationView navigationView = findViewById(R.id.nav_viewhomescreen);
                mHeaderView =  navigationView.getHeaderView(0);
                textViewUsername = (TextView) mHeaderView.findViewById(R.id.tw_usernameMenu);
                textViewEmail= (TextView) mHeaderView.findViewById(R.id.tw_emailMenu);



                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentUser = singleSnapshot.getValue(User.class);

                }
                    if(currentUser!=null ) {

                        fragmentClient = RequestsFragment.newInstanceClient(currentUser);
                        fragmentClientBook = BookingFragment.newInstanceClient(currentUser);
                        if (currentUser.getEmail().equals(currentUserEmail)) {
                            textViewUsername.setText(currentUser.getUsername());
                            textViewEmail.setText(currentUser.getEmail());

                        }

                        if (sProgressDialog != null && sProgressDialog.isShowing()) {
                            sProgressDialog.dismiss();
                        }

                        navigationView.inflateMenu(R.menu.drawermenu);


                        navigationView.setNavigationItemSelectedListener(HomeScreenClientActivity.this);

                        toggle = new ActionBarDrawerToggle(HomeScreenClientActivity.this, drawer, toolbar,
                                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                        drawer.addDrawerListener(toggle);
                        toggle.syncState();
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new SearchServiceFragment()).commit();
                            navigationView.setCheckedItem(R.id.it_search);
                        }



                    }  else  {
                        navigationView.inflateMenu(R.menu.drawermenuservice);
                        navigationView.setNavigationItemSelectedListener(HomeScreenClientActivity.this);

                        toggle = new ActionBarDrawerToggle(HomeScreenClientActivity.this, drawer, toolbar,
                                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                        drawer.addDrawerListener(toggle);
                        toggle.syncState();
                        if (savedInstanceState == null) {


                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new ServiceProfileFragment()).commit();
                            navigationView.setCheckedItem(R.id.it_serviceProfile);
                        }

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


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentService = singleSnapshot.getValue(Service.class);
                }

                if (currentService != null ) {
                        fragmentServ = RequestsFragment.newInstanceServ(currentService);
                        fragmentServBook = BookingFragment.newInstanceServ(currentService);


                        if (currentService.getEmail().equals(currentUserEmail)) {
                            textViewUsername.setText(currentService.getUsername());
                            textViewEmail.setText(currentService.getEmail());

                        }



                    }




                if (sProgressDialog != null && sProgressDialog.isShowing()) {
                    sProgressDialog.dismiss();
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

                if(currentService!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragmentServBook).commit();}
                else if(currentUser!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragmentClientBook).commit();
                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new BookingFragment()).commit();
                break;

            case R.id.it_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SetariFragment()).commit();
                break;
            case R.id.it_reviews:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReviewsFragment()).commit();
                break;

            case R.id.it_chat:
                sProgressDialog.isShowing();
                goToChat();
                break;

            case R.id.it_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchServiceFragment()).commit();
                break;
            case R.id.it_serviceProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ServiceProfileFragment()).commit();
                break;
            case R.id.it_serviceProfileUpdate:
                sProgressDialog.isShowing();
                goToAboutService();
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

    private void goToAboutService(){

        Intent intent = new Intent();
        intent.setClass(context, AboutServiceActivity.class);
        intent.putExtra("CurrentService", currentService);
        startActivityForResult(intent,0);


        if (sProgressDialog != null && sProgressDialog.isShowing()) {
            sProgressDialog.dismiss();
        }

    }

    private  void goToChat(){
        Intent intent = new Intent();
        intent.setClass(context, ChatConversationActivity.class);
        startActivity(intent);
    }
}