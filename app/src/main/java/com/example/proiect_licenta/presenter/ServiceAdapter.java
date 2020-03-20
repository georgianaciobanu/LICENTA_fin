package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.example.proiect_licenta.view.AboutServiceActivity;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;


public class ServiceAdapter extends ArrayAdapter<ServiceDataModel> {
    String TAG = "ServiceAdapter";
    OnGetDataListener listenerServiceToSend;
    Service serviceToSend = new Service();
    public ServiceAdapter(Context context, ArrayList<ServiceDataModel> serviceList) {
        super(context, 0, serviceList);
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

        listenerServiceToSend = new

                OnGetDataListener() {
                    @Override
                    public void onStartFirebaseRequest() {

                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        for (DataSnapshot singleSnapshot : data.getChildren()) {
                            serviceToSend = singleSnapshot.getValue(Service.class);
                            goToAboutService();
                        }




                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                };
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.searched_service_row_item, parent, false
            );
        }

        ImageView imageViewService = convertView.findViewById(R.id.item_info);
        ImageView loc = convertView.findViewById(R.id.loc_info);
        TextView textViewName = convertView.findViewById(R.id.servicename);
        MultiAutoCompleteTextView adresa = convertView.findViewById(R.id.adresa_bd);
        final ServiceDataModel currentItem = getItem(position);

        if (currentItem != null) {

            textViewName.setText(currentItem.getName());
            adresa.setText(currentItem.getAdresa());

        }

        imageViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, currentItem.getName());
                FirebaseFunctions.getServiceFirebase("numeService", currentItem.getName(), listenerServiceToSend);

            }
        });


        return convertView;
    }




    public void goToAboutService()
    {


        Intent intent = new Intent();
        intent.setClass(getContext(), AboutServiceActivity.class);
        intent.putExtra("CurrentService", serviceToSend);
        getContext().startActivity(intent);


    }
}
