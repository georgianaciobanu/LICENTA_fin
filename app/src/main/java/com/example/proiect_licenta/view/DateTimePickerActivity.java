package com.example.proiect_licenta.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.presenter.TrimiteCerereActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimePickerActivity extends AppCompatActivity {
    Long time;
    Date dataProgramare;
    Button buttonSet;
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker);
      buttonSet=(Button)findViewById(R.id.date_time_set);

        Intent i=getIntent();
        request = (Request) i.getSerializableExtra("request");
        final View dialogView = View.inflate(getApplicationContext(), R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                time = calendar.getTimeInMillis();
                alertDialog.dismiss();
                dataProgramare=calendar.getTime();
                request.setDataProgramare(dataProgramare);
                Intent itDate = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
                itDate.putExtra("requestBack",request);
                startActivityForResult(itDate,0);

            }
        });


    }
}
