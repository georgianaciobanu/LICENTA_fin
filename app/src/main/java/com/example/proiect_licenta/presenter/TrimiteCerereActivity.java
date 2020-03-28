package com.example.proiect_licenta.presenter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.ProductsItem;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.view.ChooseServicesActivity;
import com.example.proiect_licenta.view.DateTimePickerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;


public class TrimiteCerereActivity extends AppCompatActivity {
    private ArrayList<ProductsItem> listaProduse;
    private ProductsAdaptor mAdapter;
    Spinner spinner;
    DatabaseReference reference;

    private TextView mDisplayDate;
    Button trimiteCerere;
    EditText detalii;
    Request request;
    TextView servicii;
    Service currentService=new Service();
    FirebaseUser firebaseUser;
    User cUser;
    String TAG="TrimiteCerereActivity";
    OnGetDataListener listenerUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimite_cerere);


        listenerUser = new

                OnGetDataListener() {
                    @Override
                    public void onStartFirebaseRequest() {

                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        for (DataSnapshot singleSnapshot : data.getChildren()) {

                            cUser = singleSnapshot.getValue(User.class);
                            if(cUser!=null) {
                                request.setClient(cUser);
                                requestInsertFirebase(request);
                            }
                        }




                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                };


        request = new Request();
        Intent i=getIntent();





if(getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.AboutServiceActivity")) {
    currentService = (Service) i.getSerializableExtra("CurrentService");
    request.setService(currentService);
}else if(getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.ChooseServicesActivity")){

    request = (Request) i.getSerializableExtra("requestBack");
    currentService=request.getService();
}
else if(getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.DateTimePickerActivity")) {
    request = (Request) i.getSerializableExtra("requestBack");
    currentService=request.getService();

}


        initList();
        spinner = findViewById(R.id.spinnerCategorii);
        mAdapter = new ProductsAdaptor(this, listaProduse);
        spinner.setAdapter(mAdapter);
        mDisplayDate = (TextView) findViewById(R.id.tw_alegeData);
        trimiteCerere= (Button)findViewById(R.id.btn_trimite);
        detalii= (EditText) findViewById(R.id.et_detaliiSuplimentare);
        servicii=(TextView)findViewById(R.id.tw_adaugaServicii);

        if(request.getDataProgramare()!=null){
            mDisplayDate.setText(request.getDataProgramare().toString());
        }


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itDate = new Intent(getApplicationContext(), DateTimePickerActivity.class);
                itDate.putExtra("request",request);
                startActivity(itDate);
                 }
        });


        servicii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itReq = new Intent(getApplicationContext(), ChooseServicesActivity.class);
                itReq.putExtra("request",request);
                if(request.getServicii()!=null)
                Log.i(TAG," DIN TRIMITE SPRE CHOOSE : "+request.getServicii().size());
                startActivity(itReq);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProductsItem clickedItem = (ProductsItem) parent.getItemAtPosition(position);
                String clickedProdus = clickedItem.getProdus();
                if(clickedProdus!=null) {
                    request.setProdus(clickedProdus);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        trimiteCerere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trimiteCererea(request);
            }
        });
    }


    private void initList() {
        listaProduse = new ArrayList<>();
        int img=0;
        for(String p :request.getService().getProduse()){
            if (p.equals("ELECTROCASNICE") )
                img=R.drawable.electrocasnice;
            if (p.equals("TELEFON/TABLETA"))
                img= R.drawable.telefon;
            if (p.equals("MASINA"))
                img= R.drawable.masina;
            if (p.equals("PC/LAPTOP"))
                img= R.drawable.pc;

            listaProduse.add(new ProductsItem(p, img));
        }

    }

    private void trimiteCererea(final Request request){


        request.setDataTrimiterii(Calendar.getInstance().getTime());

        if(detalii.getText().toString()!=null){
            request.setDetalii(detalii.getText().toString());
        }

        request.setStatus("trimis spre validare");

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference();
        if(firebaseUser!=null) {
            String currentUserEmail = firebaseUser.getEmail();
            listenerUser.onStartFirebaseRequest();
            FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(currentUserEmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                 listenerUser.onSuccess(dataSnapshot);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listenerUser.onFailed(databaseError);
                }
            });
        }




    }


    public void requestInsertFirebase(final Request request) {

        reference= FirebaseDatabase.getInstance().getReference("Request");
        String key = reference.push().getKey();
        request.setRequestId(key);
        reference.child(request.getRequestId()).setValue(request);




    }
}
