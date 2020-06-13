package com.example.proiect_licenta.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ReviewsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;


public class ReviewsFragment extends Fragment {
View view;
OnGetDataListener listenerCurrentReview;
    Review currentReview;
    ListView listaReviews;
    ArrayList<Review> reviews;
    ReviewsAdapter reviewsAdapter;
    RatingBar ratingBar;
    Intent it;
    OnGetDataListener listenerServ;
    Service service;
    FirebaseUser firebaseUser;
    Service currentService= new Service();
    ProgressDialog progressDialog ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_reviews, container, false);



        progressDialog = new ProgressDialog(getContext());

        progressDialog.setTitle("Loading");
        progressDialog.show();


        listaReviews=(ListView)view.findViewById(R.id.lista_reviews_serv) ;
        currentReview=new Review();
        reviews=new ArrayList<>();
        ratingBar=(RatingBar)view.findViewById(R.id.ratingBarServ);
        ratingBar.isIndicator();


        service=new Service();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserEmail= firebaseUser.getEmail();
        listenerServ=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    service = singleSnapshot.getValue(Service.class);
                    if(service.getEmail().equals(currentUserEmail)){
                        currentService=service;
                        FirebaseFunctions.getReviewFirebase("idService",currentService.getServiceId(),listenerCurrentReview);
                    }
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        };

        FirebaseFunctions.getServiceFirebase("email",currentUserEmail,listenerServ);



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
                    reviewsAdapter = new ReviewsAdapter(view.getContext(), reviews);
                    listaReviews.setAdapter(reviewsAdapter);
                    progressDialog.hide();
                }


                progressDialog.hide();

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(view.getContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };





        return view;
    }
}