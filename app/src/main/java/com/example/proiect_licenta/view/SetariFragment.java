package com.example.proiect_licenta.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proiect_licenta.MainActivity;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.AESCrypt;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


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
    TextView stergeCont;
    String pass;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



       if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
           getContext().getTheme().applyStyle(R.style.darktheme, true);
       }
       else{
           getContext().getTheme().applyStyle(R.style.AppTheme, true);
       }

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setTitle("Loading");
        progressDialog.show();

        view =inflater.inflate(R.layout.fragment_setari, container, false);
        aswitch=(Switch)view.findViewById(R.id.switchTheme);
        usernameEt=(EditText)view.findViewById(R.id.edtx_settings_username);
        proprietarEt=(EditText)view.findViewById(R.id.edtx_settings_tf);
        imageUpdateSett=(ImageButton) view.findViewById(R.id.imageUpdateSett);
        stergeCont=(TextView) view.findViewById(R.id.stergeCont);

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            aswitch.setChecked(true);
        }
        listnerUser=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) throws Exception {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentUser = singleSnapshot.getValue(User.class);
                }

                if (currentUser != null ) {
                    usernameEt.setText(currentUser.getUsername());
                    proprietarEt.setText(currentUser.getTelefon());
                    proprietarEt.setHint("Telefon");
                    pass= AESCrypt.decrypt(currentUser.getPass());
                    r=0;
                    progressDialog.dismiss();
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
            public void onSuccess(DataSnapshot data) throws Exception {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentService = singleSnapshot.getValue(Service.class);
                }

                if (currentService != null ) {
                    usernameEt.setText(currentService.getUsername());
                    proprietarEt.setHint("Proprietar");
                    proprietarEt.setText(currentService.getProprietar());
                    pass= AESCrypt.decrypt(currentService.getPass());

                    progressDialog.dismiss();
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

        stergeCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        view.getContext());

                // set title
                alertDialogBuilder.setTitle("Doriti sa stergeti contul?");

                // set dialog message
                alertDialogBuilder

                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Get auth credentials from the user for re-authentication. The example below shows
                                // email and password credentials but there are multiple possible providers,
                                // such as GoogleAuthProvider or FacebookAuthProvider.
                                if (pass != null) {
                                    AuthCredential credential = EmailAuthProvider
                                            .getCredential(firebaseUser.getEmail(), pass);

                                    // Prompt the user to re-provide their sign-in credentials
                                    firebaseUser.reauthenticate(credential)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    firebaseUser.delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                                        Query query;
                                                                        if (currentUser.getEmail() != null) {
                                                                            query = ref.child("User").orderByChild("email").equalTo(firebaseUser.getEmail());
                                                                        } else {
                                                                            query = ref.child("Service").orderByChild("email").equalTo(firebaseUser.getEmail());

                                                                        }
                                                                        if (query != null) {
                                                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                    for (DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                                                                                        requestSnapshot.getRef().removeValue();

                                                                                        Intent i = new Intent(view.getContext(), MainActivity.class);
                                                                                        startActivity(i);


                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onCancelled(DatabaseError databaseError) {

                                                                                }
                                                                            });

                                                                        }
                                                                    }
                                                                }
                                                            });

                                                }
                                            });

                                }

                        }
            })
                    .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.cancel();
                }
            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
                    alertDialog.show();
            }
        });

        return view;
    }


    public void restartApp() throws java.lang.InstantiationException, IllegalAccessException {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SetariFragment.class.newInstance()).commit();
    }
}