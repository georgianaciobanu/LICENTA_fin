package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.view.ReviewsFragment;
import com.example.proiect_licenta.view.UploadImageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import java.text.DateFormat;
import java.util.ArrayList;


public class ReviewsAdapter extends ArrayAdapter<Review> {


      OnGetDataListener listenerUser;
      User u;
      String userName;
      Review currentItem;

     TextView tw_nume;
    TextView tw_data;
    RatingBar rating;
    ImageView avatarRew;
    TextView tx_commentRew;


    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        super(context, 0, reviews);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.reviews_item_list, parent, false
            );
        }


       tw_nume = convertView.findViewById(R.id.numeUserRew);
      tw_data = convertView.findViewById(R.id.dataRew);
        rating= convertView.findViewById(R.id.ratingBarList);
        avatarRew=convertView.findViewById(R.id.defaultAvatar);
        tx_commentRew=convertView.findViewById(R.id.tx_commentRew);

        listenerUser=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    u = singleSnapshot.getValue(User.class);
                    if(u.getEmail().equals(currentItem.getIdClient())){
                        userName=u.getUsername();
                        tw_nume.setText(userName);
                        if(currentItem.getData()!=null) {
                           String dateToDisplay= DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(currentItem.getData());
                            tw_data.setText(dateToDisplay);
                        }
                        rating.setRating(Float.valueOf(currentItem.getRateValue()));
                        avatarRew.setImageResource(R.drawable.ic_person_black_24dp);
                        tx_commentRew.setText(currentItem.getComment());

                    }
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        };


        currentItem = getItem(position);
           if(currentItem!=null) {

               FirebaseFunctions.getUserFirebase(listenerUser,currentItem.getIdClient());


       }

        return convertView;
    }


    }
