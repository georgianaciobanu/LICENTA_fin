package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    FirebaseUser firebaseUser;
    ArrayList<ProductsItem> listaProduse;
    ProductImageAdapter mAdapter;
    String clickedProdus;

    String serviceEmail;
    OnGetDataListener listenerUpdateServicii;
    Service currentService;

    ArrayList<Serviciu> serviciiSelectate= new ArrayList<>();
    ArrayList<Serviciu> serviciiService= new ArrayList<>();
    ArrayList<Serviciu> serviciiFirebase=new ArrayList<>();

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


        final ImageButton imageViewDelete;
        final ImageButton imageViewUpdate;
        final ImageView images ;
        final  EditText myTitle ;
        final EditText myDescription ;
        final  EditText myDetails ;
        final Spinner spinner;

        spinner = convertView.findViewById(R.id.spinnerProd);
        images = convertView.findViewById(R.id.image);
        myTitle = convertView.findViewById(R.id.textView1);
        myDescription = convertView.findViewById(R.id.textView2);
        myDetails = convertView.findViewById(R.id.textView3);
        imageViewDelete=convertView.findViewById(R.id.imageViewDelete);
        imageViewUpdate=convertView.findViewById(R.id.imageViewUpdate);





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

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ProductsItem clickedItem = (ProductsItem) parent.getItemAtPosition(position);
                                clickedProdus = clickedItem.getProdus();


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                }


            }


            @Override
            public void onFailed(DatabaseError databaseError) {


            }
        };


        imageViewDelete.setVisibility(View.INVISIBLE);
        imageViewUpdate.setVisibility(View.INVISIBLE);



        if (reqq == 0) {
            FirebaseFunctions.UpdateServicii(serviceEmail, listenerUpdateServicii);
            spinner.setVisibility(View.VISIBLE);
            images.setVisibility(View.INVISIBLE);
            myTitle.setEnabled(true);
            myDescription.setEnabled(true);
            myDetails.setEnabled(true);
            imageViewDelete.setVisibility(View.VISIBLE);
            imageViewUpdate.setVisibility(View.VISIBLE);

        }
        else{
            myTitle.setEnabled(false);
            myDescription.setEnabled(false);
            myDetails.setEnabled(false);
            imageViewDelete.setVisibility(View.INVISIBLE);
            images.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            imageViewUpdate.setVisibility(View.INVISIBLE);
        }


if(reqq!=0) {
    view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            int desiredBackgroundColor = Color.BLUE;

            ColorDrawable viewColor = (ColorDrawable) view.getBackground();

            if (viewColor == null) {
                view.setBackgroundColor(desiredBackgroundColor);
                final Serviciu selectedValue = serviciiService.get(position);
                serviciiSelectate.add(selectedValue);
                return;
            }

            int currentColorId = viewColor.getColor();

            if (currentColorId == desiredBackgroundColor) {
                view.setBackgroundColor(Color.TRANSPARENT);
                final Serviciu selectedValue = serviciiService.get(position);
                serviciiSelectate.remove(selectedValue);
            } else {
                view.setBackgroundColor(desiredBackgroundColor);
                final Serviciu selectedValue = serviciiService.get(position);
                serviciiSelectate.add(selectedValue);

            }

        }
    });

}

        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ArrayList<Serviciu> serviciiFirebaseToRemove= new ArrayList<>();
                serviciiFirebase=currentService.getSevicii();
                for(Serviciu s: serviciiFirebase){
                    if(s.getDenumire().equals(serviciiService.get(position).getDenumire())){

                        serviciiFirebaseToRemove.add(s);

                    }
                }
                serviciiFirebase.removeAll(serviciiFirebaseToRemove);
                currentService.setSevicii(serviciiFirebase);
                FirebaseFunctions.updateServicii(currentService.getServiceId(),serviciiFirebase);

            }
        });
      imageViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ArrayList<Serviciu> serviciiFirebaseToRemove= new ArrayList<>();
                ArrayList<Serviciu> serviciiFirebaseToAdd= new ArrayList<>();
                serviciiFirebase=currentService.getSevicii();
                for(Serviciu s: serviciiFirebase){
                    if(s.getDenumire().equals(serviciiService.get(position).getDenumire())){

                        Serviciu nou= new Serviciu();
                        nou.setDenumire( myTitle.getText().toString());
                        nou.setDetalii(myDetails.getText().toString());
                        nou.setPret(Double.valueOf(myDescription.getText().toString()));



                        nou.setProdus(clickedProdus);

                        serviciiFirebaseToRemove.add(s);
                        serviciiFirebaseToAdd.add(nou);





                    }
                }
                serviciiFirebase.removeAll(serviciiFirebaseToRemove);
                serviciiFirebase.addAll(serviciiFirebaseToAdd);
                currentService.setSevicii(serviciiFirebase);
                FirebaseFunctions.updateServicii(currentService.getServiceId(),serviciiFirebase);


            }
        });


         currentItem = getItem(position);






        int desiredBackgroundColor = Color.RED;


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




