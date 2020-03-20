package com.example.proiect_licenta.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.presenter.ServicesListAdapter;

import java.util.ArrayList;

public class ServicesListActivity extends AppCompatActivity {

   ArrayList<Serviciu> servicii= new ArrayList<>();

    ListView listView;
   ServicesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicii);

        Intent i= getIntent();
        servicii = (ArrayList<Serviciu>) i.getSerializableExtra("Servicii");

        listView = findViewById(R.id.listView);
        adapter = new ServicesListAdapter(getApplicationContext(), servicii,null);
        listView.setAdapter(adapter);



    }
}



