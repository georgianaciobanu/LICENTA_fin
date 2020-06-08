package com.example.proiect_licenta.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Upload;
import com.example.proiect_licenta.view.UpdatePhotosActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotosAdapter extends ArrayAdapter<Upload> {

ArrayList<Upload> uploadsCopy= new ArrayList<>();
Context cont;
    public PhotosAdapter(Context context, ArrayList<Upload> uploads) {
        super(context, 0, uploads);
        uploadsCopy=uploads;
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.view_custom, parent, false
            );
            viewHolder=new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.myImage);
            viewHolder.imageButtonDeletePhotos=convertView.findViewById(R.id.imageButtonDeletePhotos);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder ) convertView.getTag();
        }


        final Upload currentItem = getItem(position);

        if (currentItem != null) {
            String url=currentItem.getImageUrl();
            Picasso.get().load(Uri.parse(currentItem.getImageUrl())).into(viewHolder.imageView  );
        }
        final String url=currentItem.getImageUrl();
        viewHolder.imageButtonDeletePhotos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Image");
                Query applesQuery = ref.orderByChild("imageUrl").equalTo(url);

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            uploadsCopy.remove(position);
                            notifyDataSetChanged();

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        return convertView;
    }

    static class ViewHolder {
        private  ImageView imageView;
        private ImageView imageButtonDeletePhotos;
    }
}

