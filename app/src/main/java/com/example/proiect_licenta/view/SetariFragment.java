package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class SetariFragment extends Fragment {

    private Switch aswitch;
    View  view;
    OnGetDataListener listenerService;
    Service currentService;
    User currentUser;
    FirebaseUser firebaseUser;
    String currentUserEmail;
    OnGetDataListener listnerUser;
    EditText usernameEt;
    EditText proprietarEt;
    ImageButton imageUpdateSett;
    int r=1;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



       if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
           getContext().getTheme().applyStyle(R.style.darktheme, true);
       }
       else{
           getContext().getTheme().applyStyle(R.style.AppTheme, true);
       }
        view =inflater.inflate(R.layout.fragment_setari, container, false);
        aswitch=(Switch)view.findViewById(R.id.switchTheme);
        usernameEt=(EditText)view.findViewById(R.id.edtx_settings_username);
        proprietarEt=(EditText)view.findViewById(R.id.edtx_settings_tf);
        imageUpdateSett=(ImageButton) view.findViewById(R.id.imageUpdateSett);

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            aswitch.setChecked(true);
        }
        listnerUser=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentUser = singleSnapshot.getValue(User.class);
                }

                if (currentUser != null ) {
                    usernameEt.setText(currentUser.getUsername());
                    proprietarEt.setHint("Telefon");
                    proprietarEt.setText(currentUser.getTelefon());
                    r=0;
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        };

        listenerService=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentService = singleSnapshot.getValue(Service.class);
                }

                if (currentService != null ) {
                    usernameEt.setText(currentService.getUsername());
                    proprietarEt.setHint("Proprietar");
                    proprietarEt.setText(currentService.getProprietar());
                }
                else{
                    FirebaseFunctions.getUserFirebase(listnerUser,currentUserEmail);
                }





            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        };

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUserEmail=firebaseUser.getEmail();

        FirebaseFunctions.getServiceFirebase("email",currentUserEmail,listenerService);

        aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    try {
                        restartApp();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    try {
                        restartApp();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        imageUpdateSett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r==0){
                   FirebaseFunctions.updateUsernameUser(currentUser.getId(),usernameEt.getText().toString(),proprietarEt.getText().toString());
                }else{
                    FirebaseFunctions.updateUsernameService(currentService.getServiceId(),usernameEt.getText().toString(),proprietarEt.getText().toString());

                }
            }
        });



        return view;
    }


    public void restartApp() throws java.lang.InstantiationException, IllegalAccessException {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SetariFragment.class.newInstance()).commit();
    }
}