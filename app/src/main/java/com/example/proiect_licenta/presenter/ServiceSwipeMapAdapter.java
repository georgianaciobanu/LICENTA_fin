package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.example.proiect_licenta.model.Upload;
import com.example.proiect_licenta.view.AboutServiceActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ServiceSwipeMapAdapter extends PagerAdapter {

    private List<ServiceDataModel> models;
    private LayoutInflater layoutInflater;
    private Context context;
    String TAG = "ServiceAdapter";
    OnGetDataListener listenerServiceToSend;
    Service serviceToSend = new Service();
    OnGetDataListener listenerImage;
    Upload ex;





    public ServiceSwipeMapAdapter(List<ServiceDataModel> models, Context context) {
        this.models = models;
        this.context = context;

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);


        listenerServiceToSend = new

                OnGetDataListener() {
                    @Override
                    public void onStartFirebaseRequest() {

                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        for (DataSnapshot singleSnapshot : data.getChildren()) {
                            serviceToSend = singleSnapshot.getValue(Service.class);
                            goToAboutService(context);
                        }




                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                };


        View view = layoutInflater.inflate(R.layout.searched_service_row_item, container, false);

        final ImageView imageViewService = view.findViewById(R.id.item_info);
        ImageView loc = view.findViewById(R.id.loc_info);
        TextView textViewName = view.findViewById(R.id.servicename);
        TextView adresa = view.findViewById(R.id.adresa_bd);
        TextView program=view.findViewById(R.id.programservice);




            textViewName.setText(models.get(position).getName());
            adresa.setText(models.get(position).getAdresa());
            program.setText(models.get(position).getProgram());




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFunctions.getServiceFirebase("numeService", models.get(position).getName(), listenerServiceToSend);


            }
        });

        listenerImage = new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for (DataSnapshot singleSnapshot : data.getChildren()) {
                    ex = singleSnapshot.getValue(Upload.class);

                    if (ex.getEmailService().equals(models.get(position).getEmail())) {
                        Picasso.get().load(ex.getImageUrl()).into(imageViewService);
                        break;
                    }
                }




            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        };

        FirebaseFunctions.getImageFire("emailService",models.get(position).getEmail(),listenerImage);


        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


    public void goToAboutService(Context context)
    {


        Intent intent = new Intent();
        intent.setClass(context, AboutServiceActivity.class);
        intent.putExtra("CurrentService", serviceToSend);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }
}
