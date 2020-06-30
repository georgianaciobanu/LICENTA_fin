package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.example.proiect_licenta.model.Upload;
import com.example.proiect_licenta.view.AboutServiceActivity;
import com.example.proiect_licenta.view.SearchedServiceActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavServicesAdapter extends ArrayAdapter<Service> {
    OnGetDataListener listenerImage;
    Upload ex;
    Context cont;
      List<Service> models;
    public FavServicesAdapter(Context context, ArrayList<Service> countryList) {
        super(context, 0, countryList);
        models=countryList;
        cont=context;
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

    private View initView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.fav_services, parent, false
            );
        }

        final ProgressDialog dialog = new ProgressDialog(cont);
        dialog.setMessage("Please wait.....");
        dialog.show();
        final int pos=position;
        final ImageView imageViewService = convertView.findViewById(R.id.item_info);
        ImageView loc = convertView.findViewById(R.id.loc_info);
        TextView textViewName = convertView.findViewById(R.id.servicename);
        TextView adresa = convertView.findViewById(R.id.adresa_bd);
        TextView program=convertView.findViewById(R.id.programservice);


        listenerImage = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    ex = singleSnapshot.getValue(Upload.class);

                    if (ex.getEmailService().equals(models.get(pos).getEmail())) {
                        Picasso.get().load(ex.getImageUrl()).into(imageViewService);
                        break;

                    }
                }



                dialog.hide();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        };

        FirebaseFunctions.getImageFire("emailService",models.get(position).getEmail(),listenerImage);


        final Service currentItem = getItem(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAboutService(currentItem);

            }
        });

        if (currentItem != null) {
            textViewName.setText(currentItem.getNumeService());
            adresa.setText(currentItem.getLoc().getAdresa());
            program.setText(currentItem.getProgram());
        }
        return convertView;
    }

    public void goToAboutService(Service currentItem)
    {


        Intent intent = new Intent();
        intent.setClass(getContext(), AboutServiceActivity.class);
        intent.putExtra("CurrentService",currentItem );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);


    }
}