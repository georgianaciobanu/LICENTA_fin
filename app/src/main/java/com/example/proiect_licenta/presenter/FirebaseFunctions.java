package com.example.proiect_licenta.presenter;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.view.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseFunctions {

    String TAG="FirebaseFunctions";
    FirebaseUser firebaseUser;



    public static void getUserFirebase( final OnGetDataListener listener, String currentUserEmail ){

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() { @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            listener.onSuccess(dataSnapshot);
        }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });

    }



    public static void  getRequestsFirebase( final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();
        FirebaseDatabase.getInstance().getReference().child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

        public static void getServicesFirebase( final OnGetDataListener listener) {

            listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }
    public static void readRequestService(String child, final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child(child).orderByChild("email").equalTo("servicegeo@mail.com").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void getRequestIdFirebase(String child, final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Request").orderByChild("requestId").equalTo("-M0nFBg1cckdaSl6Qj9O").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }
    public static void getBookingFirebase( final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Request").orderByChild("status").equalTo("confirmata").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void getServiceFirebase(String child, String value,final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Service").orderByChild(child).equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    public static void getReviewFirebase(String child, String value,final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Review").orderByChild(child).equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void getLocationFirebase(String child, String value,final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Location").orderByChild(child).equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    public static void updateRequest (Request request, String status){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Request").child(request.getRequestId());
        databaseReference.child("status").setValue(status);
        request.setStatus(status);



    }





    public static void forgetPass(String email){

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("FirebaseFunctions", "Email sent.");
                        }
                    }
                });


    }

}
