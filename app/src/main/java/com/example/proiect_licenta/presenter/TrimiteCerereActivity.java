package com.example.proiect_licenta.presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ProductsItem;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.view.ChooseServicesActivity;
import com.example.proiect_licenta.view.RequestDetailsActivity;
import com.example.proiect_licenta.view.TimePickerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrimiteCerereActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private ArrayList<ProductsItem> listaProduse;
    private ProductsAdaptor mAdapter;
    Spinner spinner;
    DatabaseReference reference;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayHour;
    Button trimiteCerere;
    EditText detalii;
    Date dataProgramare;
    Request request;
    TextView servicii;
    Service currentService;
   ArrayList<Serviciu> serviciiSelectate= new ArrayList<Serviciu>();
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    User cUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimite_cerere);
        initList();
        currentService= new Service();

        Intent i = getIntent();
        serviciiSelectate = (ArrayList<Serviciu>) i.getSerializableExtra("ListaServicii");

        currentService = (Service) i.getSerializableExtra("CurrentService");

        spinner = findViewById(R.id.spinnerCategorii);
        mAdapter = new ProductsAdaptor(this, listaProduse);
        spinner.setAdapter(mAdapter);
        mDisplayDate = (TextView) findViewById(R.id.tw_alegeData);
        mDisplayHour=(TextView)findViewById(R.id.tw_alegeOra);
        request = new Request();
        trimiteCerere= (Button)findViewById(R.id.btn_trimite);
        detalii= (EditText) findViewById(R.id.et_detaliiSuplimentare);
        servicii=(TextView)findViewById(R.id.tw_adaugaServicii);
       final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        servicii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itReq = new Intent(getApplicationContext(), ChooseServicesActivity.class);
                itReq.putExtra("ListaServicii",serviciiSelectate);
                startActivity(itReq);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProductsItem clickedItem = (ProductsItem) parent.getItemAtPosition(position);
                String clickedProdus = clickedItem.getProdus();
                request.setProdus(clickedProdus);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TrimiteCerereActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dateString = day + "-" + month + "-" + year;
                mDisplayDate.setText(dateString);


                try {

                    dataProgramare    = format.parse ( dateString );
                    Date dataTrimitere=  Calendar.getInstance().getTime();
                    request.setDataProgramare(dataProgramare);
                    request.setDataTrimiterii(dataTrimitere);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        mDisplayHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
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
        listaProduse.add(new ProductsItem("Electrocasnice", R.drawable.electrocasnice));
        listaProduse.add(new ProductsItem("Telefon/Tableta", R.drawable.telefon));
        listaProduse.add(new ProductsItem("Masina", R.drawable.masina));
        listaProduse.add(new ProductsItem("PC/Laptop", R.drawable.pc));
    }

    private void trimiteCererea(final Request request){

        if(detalii.getText().toString()!=null){
            request.setDetalii(detalii.getText().toString());
        }
        request.setServicii(serviciiSelectate);
        request.setService(currentService);
        request.setStatus("trimis spre validare");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference();
        if(firebaseUser!=null) {
            String userKey = firebaseUser.getUid();

            reference.child("User").child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   cUser = dataSnapshot.getValue(User.class);
                   if(cUser!=null) {
                       request.setClient(cUser);
                   }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }



        requestInsertFirebase(request);
        Toast.makeText(getApplicationContext(), request.getProdus()+ " "+ request.getDetalii()+ " "+ request.getDataProgramare(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        mDisplayHour.setText(hourOfDay + ": " + minute);
    }

    public void requestInsertFirebase(final Request request) {

        reference= FirebaseDatabase.getInstance().getReference("Request");
        String key = reference.push().getKey();
        request.setRequestId(key);
        reference.child(request.getRequestId()).setValue(request);




    }
}
