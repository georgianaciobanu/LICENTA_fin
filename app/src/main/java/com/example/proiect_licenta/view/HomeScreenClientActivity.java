package com.example.proiect_licenta.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.proiect_licenta.MainActivity;
import com.example.proiect_licenta.R;
import com.google.android.material.navigation.NavigationView;


public class HomeScreenClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_client);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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