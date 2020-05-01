package com.example.proiect_licenta.view;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Upload;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;
import java.util.ArrayList;
import java.util.List;


public class ServiceDetailsActivity extends AppCompatActivity {
    ArrayList<Upload> mUploads ;
    OnGetDataListener listenerImage;
    Upload ex;
    CarouselView carouselView;
    List<String> imageUrls = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        mUploads = new ArrayList<>();
        ex = new Upload();


        listenerImage = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    ex = singleSnapshot.getValue(Upload.class);

                    if (ex.getEmailService().equals("ricaaa@mail.com")) {
                        mUploads.add(ex);
                    }
                }

                if (mUploads.size() > 0) {

                    for (Upload u : mUploads) {
                        imageUrls.add(u.getImageUrl());


                    }
                    carouselView = (CarouselView) findViewById(R.id.carouselView);

                    carouselView.setViewListener(viewListener);
                    carouselView.setPageCount(mUploads.size());
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };

        FirebaseFunctions.getImageFire("emailService","ricaaa@mail.com",listenerImage);


    }


    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(final int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            final ImageView myImageView = customView.findViewById(R.id.myImage);
            Picasso.get().load(Uri.parse(imageUrls.get(position))).into(myImageView );
            return customView;
        }
    };



}
