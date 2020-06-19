package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
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
import androidx.core.content.ContextCompat;

public class ServicesListAdapter extends ArrayAdapter<Serviciu> {

    int rImages[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};
    int reqq=1;
    Serviciu currentItem;
    int isCheked;

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



    public ServicesListAdapter(Context context, ArrayList<Serviciu> servicesList,ArrayList<Serviciu> servicesListSelected, int req, int isChecked) {
        super(context, 0, servicesList);
        if(servicesList!=null)
        serviciiService=servicesList;
        if(servicesListSelected!=null)
            serviciiSelectate = servicesListSelected;
        isCheked=isChecked;




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
    final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row, parent, false
            );
            viewHolder=new ViewHolder();
            viewHolder.spinner = convertView.findViewById(R.id.spinnerProd);
            viewHolder.images = convertView.findViewById(R.id.image);
            viewHolder.myTitle = convertView.findViewById(R.id.textView1);
            viewHolder.myDescription = convertView.findViewById(R.id.textView2);
            viewHolder.myDetails = convertView.findViewById(R.id.textView3);
            viewHolder.imageViewDelete=convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewUpdate=convertView.findViewById(R.id.imageViewUpdate);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final View convertView2=convertView;


//        final ImageButton imageViewDelete;
//        final ImageButton imageViewUpdate;
//        final ImageView images ;
//        final  EditText myTitle ;
//        final EditText myDescription ;
//        final  EditText myDetails ;
//        final Spinner spinner;

//        spinner = convertView.findViewById(R.id.spinnerProd);
//        images = convertView.findViewById(R.id.image);
//        myTitle = convertView.findViewById(R.id.textView1);
//        myDescription = convertView.findViewById(R.id.textView2);
//        myDetails = convertView.findViewById(R.id.textView3);
//        imageViewDelete=convertView.findViewById(R.id.imageViewDelete);
//        imageViewUpdate=convertView.findViewById(R.id.imageViewUpdate);





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
                        mAdapter = new ProductImageAdapter(convertView2.getContext(), listaProduse);
                       viewHolder.spinner.setAdapter(mAdapter);

                        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        viewHolder.imageViewDelete.setVisibility(View.INVISIBLE);
        viewHolder.imageViewUpdate.setVisibility(View.INVISIBLE);



        if (reqq == 0) {
            FirebaseFunctions.UpdateServicii(serviceEmail, listenerUpdateServicii);
            viewHolder.spinner.setVisibility(View.VISIBLE);
            viewHolder.images.setVisibility(View.INVISIBLE);
            viewHolder.myTitle.setEnabled(true);
            viewHolder.myDescription.setEnabled(true);
            viewHolder.myDetails.setEnabled(true);
            viewHolder.imageViewDelete.setVisibility(View.VISIBLE);
            viewHolder.imageViewUpdate.setVisibility(View.VISIBLE);

        }
        else{
            viewHolder.myTitle.setEnabled(false);
            viewHolder.myDescription.setEnabled(false);
            viewHolder.myDetails.setEnabled(false);
            viewHolder.imageViewDelete.setVisibility(View.INVISIBLE);
            viewHolder.images.setVisibility(View.VISIBLE);
            viewHolder.spinner.setVisibility(View.INVISIBLE);
            viewHolder.imageViewUpdate.setVisibility(View.INVISIBLE);
        }


if(reqq!=0  && isCheked==1 ) {
    convertView2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            int desiredBackgroundColor = getContext().getResources().getColor(R.color.middleBlue);

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

        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
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

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) convertView2.findViewById(R.id.custom_toast_container));

                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("Informatiile au fost modificate cu succes");

                Toast toast = new Toast(getContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

                FirebaseFunctions.updateServicii(currentService.getServiceId(),serviciiFirebase);

            }
        });
      viewHolder.imageViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ArrayList<Serviciu> serviciiFirebaseToRemove= new ArrayList<>();
                ArrayList<Serviciu> serviciiFirebaseToAdd= new ArrayList<>();
                serviciiFirebase=currentService.getSevicii();
                for(Serviciu s: serviciiFirebase){
                    if(s.getDenumire().equals(serviciiService.get(position).getDenumire())){

                        Serviciu nou= new Serviciu();
                        nou.setDenumire( viewHolder.myTitle.getText().toString());
                        nou.setDetalii(viewHolder.myDetails.getText().toString());
                        String[] separated = viewHolder.myDescription.getText().toString().split(" ");

                        nou.setPret(Double.valueOf(separated[0]));



                        nou.setProdus(clickedProdus);

                        serviciiFirebaseToRemove.add(s);
                        serviciiFirebaseToAdd.add(nou);





                    }
                }
                serviciiFirebase.removeAll(serviciiFirebaseToRemove);
                serviciiFirebase.addAll(serviciiFirebaseToAdd);
                currentService.setSevicii(serviciiFirebase);

                FirebaseFunctions.updateServicii(currentService.getServiceId(),serviciiFirebase);
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) convertView2.findViewById(R.id.custom_toast_container));

                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("Informatiile au fost modificate cu succes");

                Toast toast = new Toast(getContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();


            }
        });


         currentItem = getItem(position);






        int desiredBackgroundColor = getContext().getResources().getColor(R.color.middleBlue);


            if(serviciiSelectate.contains(currentItem)){

                convertView.setBackgroundColor(desiredBackgroundColor);

            }



        if (currentItem != null) {

            viewHolder.myTitle.setText(currentItem.getDenumire());
            viewHolder.myDescription.setText(currentItem.getPret().toString()+" RON");
            viewHolder.myDetails.setText(currentItem.getDetalii());



    if (currentItem.getProdus().equals("ELECTROCASNICE") )
        viewHolder.images.setImageResource(rImages[0]);
    if (currentItem.getProdus().equals("TELEFON/TABLETA") )
        viewHolder.images.setImageResource(rImages[1]);
    if (currentItem.getProdus().equals("MASINA"))
        viewHolder.images.setImageResource(rImages[2]);
    if (currentItem.getProdus().equals("PC/LAPTOP"))
        viewHolder.images.setImageResource(rImages[3]);

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

    static class ViewHolder {
         ImageButton imageViewDelete;
         ImageButton imageViewUpdate;
         ImageView images ;
          EditText myTitle ;
         EditText myDescription ;
          EditText myDetails ;
          Spinner spinner;
    }
}




