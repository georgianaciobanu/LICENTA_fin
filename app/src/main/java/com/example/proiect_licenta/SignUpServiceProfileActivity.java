package com.example.proiect_licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpServiceProfileActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    Button completeProfile;
    EditText proprietar;
    EditText numeService;
    EditText descriere;
    EditText program;
    EditText adresa;
    Button upload;
    int count=0;
    Service currentService;
    String adresaService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_service_profile);


        Intent i = getIntent();
        currentService = (Service)i.getSerializableExtra("Service");

        completeProfile=(Button)findViewById(R.id.BTNprofileRegisterService2);
        proprietar=(EditText)findViewById(R.id.et_ownerName);
        numeService=(EditText)findViewById(R.id.et_serviceName);
        descriere=(EditText)findViewById(R.id.et_descriereService) ;
        program=(EditText)findViewById(R.id.et_program);
        adresa=(EditText)findViewById(R.id.et_adresa);
        upload=(Button)findViewById(R.id.BTN_upload) ;

        proprietar.setText("proprietar service");
        numeService.setText("nume service" );
        descriere.setText("descrierea service-ului este aici");
        program.setText("program service");
        adresa.setText("adresa service");


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                upload.setEnabled(count < 2);

            }
        });


        proprietar.setOnFocusChangeListener(this);
        numeService.setOnFocusChangeListener(this);
        descriere.setOnFocusChangeListener(this);
        program.setOnFocusChangeListener(this);
        adresa.setOnFocusChangeListener(this);

        completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String proprietarS= proprietar.getText().toString();
                String numeServiceS= numeService.getText().toString();
                String descriereS= descriere.getText().toString();
                String programS= program.getText().toString();
                String adresaS= adresa.getText().toString();


                if (proprietarS.isEmpty() || numeServiceS.isEmpty() || descriereS.isEmpty() ||  programS.isEmpty() || adresaS.isEmpty() || upload.isEnabled()){
                    Toast.makeText(getApplicationContext(), "Completarea campurilor este obligatorie", Toast.LENGTH_LONG).show();
                }
                else {

                    inregistreazaService(proprietarS,numeServiceS,descriereS,programS,adresaS);

                }

            }

    });





}

    public void goToServiceProfile() {
        Intent itProd = new Intent(this, SignUpServiceProfile2Activity.class);
        itProd.putExtra("adresa", adresaService);
        startActivity(itProd);

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

    public void inregistreazaService(String proprietar, String numeService, String descriere, String program,String adresa){

        currentService.setProprietar(proprietar);
        currentService.setNumeService(numeService);
        currentService.setDescriere(descriere);
        currentService.setProgram(program);

        adresaService=adresa;

        if(currentService!=null) {
            Toast.makeText(SignUpServiceProfileActivity.this, "Info service 1: " + currentService.username + " " + currentService.email + " " + currentService.telefon + " " + currentService.pass+ " "+
                    currentService.descriere +" "+ currentService.numeService+ " "+currentService.program+" "+currentService.proprietar,  Toast.LENGTH_LONG).show();
            goToServiceProfile();
        }
    }
}
