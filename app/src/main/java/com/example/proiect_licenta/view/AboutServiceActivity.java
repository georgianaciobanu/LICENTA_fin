package com.example.proiect_licenta.view;

import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ServicesListAdapter;
import com.example.proiect_licenta.presenter.TrimiteCerereActivity;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class AboutServiceActivity extends AppCompatActivity implements RatingDialogListener {
    Button cerere;
    TextView listaServicii;
    TextView btnAdresa;
    TextView proprietar;
    TextView program;
    TextView telefon;
    OnGetDataListener listenerLocation;
    TextView email;
    TextView descriere;
    TextView nume;
    Service currentService;
    OnGetDataListener listenerCurrentReview;
    OnGetDataListener listenerReview;
    Review rating;

    PhysicalLocation deviceLocationNow;
PhysicalLocation currentLocation;
    RatingBar ratingBar;
    DatabaseReference reference;
    String currentuser;
    Review currentReview;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_service);

        Intent i = getIntent();

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
         currentuser= firebaseUser.getEmail();
         final String currentUserId = firebaseUser.getUid();
        currentReview=new Review();
        currentService=(Service) i.getSerializableExtra("CurrentService");
        cerere = (Button) findViewById(R.id.BTN_timiteCerere);
        proprietar = (TextView) findViewById(R.id.tw_proprietarService);
        program = (TextView) findViewById(R.id.tw_programService);
        descriere = (TextView) findViewById(R.id.tw_descriereService);
        telefon = (TextView) findViewById(R.id.tw_telefonService);
        email = (TextView) findViewById(R.id.tw_mailService);
        nume = (TextView) findViewById(R.id.tw_NumeService);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);



        listaServicii = (TextView) findViewById(R.id.tw_listaServicii);
        btnAdresa = (TextView) findViewById(R.id.tw_adresaService);


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
                    }

                }
                  if(sum>0 && count>0){
                      ratingBar.setRating(sum/count);
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
                    currentReview = singleSnapshot.getValue(Review.class);
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


//        final PhysicalLocation adresaMea= new PhysicalLocation();
//        adresaMea.setAdresa("Bloc 37, Strada Lerești, București");
//        adresaMea.setLatitudine(44.403052);
//        adresaMea.setLogitudine(26.0531606);




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
                                    // goToMaps(22.304634,73.161070,23.022242,72.548844);
                                    if(deviceLocationNow!=null) {
                                        goToMaps(deviceLocationNow.getLatitudine(),
                                                deviceLocationNow.getLogitudine(),
                                                currentService.getLoc().getLatitudine(),
                                                currentService.getLoc().getLogitudine());
                                    }
                                }
                            });


                        }




                        //rating bar

        FloatingActionButton fab = findViewById(R.id.button_rating);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showRatingDialog();
            }
        });

                    }




    public void goToCerere() {
        Intent itReq = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
        itReq.putExtra("CurrentService",currentService);
        startActivityForResult(itReq,0);



    }

    public void goToListaServicii() {
        Intent it = new Intent(this, ServicesListActivity.class);
        it.putExtra("Servicii",currentService.getSevicii());
        startActivity(it);

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
        proprietar.setText(currentService.getProprietar());
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

        FirebaseFunctions.getReviewFirebase("idService",currentService.getServiceId(),listenerReview);


    }


    public void reviewInsertFirebase(final Review review) {

        reference= FirebaseDatabase.getInstance().getReference("Review");
        String key = reference.push().getKey();
        reference.child(key).setValue(review);




    }




}
