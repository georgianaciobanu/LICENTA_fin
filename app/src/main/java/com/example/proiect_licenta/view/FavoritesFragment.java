package com.example.proiect_licenta.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.example.proiect_licenta.presenter.FavServicesAdapter;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ReviewsAdapter;
import com.example.proiect_licenta.presenter.ServiceSwipeMapAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {
    View view;
    OnGetDataListener listenerFavoritesService;
    OnGetDataListener listenerBestReviews;
    ArrayList<String> servicesIds=new ArrayList<>();
    ListView listaFavServices;
    ArrayList<Service> favServices= new ArrayList<>();
    List<ServiceDataModel> dataModelList;
    Service service;
    Review review;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog ;
   // ServiceSwipeMapAdapter adpater;
    //FavServicesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_favorites, container, false);



        progressDialog = new ProgressDialog(getContext());

        progressDialog.setTitle("Loading");
        progressDialog.show();


        listaFavServices=(ListView)view.findViewById(R.id.lista_fav_services) ;
        service=new Service();
        review=new Review();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserEmail= firebaseUser.getEmail();

        listenerFavoritesService=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    service = singleSnapshot.getValue(Service.class);
                    if(servicesIds.contains(service.getServiceId())) {
                        favServices.add(service);
                    }

                }

                if(favServices.size()>0){
                    FavServicesAdapter  adapter = new FavServicesAdapter(view.getContext(),favServices);
                    listaFavServices.setAdapter(adapter);
                     progressDialog.hide();
                }else{
                     progressDialog.hide();
                    Toast.makeText(view.getContext(),"Nu aveti service-uri favorite!",Toast.LENGTH_LONG).show();

                }

            }







            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(view.getContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };


        listenerBestReviews=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {
               if(data==null) {
                   Toast.makeText(view.getContext(),"Nu aveti service-uri favorite!",Toast.LENGTH_LONG).show();
                   progressDialog.hide();
               }


                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    review = singleSnapshot.getValue(Review.class);
                    if(Integer.parseInt(review.getRateValue())==5){
                        servicesIds.add(review.getIdService());

                    }
                }


                    FirebaseFunctions.getServicesFirebase(listenerFavoritesService);


            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        };

        FirebaseFunctions.getReviewFirebase("idClient",currentUserEmail,listenerBestReviews);









        return view;
    }
}