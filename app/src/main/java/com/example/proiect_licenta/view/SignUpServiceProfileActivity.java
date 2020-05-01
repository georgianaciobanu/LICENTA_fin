package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


public class SignUpServiceProfileActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    Button completeProfile;
    EditText proprietar;
    EditText numeService;
    EditText descriere;
    EditText program;
    Service currentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_service_profile);


        Intent i = getIntent();
        currentService = (Service) i.getSerializableExtra("Service");

        completeProfile = (Button) findViewById(R.id.BTNprofileRegisterService2);
        proprietar = (EditText) findViewById(R.id.et_ownerName);
        numeService = (EditText) findViewById(R.id.et_serviceName);
        descriere = (EditText) findViewById(R.id.et_descriereService);
        program = (EditText) findViewById(R.id.et_program);
        proprietar.setText("proprietar service");
        numeService.setText("nume service");
        descriere.setText("descrierea service-ului este aici");
        program.setText("program service");






        proprietar.setOnFocusChangeListener(this);
        numeService.setOnFocusChangeListener(this);
        descriere.setOnFocusChangeListener(this);
        program.setOnFocusChangeListener(this);

        completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String proprietarS = proprietar.getText().toString();
                String numeServiceS = numeService.getText().toString();
                String descriereS = descriere.getText().toString();
                String programS = program.getText().toString();



                if (proprietarS.isEmpty() || numeServiceS.isEmpty() || descriereS.isEmpty() || programS.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Completarea campurilor este obligatorie", Toast.LENGTH_LONG).show();
                } else {

                    inregistreazaService(proprietarS, numeServiceS, descriereS, programS);

                }

            }

        });


    }

    public void goToServiceProfile() {
        Intent it = new Intent(this, SignUpServiceProfile2Activity.class);
        it.putExtra("Service", currentService);
        startActivity(it);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText e = (EditText) v;
        if (!hasFocus) {
            if (e.getText().toString().isEmpty()) {
                e.setBackgroundResource(R.drawable.edittext_style_error);
                e.setError("Completati toate campurile");
            } else {
                e.setError(null);
                e.setBackgroundResource(R.drawable.edittext_style);
            }
        }
    }

    public void inregistreazaService(String proprietar, String numeService, String descriere, String program) {

        currentService.setProprietar(proprietar);
        currentService.setNumeService(numeService);
        currentService.setDescriere(descriere);
        currentService.setProgram(program);



        if (currentService != null) {
            Toast.makeText(SignUpServiceProfileActivity.this, "Info service 1: "
                    + currentService.getUsername() + " "
                    + currentService.getEmail() + " "
                    + currentService.getTelefon() + " "
                    + currentService.getPass() + " "
                    + currentService.getDescriere() + " "
                    + currentService.getNumeService() + " "
                    + currentService.getProgram() + " "
                    + currentService.getProprietar(), Toast.LENGTH_LONG).show();
            goToServiceProfile();
        }
    }



}
