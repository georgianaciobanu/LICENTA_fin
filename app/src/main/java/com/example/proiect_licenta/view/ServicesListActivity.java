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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
   Service currentService;
    OnGetDataListener listenerUpdateServicii;
    ProgressDialog pg;
    ArrayList<Serviciu> serviciiFirebase;
    String serviceEmail;
    FirebaseUser firebaseUser;
    String serviceId;
    LinearLayout vwParentRow ;
    Button btnChild ;
    ImageView   images ;
    LinearLayout   vwParentRow2 ;
    EditText   myTitle;
    EditText    myDescription ;
    EditText  myDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicii);
         adauga=(Button)findViewById(R.id.btn_add);
        adauga.setVisibility(View.INVISIBLE);

        Intent i= getIntent();
        currentService = (Service) i.getSerializableExtra("Service");
        servicii = currentService.getSevicii();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        serviceId=firebaseUser.getUid();
        serviceEmail=firebaseUser.getEmail();


        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddServicesListActivity.class);
                intent.putExtra("Service", currentService);
                startActivityForResult(intent,0);
            }
        });

        if(getCallingActivity()!=null) {
            if (getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.AboutServiceActivity")) {
                req = 0;
                adauga.setVisibility(View.VISIBLE);
            }
        }

        listView = findViewById(R.id.listView);
        adapter = new ServicesListAdapter(getApplicationContext(), servicii,null,req);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                       final int position=i;
                        vwParentRow = (LinearLayout)view.getParent();
                         btnChild = (Button)vwParentRow.getChildAt(0);
                         images = (ImageView)vwParentRow.getChildAt(1);
                        vwParentRow2 = (LinearLayout) vwParentRow.getChildAt(2);
                          myTitle = (EditText)vwParentRow2.getChildAt(0);
                          myDescription = (EditText)vwParentRow2.getChildAt(1);
                          myDetails = (EditText)vwParentRow2.getChildAt(2);



                       listenerUpdateServicii = new OnGetDataListener() {
                           @Override
                           public void onStartFirebaseRequest() {

                           }

                           @Override
                           public void onSuccess(DataSnapshot data) {

                               for (DataSnapshot singleSnapshot : data.getChildren()) {
                                   currentService  = singleSnapshot.getValue(Service.class);
                                   if (currentService.getEmail().equals(serviceEmail)) {

                                       serviciiFirebase=currentService.getSevicii();
                                       for(Serviciu s: serviciiFirebase){
                                           if(s.getDenumire().equals(servicii.get(position).getDenumire())){

                                               s.setDenumire( myTitle.getText().toString());
                                               s.setDetalii(myDetails.getText().toString());
                                               s.setPret(Double.valueOf(myDescription.getText().toString()));

                                               currentService.setSevicii(serviciiFirebase);



                                           }
                                       }
                                       FirebaseFunctions.updateServicii(currentService.getServiceId(),serviciiFirebase);




                                       //pg.hide();
                                   }
                               }


                               //pg.hide();


                           }

                           @Override
                           public void onFailed(DatabaseError databaseError) {
                               pg.hide();

                           }
                       };




                       FirebaseFunctions.UpdateServicii(serviceEmail, listenerUpdateServicii);

                  }
              });








    }
}



