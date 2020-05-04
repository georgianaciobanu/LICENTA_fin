package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServicesListAdapter extends ArrayAdapter<Serviciu> {

    int rImages[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};
    int reqq=1;
    ProgressDialog pg;
    Serviciu serviciu;
    ArrayList<Serviciu> serviciiFirebase;
    OnGetDataListener listenerUpdateServicii;
    Serviciu currentItem;

    ImageView images ;
    EditText myTitle ;
    EditText myDescription ;
    EditText myDetails ;
    ImageButton imageViewDelete;

    ArrayList<Serviciu> serviciiSelectate= new ArrayList<>();
    ArrayList<Serviciu> serviciiService= new ArrayList<>();
    public ServicesListAdapter(Context context, ArrayList<Serviciu> servicesList,ArrayList<Serviciu> servicesListSelected, int req) {
        super(context, 0, servicesList);
        if(servicesList!=null)
        serviciiService=servicesList;
        if(servicesListSelected!=null)
            serviciiSelectate = servicesListSelected;

     if(req==0){
         reqq=0;
     }
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
                    R.layout.row, parent, false
            );
        }
        listenerUpdateServicii = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    serviciu = singleSnapshot.getValue(Serviciu.class);
                    if (serviciu.getDenumire().equals(currentItem.getDenumire())) {

                        HashMap<String, Object> result1 = new HashMap<>();
                        result1.put("denumire", myTitle.getText().toString());

                        HashMap<String, Object> result2 = new HashMap<>();
                        result2.put("detalii", myDescription.getText().toString());

                        HashMap<String, Object> result3 = new HashMap<>();
                        result3.put("pret", myDetails.getText().toString());


                        singleSnapshot.getRef().updateChildren(result1);
                        singleSnapshot.getRef().updateChildren(result2);
                        singleSnapshot.getRef().updateChildren(result3);


                        //pg.hide();
                    }
                }


                //pg.hide();


            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                pg.hide();

            }
        };
         images = convertView.findViewById(R.id.image);
         myTitle = convertView.findViewById(R.id.textView1);
         myDescription = convertView.findViewById(R.id.textView2);
         myDetails = convertView.findViewById(R.id.textView3);
         imageViewDelete=convertView.findViewById(R.id.imageViewDelete);
        imageViewDelete.setVisibility(View.INVISIBLE);
        if (reqq == 0) {

            myTitle.setEnabled(true);
            myDescription.setEnabled(true);
            myDetails.setEnabled(true);
            imageViewDelete.setVisibility(View.VISIBLE);

        }
        else{
            myTitle.setEnabled(false);
            myDescription.setEnabled(false);
            myDetails.setEnabled(false);
        }


         currentItem = getItem(position);

        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFunctions.UpdateServicii(position, listenerUpdateServicii);
            }
        });



        int desiredBackgroundColor = Color.BLUE;


            if(serviciiSelectate.contains(currentItem)){

                convertView.setBackgroundColor(desiredBackgroundColor);

            }


        if (currentItem != null) {

            myTitle.setText(currentItem.getDenumire());
            myDescription.setText(currentItem.getPret().toString());
            myDetails.setText(currentItem.getDetalii());
            if (currentItem.getProdus().equals("ELECTROCASNICE") )
                images.setImageResource(rImages[0]);
            if (currentItem.getProdus().equals("TELEFON/TABLETA") )
                images.setImageResource(rImages[1]);
            if (currentItem.getProdus().equals("MASINA"))
                images.setImageResource(rImages[2]);
            if (currentItem.getProdus().equals("PC/LAPTOP"))
                images.setImageResource(rImages[3]);
        }


        return convertView;
    }
}

