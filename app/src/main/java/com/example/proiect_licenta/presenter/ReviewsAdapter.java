package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
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



    Review currentItem;


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
        final Viewholder viewHolder;


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.reviews_item_list, parent, false
            );
            viewHolder = new Viewholder();

            viewHolder.tw_nume = convertView.findViewById(R.id.numeUserRew);
            viewHolder.tw_data = convertView.findViewById(R.id.dataRew);
            viewHolder.rating = convertView.findViewById(R.id.ratingBarList);
            viewHolder.avatarRew = convertView.findViewById(R.id.defaultAvatar);
            viewHolder.tx_commentRew = convertView.findViewById(R.id.tx_commentRew);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }


        currentItem = getItem(position);
        if (currentItem != null) {

            viewHolder.tw_nume.setText(currentItem.getIdClient());
            if (currentItem.getData() != null) {
                String dateToDisplay = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(currentItem.getData());
                viewHolder.tw_data.setText(dateToDisplay);
            }
            viewHolder.rating.setRating(Float.valueOf(currentItem.getRateValue()));
            viewHolder.avatarRew.setImageResource(R.drawable.ic_person_black_24dp);
            viewHolder.tx_commentRew.setText(currentItem.getComment());
        }




        return convertView;
    }
    static class Viewholder {
         TextView tw_nume;
         TextView tw_data;
         RatingBar rating;
         ImageView avatarRew;
         TextView tx_commentRew;
    }

    }
