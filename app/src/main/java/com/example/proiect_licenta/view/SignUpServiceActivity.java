package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;

import java.util.regex.Pattern;

public class SignUpServiceActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    Button completeProfile;
    EditText username;
    EditText email;
    EditText telefon;
    EditText pass;
    EditText comfirmedPass;

    Service serviceNou;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
//                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_service);

        completeProfile = (Button) findViewById(R.id.BTNprofileRegisterService1);
        serviceNou = new Service();


        username = (EditText) findViewById(R.id.et_username_service);
        email = (EditText) findViewById(R.id.et_mail_service);
        telefon = (EditText) findViewById(R.id.et_telefon_service);
        pass = (EditText) findViewById(R.id.et_pass_service);
        comfirmedPass = (EditText) findViewById(R.id.et_confPass_service);

        username.setText("username service");
        email.setText("service@mail.com");
        telefon.setText("0789554333");
        pass.setText("pass666&");
        comfirmedPass.setText("pass666&");

        completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername() && validateEmail() && validateTelefon() && validatePassword()) {
                    getServiceInfo(username.getText().toString(), email.getText().toString(), pass.getText().toString(), telefon.getText().toString());
                } else {

                    Toast.makeText(getApplicationContext(), "Completarea campurilor este obligatorie", Toast.LENGTH_LONG).show();
                }
            }


        });
    }

    public void getServiceInfo(String username, String email, String pass, String telefon) {

        serviceNou.setUsername(username);
        serviceNou.setEmail(email);
        serviceNou.setPass(pass);
        serviceNou.setTelefon(telefon);


        if (serviceNou != null) {
         
            goToServiceProfile();
        }
    }

    public void goToServiceProfile() {
        Intent it = new Intent(this, SignUpServiceProfileActivity.class);
        it.putExtra("Service", serviceNou);
        startActivity(it);

    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Introduceti o adresa de email valida");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = username.getText().toString().trim();

        if (usernameInput.length() > 50) {
            username.setError("Username-ul ales este prea lung");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean validateTelefon() {
        String telefomInput = telefon.getText().toString().trim();

        if (telefomInput.length() != 10) {
            telefon.setError("Introduceti un numar de telefon valid");
            return false;
        } else {
            telefon.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = pass.getText().toString().trim();

        if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setError("Parola prea usoara");
            return false;
        } else if ((pass.getText().toString()).equals(comfirmedPass.getText().toString())) {
            pass.setError(null);
            return true;
        } else {
            pass.setError("Parolele nu corespund");

            return false;
        }
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

        validateEmail();
        validateUsername();
        validatePassword();
        validateTelefon();
    }
}
