package com.example.proiect_licenta.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiect_licenta.R;

public class LoginActivity extends AppCompatActivity {


    CardView logIn;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logIn=(CardView) findViewById(R.id.CardView_btnlogin);
        email=(EditText)findViewById(R.id.et_login_mail);
        password=(EditText)findViewById(R.id.et_login_pass);

        email.setText("client@mail.com");
        password.setText("pass12" );





        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean exista= true;//helper.verificaLogIn(email.getText().toString(),password.getText().toString());
                int serviceOrClient; //1=service, 2=client
                serviceOrClient=2;

                if(exista==true){

                if(serviceOrClient==1){
                    goToHomeScreenService();
                    Toast.makeText(LoginActivity.this,"Email: "+email.getText().toString()+ " Pass: "+password.getText().toString(),Toast.LENGTH_LONG).show();
                }else if(serviceOrClient==2){
                   // goToHomeScreenClient();
                    goToHomeScreenService();
                    Toast.makeText(LoginActivity.this,"Email: "+email.getText().toString()+ " Pass: "+password.getText().toString(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,"A aparut o eroare! serviceOrClient= "+ serviceOrClient,Toast.LENGTH_SHORT).show();
                }


                }
                else {
                    Toast.makeText(LoginActivity.this,"Emailul si parola sunt gresite",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void goToHomeScreenClient() {
        Intent it = new Intent(this, HomeScreenClientActivity.class);
        startActivity(it);

    }

    public void goToHomeScreenService() {
        Intent it = new Intent(this, HomeScreenServiceActivity.class);
        startActivity(it);

    }
}
