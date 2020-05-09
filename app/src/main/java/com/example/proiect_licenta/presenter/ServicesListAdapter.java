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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.ProductsItem;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServicesListAdapter extends ArrayAdapter<Serviciu> {

    int rImages[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};
    int reqq=1;
    Serviciu currentItem;
    ImageView images ;
    EditText myTitle ;
    EditText myDescription ;
    EditText myDetails ;
    ImageButton imageViewDelete;
    FirebaseUser firebaseUser;
    ArrayList<ProductsItem> listaProduse;
    ProductImageAdapter mAdapter;
    Spinner spinner;
    String serviceEmail;
    OnGetDataListener listenerUpdateServicii;
    Service currentService;

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
         firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
         serviceEmail=firebaseUser.getEmail();

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
        final View view=convertView;
        spinner = convertView.findViewById(R.id.spinnerProd);
        listenerUpdateServicii = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    currentService  = singleSnapshot.getValue(Service.class);
                   if (currentService.getEmail().equals(serviceEmail)) {
                       initList();
                       mAdapter = new ProductImageAdapter(view.getContext(), listaProduse);
                       spinner.setAdapter(mAdapter);

                   }
//
//                        serviciiFirebase=currentService.getSevicii();
//                        for(Serviciu s: serviciiFirebase){
//                            if(s.getDenumire().equals(servicii.get(position).getDenumire())){
//
//                                s.setDenumire( myTitle.getText().toString());
//                                s.setDetalii(myDetails.getText().toString());
//                                s.setPret(Double.valueOf(myDescription.getText().toString()));
//
//                                currentService.setSevicii(serviciiFirebase);
//
//
//
//                            }
//                        }
                    // FirebaseFunctions.updateServicii(currentService.getServiceId(),serviciiFirebase);





                    //}
                }





            }

            @Override
            public void onFailed(DatabaseError databaseError) {


            }
        };


if(reqq==0) {

    FirebaseFunctions.UpdateServicii(serviceEmail, listenerUpdateServicii);


}
         images = convertView.findViewById(R.id.image);
         myTitle = convertView.findViewById(R.id.textView1);
         myDescription = convertView.findViewById(R.id.textView2);
         myDetails = convertView.findViewById(R.id.textView3);
         imageViewDelete=convertView.findViewById(R.id.imageViewDelete);



         imageViewDelete.setVisibility(View.INVISIBLE);
        if (reqq == 0) {
            spinner.setVisibility(View.VISIBLE);
            images.setVisibility(View.INVISIBLE);
            myTitle.setEnabled(true);
            myDescription.setEnabled(true);
            myDetails.setEnabled(true);
            imageViewDelete.setVisibility(View.VISIBLE);


        }
        else{
            myTitle.setEnabled(false);
            myDescription.setEnabled(false);
            myDetails.setEnabled(false);
            imageViewDelete.setVisibility(View.INVISIBLE);
            images.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.INVISIBLE);
        }




         currentItem = getItem(position);






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

    private void initList() {
        listaProduse = new ArrayList<>();
        for(String produs :currentService.getProduse()){
            if(produs.equals("ELECTROCASNICE")) {
                listaProduse.add(new ProductsItem("ELECTROCASNICE", R.drawable.electrocasnice));
            }

            if(produs.equals("TELEFON/TABLETA")) {
                listaProduse.add(new ProductsItem("TELEFON/TABLETA", R.drawable.telefon));
            }
            if(produs.equals("MASINA")) {
                listaProduse.add(new ProductsItem("MASINA", R.drawable.masina));
            }
            if(produs.equals("PC/LAPTOP")) {
                listaProduse.add(new ProductsItem("PC/LAPTOP", R.drawable.pc));
            }
        }

    }
}




