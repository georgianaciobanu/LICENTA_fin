package com.example.proiect_licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpClientActivity extends AppCompatActivity implements View.OnFocusChangeListener{
    Button register;
    EditText username;
    EditText email;
    EditText telefon;
    EditText pass;
    EditText comfirmedPass;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_client);

        register=(Button)findViewById(R.id.regButton);
        username=(EditText)findViewById(R.id.et_username);
        email=(EditText)findViewById(R.id.et_mail);
        telefon=(EditText)findViewById(R.id.et_telefon);
        pass=(EditText)findViewById(R.id.et_pass);
        comfirmedPass=(EditText)findViewById(R.id.et_confPass);
        username.setText("username client");
        email.setText("client@mail.com" );
        telefon.setText("0789554333");
        pass.setText("pass123!");
        comfirmedPass.setText("pass123!");

        username.setOnFocusChangeListener(this);
        email.setOnFocusChangeListener(this);
        telefon.setOnFocusChangeListener(this);
        pass.setOnFocusChangeListener(this);
        comfirmedPass.setOnFocusChangeListener(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUsername() && validateEmail() && validateTelefon() && validatePassword()){
                    inregistrareClient();

                }
                else {

                    Toast.makeText(getApplicationContext(), "Completarea campurilor este obligatorie", Toast.LENGTH_LONG).show();
                }
            }


        });



    }

    public void goToHomeScreenClient() {
        Intent it = new Intent(this, HomeScreenClientActivity.class);
        startActivity(it);

    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Introduceti o adresa de email valida");
            return false;
        }
        else {
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
        }
        else if((pass.getText().toString()).equals(comfirmedPass.getText().toString())){
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


    public  void inregistrareClient() {

        Client clientNou= new Client();
        clientNou.setUsername(username.getText().toString());
        clientNou.setEmail(email.getText().toString());
        clientNou.setTelefon(telefon.getText().toString());
        clientNou.setPass(pass.getText().toString());

        if(clientNou!=null) {
            Toast.makeText(SignUpClientActivity.this, "Client inregistrat: " + clientNou.username + " " + clientNou.email + " " + clientNou.telefon + " " + clientNou.pass, Toast.LENGTH_LONG).show();
            goToHomeScreenClient();
        }
    }

}
