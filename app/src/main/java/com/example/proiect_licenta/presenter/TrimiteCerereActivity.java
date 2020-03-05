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
import com.example.proiect_licenta.model.Product;
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
    Request requestServicii;
    Date dataTrimitere;
    Request request;
    TextView servicii;
    Service currentService=new Service();
    Service currentServiceFromDB;
   ArrayList<Serviciu> serviciiSelectate= new ArrayList<Serviciu>();
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    User cUser;
    String TAG="TrimiteCerereActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimite_cerere);

        requestServicii=new Request();
        currentServiceFromDB= new Service();
        request = new Request();
        Intent i=getIntent();

//if(getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.AboutServiceActivity")){
    currentServiceFromDB = (Service) i.getSerializableExtra("CurrentService");
  if(currentServiceFromDB!=null){
      currentService=currentServiceFromDB;
      request.setService(currentService);
  }
//}
//else if(getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.ChooseServicesActivity")){
    serviciiSelectate=(ArrayList<Serviciu>)i.getSerializableExtra("ListaServiciiSelectate");
//}






        initList();
        spinner = findViewById(R.id.spinnerCategorii);
        mAdapter = new ProductsAdaptor(this, listaProduse);
        spinner.setAdapter(mAdapter);
        mDisplayDate = (TextView) findViewById(R.id.tw_alegeData);
        mDisplayHour=(TextView)findViewById(R.id.tw_alegeOra);

        trimiteCerere= (Button)findViewById(R.id.btn_trimite);
        detalii= (EditText) findViewById(R.id.et_detaliiSuplimentare);
        servicii=(TextView)findViewById(R.id.tw_adaugaServicii);
       final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        servicii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itReq = new Intent(getApplicationContext(), ChooseServicesActivity.class);
                itReq.putExtra("ListaServiciiSelectate",serviciiSelectate);
                itReq.putExtra("ListaServicii",request.getService().getSevicii());
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
                     dataTrimitere=  Calendar.getInstance().getTime();


                   // TODO: add timepicker hour to dataProgramare

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
        int img=0;
        for(String p :request.getService().getProduse()){
            if (p == "ELECTROCASNICE")
                img=R.drawable.electrocasnice;
            if (p == "TELEFON/TABLETA")
                img= R.drawable.telefon;
            if (p == "MASINA")
                img= R.drawable.masina;
            if (p == "PC/LAPTOP")
                img= R.drawable.pc;

            listaProduse.add(new ProductsItem(p, img));
        }

    }

    private void trimiteCererea(final Request request){

        //TODO: BUG LISTA SERVICII SOLICITATE

        if(detalii.getText().toString()!=null){
            request.setDetalii(detalii.getText().toString());
        }
       // request.setServicii(serviciiSelectate);
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

        request.setDataProgramare(dataProgramare);
        request.setDataTrimiterii(dataTrimitere);

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
