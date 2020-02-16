package com.example.proiect_licenta.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.proiect_licenta.R;

public class HomeScreenServiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawer;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_service);

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
