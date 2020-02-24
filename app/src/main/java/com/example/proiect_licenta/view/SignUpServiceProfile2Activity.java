package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;

import java.util.ArrayList;

public class SignUpServiceProfile2Activity extends AppCompatActivity implements View.OnFocusChangeListener {
    Button completeProfile;
    Service currentService;
    CheckBox telefon, pc, electocasnice, masini;
    ArrayList<String> produse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_service_profile2);

        Intent i = getIntent();
        currentService = (Service) i.getSerializableExtra("Service");


        completeProfile = (Button) findViewById(R.id.BTNprofileRegisterService3);
        telefon = (CheckBox) findViewById(R.id.Cbox_Telefon);
        pc = (CheckBox) findViewById(R.id.Cbox_PC);
        electocasnice = (CheckBox) findViewById(R.id.Cbox_Electrocasnice);
        masini = (CheckBox) findViewById(R.id.Cbox_Masina);
        completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProduse();
            }
        });
    }

    public void goToAddServicesList() {
        Intent it = new Intent(this, AddServicesListActivity.class);
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


    public void getProduse() {
        produse = new ArrayList<>();
        if (telefon.isChecked()) {
            produse.add(telefon.getText().toString());
        }
        if (pc.isChecked()) {
            produse.add(pc.getText().toString());
        }
        if (electocasnice.isChecked()) {
            produse.add(electocasnice.getText().toString());
        }
        if (masini.isChecked()) {
            produse.add(masini.getText().toString());
        }

        if (produse.size() == 0) {
            Toast.makeText(getApplicationContext(), "Completarea campurilor este obligatorie", Toast.LENGTH_LONG).show();
        } else {
            currentService.setProduse(produse);

            goToAddServicesList();
        }
    }
}
