package com.example.proiect_licenta.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Upload;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ReviewsAdapter;
import com.example.proiect_licenta.presenter.TrimiteCerereActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class AboutServiceActivity extends AppCompatActivity implements RatingDialogListener {
    Button cerere;
    TextView listaServicii;
    TextView btnAdresa;
    EditText program;
    EditText telefon;
    OnGetDataListener listenerLocation;
    EditText email;
    EditText descriere;
    EditText nume;
    Service currentService;
    OnGetDataListener listenerCurrentReview;
    OnGetDataListener listenerReview;
    Review rating;
    ArrayList<Upload> mUploads ;
    OnGetDataListener listenerImage;
    Upload ex;
    CarouselView carouselView;
    List<String> imageUrls = new ArrayList<String>();
    PhysicalLocation deviceLocationNow;
    PhysicalLocation currentLocation;
    RatingBar ratingBar;
    DatabaseReference reference;
    String currentuser;
    OnGetDataListener listenerBooking;
    Review currentReview;
    ListView listaReviews;
    ArrayList<Review> reviews;
    ReviewsAdapter reviewsAdapter;
    Request request;
    ArrayList<Request> bookingList;
    //ImageButton fab;
    FirebaseUser firebaseUser;
    int req=1;
    ImageButton imgButUpdate;
    OnGetDataListener listenerUpdateService;
    Service service;
    ProgressDialog pg;
    ImageButton imageButtonUpdatePhotos;
    ImageButton imageButtonChat;









    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_service);


        final Intent i = getIntent();
        currentService=(Service) i.getSerializableExtra("CurrentService");

        imgButUpdate=(ImageButton)findViewById(R.id.imageButtonUpdate);
        imageButtonUpdatePhotos=(ImageButton)findViewById(R.id.imageButtonUpdatePhotos);
        imageButtonChat=(ImageButton)findViewById(R.id.imageButtonChat);
        imgButUpdate.setVisibility(View.INVISIBLE);
        imageButtonUpdatePhotos.setVisibility(View.INVISIBLE);

        listaReviews=(ListView)findViewById(R.id.lista_reviews) ;
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
       //  fab = (ImageButton) findViewById(R.id.button_rating);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);
        ratingBar.isIndicator();
         currentuser= firebaseUser.getEmail();
        request=new Request();
        bookingList=new ArrayList<>();
        final String currentUserId = firebaseUser.getUid();
        currentReview=new Review();
        cerere = (Button) findViewById(R.id.BTN_timiteCerere);

        program = (EditText) findViewById(R.id.tw_programService);
        descriere = (EditText) findViewById(R.id.tw_descriereService);
        telefon = (EditText) findViewById(R.id.tw_telefonService);
        email = (EditText) findViewById(R.id.tw_mailService);
        nume = (EditText) findViewById(R.id.tw_NumeService);

        program.setEnabled(false);
        descriere.setEnabled(false);
        telefon.setEnabled(false);
        email.setEnabled(false);
        nume.setEnabled(false);

        if(getCallingActivity()!=null) {
            if (getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.HomeScreenClientActivity")) {
                program.setEnabled(true);
                descriere.setEnabled(true);
                telefon.setEnabled(true);
                email.setEnabled(true);
                nume.setEnabled(true);
               // fab.setVisibility(View.INVISIBLE);
                ratingBar.setVisibility(View.INVISIBLE);
                req=0;
                imgButUpdate.setVisibility(View.VISIBLE);
                imageButtonUpdatePhotos.setVisibility(View.VISIBLE);
                cerere.setVisibility(View.INVISIBLE);
                imageButtonChat.setVisibility((View.INVISIBLE));
                listaReviews.setVisibility(View.INVISIBLE);

            }
        }

        imageButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("messageFor", currentService.getEmail());
               startActivity(intent);
            }
        });



        mUploads = new ArrayList<>();
        reviews=new ArrayList<>();
        ex = new Upload();

        listaServicii = (TextView) findViewById(R.id.tw_listaServicii);

        btnAdresa = (TextView) findViewById(R.id.tw_adresaService);

        listenerUpdateService = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    service = singleSnapshot.getValue(Service.class);
                    if (service.getServiceId().equals(currentService.getServiceId())) {
                        HashMap<String, Object> result1 = new HashMap<>();
                        result1.put("numeService", nume.getText().toString());

                        HashMap<String, Object> result2 = new HashMap<>();
                        result2.put("descriere", descriere.getText().toString());

                        HashMap<String, Object> result3 = new HashMap<>();
                        result3.put("program", program.getText().toString());

                        HashMap<String, Object> result4 = new HashMap<>();
                        result4.put("email", email.getText().toString());

                        HashMap<String, Object> result5 = new HashMap<>();
                        result5.put("telefon", telefon.getText().toString());

                        singleSnapshot.getRef().updateChildren(result1);
                        singleSnapshot.getRef().updateChildren(result2);
                        singleSnapshot.getRef().updateChildren(result3);
                        singleSnapshot.getRef().updateChildren(result4);
                        singleSnapshot.getRef().updateChildren(result5);


                        pg.hide();
                    }
                }


                pg.hide();


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
                pg.hide();

            }
        };


        imgButUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg = new ProgressDialog(AboutServiceActivity.this);
                pg.setMessage("Loading");
                pg.show();

                FirebaseFunctions.getServiceFirebase("serviceId",currentService.getServiceId(),listenerUpdateService);



            }
        });

        listenerBooking = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    request = singleSnapshot.getValue(Request.class);
                    if (request.getClient().getEmail().equals(currentuser) && request.getService().getEmail().equals(currentService.getEmail()) && System.currentTimeMillis() > request.getDataProgramare().getTime()) {
                        bookingList.add(request);
                    }
                }

                if (bookingList==null && bookingList.size() == 0) {
                   // fab.hide();

                }else{

                   // fab.show();


                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };
        FirebaseFunctions.getBookingFirebase(listenerBooking);




        listenerImage = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    ex = singleSnapshot.getValue(Upload.class);

                    if (ex.getEmailService().equals(currentService.getEmail())) {
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

        FirebaseFunctions.getImageFire("emailService",currentService.getEmail(),listenerImage);


        listenerLocation=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentLocation = singleSnapshot.getValue(PhysicalLocation.class);
                    if(currentLocation.getId_corespondent().equals(currentUserId)){
                        deviceLocationNow=currentLocation;
                    }


                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };

        FirebaseFunctions.getLocationFirebase("id_corespondent",currentUserId,listenerLocation);

        listenerCurrentReview=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {
                   int count=0;
                   float sum=0;
                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    currentReview = singleSnapshot.getValue(Review.class);
                    if(currentReview!=null){
                       count++;
                       sum+=Float.valueOf(currentReview.getRateValue());
                        reviews.add(currentReview);
                    }

                }
                  if(sum>0 && count>0){
                      ratingBar.setRating(sum/count);
                      reviewsAdapter = new ReviewsAdapter(getApplicationContext(), reviews);
                      listaReviews.setAdapter(reviewsAdapter);
                  }




            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };

        FirebaseFunctions.getReviewFirebase("idService",currentService.getServiceId(),listenerCurrentReview);
        listenerReview=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {

                    if(currentReview.getIdClient()==currentuser){
                        singleSnapshot.getRef().removeValue();
                    }


                }

                reviewInsertFirebase(rating);



            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };



                        if(currentService!=null){
                           setInfoService(currentService);

                            cerere.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToCerere();
                                }
                            });

                            listaServicii.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToListaServicii();
                                }
                            });

                            btnAdresa.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   if(req==0) {
                                       Intent intent = new Intent(getApplicationContext(), SearchLocationActivity.class);
                                       intent.putExtra("Service", currentService);
                                       startActivityForResult(intent,0);

                                   }
                                   else {

                                       if (deviceLocationNow != null) {
                                           goToMaps(deviceLocationNow.getLatitudine(),
                                                   deviceLocationNow.getLogitudine(),
                                                   currentService.getLoc().getLatitudine(),
                                                   currentService.getLoc().getLogitudine());
                                       }
                                   }
                                }
                            });


                        }




                        //rating bar


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               showRatingDialog();
//            }
//        });


        imageButtonUpdatePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdatePhotosActivity.class);
                intent.putExtra("Images", mUploads);
                startActivity(intent);
            }
        });

                    }




    public void goToCerere() {
        Intent itReq = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
        itReq.putExtra("CurrentService",currentService);
        startActivityForResult(itReq,0);



    }

    public void goToListaServicii() {
        if(req==0){
            Intent intent = new Intent(this, ServicesListActivity.class);
            intent.putExtra("Service", currentService);
            startActivityForResult(intent,0);
        }else {
            Intent it = new Intent(this, ServicesListActivity.class);
            it.putExtra("Service", currentService);
            startActivity(it);
        }

    }

    public void goToMaps(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void setInfoService(Service currentService) {

        nume.setText(currentService.getNumeService());
        descriere.setText(currentService.getDescriere());
        program.setText(currentService.getProgram());
        telefon.setText(currentService.getTelefon());
        email.setText(currentService.getEmail());
        btnAdresa.setText(currentService.getLoc().getAdresa());

    }


    private void showRatingDialog(){

        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very bad","Not good","Quite ok","Very good","Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate this Service")
                .setDescription("Please select some stars and give us your feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(AboutServiceActivity.this)
                .show();

    }


    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {

         rating= new Review();

        rating.setIdService(currentService.getServiceId());
        rating.setComment(s);
        rating.setRateValue(String.valueOf(i));
        rating.setIdClient(currentuser);
        rating.setData(Calendar.getInstance().getTime());

        FirebaseFunctions.getReviewFirebase("idService",currentService.getServiceId(),listenerReview);


    }


    public void reviewInsertFirebase(final Review review) {

        reference= FirebaseDatabase.getInstance().getReference("Review");
        String key = reference.push().getKey();
        reference.child(key).setValue(review);




    }


    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(final int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            final ImageView myImageView = customView.findViewById(R.id.myImage);
            final ImageView deleteImage = customView.findViewById(R.id.imageButtonDeletePhotos);
            deleteImage.setVisibility(View.INVISIBLE);
            Picasso.get().load(Uri.parse(imageUrls.get(position))).into(myImageView );
            return customView;
        }
    };



}
