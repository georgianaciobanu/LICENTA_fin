package com.example.proiect_licenta.presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ProductsItem;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.view.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrimiteCerereActivity extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener {
    private ArrayList<ProductsItem> listaProduse;
    private ProductsAdaptor mAdapter;
    Spinner spinner;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayHour;
    Button trimiteCerere;
    EditText detalii;
    Date dataProgramare;
    Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimite_cerere);
        initList();
        spinner = findViewById(R.id.spinnerCategorii);
        mAdapter = new ProductsAdaptor(this, listaProduse);
        spinner.setAdapter(mAdapter);
        mDisplayDate = (TextView) findViewById(R.id.tw_alegeData);
        mDisplayHour=(TextView)findViewById(R.id.tw_alegeOra);
        request = new Request();
        trimiteCerere= (Button)findViewById(R.id.btn_trimite);
        detalii= (EditText) findViewById(R.id.et_detaliiSuplimentare);
       final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");



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
                    request.setDataProgramare(dataProgramare);
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

    private void trimiteCererea(Request request){

        if(detalii.getText().toString()!=null){
            request.setDetalii(detalii.getText().toString());
        }
        Toast.makeText(getApplicationContext(), request.getProdus()+ " "+ request.getDetalii()+ " "+ request.getDataProgramare(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        mDisplayHour.setText(hourOfDay + ": " + minute);
    }
}
