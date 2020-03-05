package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiect_licenta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LoginActivity extends AppCompatActivity {


    CardView logIn;
    EditText email;
    EditText password;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logIn = (CardView) findViewById(R.id.CardView_btnlogin);
        email = (EditText) findViewById(R.id.et_login_mail);
        password = (EditText) findViewById(R.id.et_login_pass);

        email.setText("server1151@email.ro");
        password.setText("serverPass1511");
        mFirebaseAuth = FirebaseAuth.getInstance();



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeScreenClientActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailS = email.getText().toString();
                String pwd = password.getText().toString();
                if(emailS.isEmpty()){
                    email.setError("Please enter email id");
                    email.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(emailS.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(emailS.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(emailS, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                goToHomeScreenClient();

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }

        });
            }

//                boolean exista = true;//helper.verificaLogIn(email.getText().toString(),password.getText().toString());
//                int serviceOrClient; //1=service, 2=client
//                serviceOrClient = 2;
//
//                if (exista == true) {
//
//                    if (serviceOrClient == 1) {
//                        goToHomeScreenService();
//                        Toast.makeText(LoginActivity.this, "Email: " + email.getText().toString() + " Pass: " + password.getText().toString(), Toast.LENGTH_LONG).show();
//                    } else if (serviceOrClient == 2) {
//                        // goToHomeScreenClient();
//                        goToHomeScreenService();
//                        Toast.makeText(LoginActivity.this, "Email: " + email.getText().toString() + " Pass: " + password.getText().toString(), Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "A aparut o eroare! serviceOrClient= " + serviceOrClient, Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "Emailul si parola sunt gresite", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
   // }

    public void goToHomeScreenClient() {
        Intent it = new Intent(this, HomeScreenClientActivity.class);
        startActivity(it);
    }

    public void goToHomeScreenService() {
        Intent it = new Intent(this, HomeScreenServiceActivity.class);
        startActivity(it);

    }
}
