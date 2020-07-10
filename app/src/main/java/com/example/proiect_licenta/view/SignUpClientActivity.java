package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.MainActivity;
import com.example.proiect_licenta.model.AESCrypt;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpClientActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    Button register;
    EditText username;
    EditText email;
    EditText telefon;
    EditText pass;
    EditText comfirmedPass;


    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    String TAG="SignUpClientActivity";

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

        register = (Button) findViewById(R.id.regButton);
        username = (EditText) findViewById(R.id.et_username);
        email = (EditText) findViewById(R.id.et_mail);
        telefon = (EditText) findViewById(R.id.et_telefon);
        pass = (EditText) findViewById(R.id.et_pass);
        comfirmedPass = (EditText) findViewById(R.id.et_confPass);
//        username.setText("username client");
//        email.setText("client@mail.com");
//        telefon.setText("0789554333");
//        pass.setText("pass123!");
//        comfirmedPass.setText("pass123!");
        username.setOnFocusChangeListener(this);
        email.setOnFocusChangeListener(this);
        telefon.setOnFocusChangeListener(this);
        pass.setOnFocusChangeListener(this);
        comfirmedPass.setOnFocusChangeListener(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername() && validateEmail() && validateTelefon() && validatePassword()) {
                    User clientNou = new User();
                    clientNou.setUsername(username.getText().toString());
                    clientNou.setEmail(email.getText().toString());
                    clientNou.setTelefon(telefon.getText().toString());
                    clientNou.setPass(pass.getText().toString());
                    userRegisterFirebase(clientNou);

                } else {
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


    public void userRegisterFirebase(final User user){
        fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser= fAuth.getCurrentUser();
                    String userid=firebaseUser.getUid();

                    reference= FirebaseDatabase.getInstance().getReference("User").child(userid);

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap. put("username",user.getUsername());
                    hashMap.put("telefon",user.getTelefon());
                    hashMap.put("email",user.getEmail());
                    try {
                        hashMap.put("pass",AESCrypt.encrypt(user.getPass()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Log.i(TAG,"user inserted");
                            goToHomeScreenClient();
                        }
                    });

                }
                else{
                    task.getException().printStackTrace();

                    Toast.makeText(SignUpClientActivity.this,"Regestration error",Toast.LENGTH_LONG).show();

                }
            }
        });

    }



}
