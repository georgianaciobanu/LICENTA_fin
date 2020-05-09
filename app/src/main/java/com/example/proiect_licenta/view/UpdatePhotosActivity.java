package com.example.proiect_licenta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Upload;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.PhotosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class UpdatePhotosActivity extends AppCompatActivity {
    ArrayList<Upload> imagesList= new ArrayList<>();
    ListView listView;
    PhotosAdapter photosAdapter;
    ImageButton imageButtonAddPhotos;
    ImageButton imageButtonDeletePhotos;
    FirebaseUser firebaseUser;
    String currentUserEmail;
    OnGetDataListener listenerService;
    Service currentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_photos);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUserEmail=firebaseUser.getEmail();


//        listenerService=new OnGetDataListener() {
//            @Override
//            public void onStartFirebaseRequest() {
//
//
//            }
//
//            @Override
//            public void onSuccess(DataSnapshot data) {
//
//                for(DataSnapshot singleSnapshot : data.getChildren()) {
//                    currentService = singleSnapshot.getValue(Service.class);
//                }
//
//
//
//
//
//
//            }
//
//            @Override
//            public void onFailed(DatabaseError databaseError) {
//            }
//        };
//
//        FirebaseFunctions.getServiceFirebase("email",currentUserEmail,listenerService);
        Intent i = getIntent();
        imagesList=(ArrayList<Upload>) i.getSerializableExtra("Images");

        listView=(ListView)findViewById(R.id.photosListForUpdate);
        imageButtonAddPhotos=(ImageButton)findViewById(R.id.imageButtonAddPhotos);
        imageButtonDeletePhotos=(ImageButton)findViewById(R.id.imageButtonDeletePhotos);
        photosAdapter= new PhotosAdapter(getApplicationContext(),imagesList);
        listView.setAdapter(photosAdapter);

        imageButtonAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(currentService!=null) {
                    Intent it = new Intent(getApplicationContext(), UploadImageActivity.class);
                    it.putExtra("ServiceEmail", currentUserEmail);
                    startActivity(it);
               // }
            }
        });



    }
}
