package com.example.proiect_licenta.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.presenter.ServicesListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

public class ServicesListActivity extends AppCompatActivity {

   ArrayList<Serviciu> servicii= new ArrayList<>();

    ListView listView;
   ServicesListAdapter adapter;
   int req=1;
   Button adauga;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicii);
         adauga=(Button)findViewById(R.id.btn_add);
        adauga.setVisibility(View.INVISIBLE);
        Intent i= getIntent();
        servicii = (ArrayList<Serviciu>) i.getSerializableExtra("Servicii");







        if(getCallingActivity()!=null) {
            if (getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.AboutServiceActivity")) {
               req=0;
                adauga.setVisibility(View.VISIBLE);

            }
        }
        listView = findViewById(R.id.listView);
        adapter = new ServicesListAdapter(getApplicationContext(), servicii,null,req);
        listView.setAdapter(adapter);



    }
}



