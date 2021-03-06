package com.example.proiect_licenta.presenter;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.view.LoginActivity;
import com.example.proiect_licenta.view.UploadImageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

public class FirebaseFunctions {

    String TAG="FirebaseFunctions";
    FirebaseUser firebaseUser;



    public static void getUserFirebase( final OnGetDataListener listener, String currentUserEmail ){

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() { @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                listener.onSuccess(dataSnapshot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });

    }


    public static void getUserByIdFirebase( final OnGetDataListener listener, String currentUserId ){

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("User").orderByChild("id").equalTo(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() { @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                listener.onSuccess(dataSnapshot);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }
    public static void getBookingFirebase( final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void getServiceFirebase(String child, String value,final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Service").orderByChild(child).equalTo(value)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }
    public static void UpdateServicii( String emailService ,final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference("Service").orderByChild("email").equalTo(emailService).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    public static void getMessagesFirebase(final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("ChatMessage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void getImageFire(String child, String value,final OnGetDataListener listener) {

        listener.onStartFirebaseRequest();

        FirebaseDatabase.getInstance().getReference().child("Image").orderByChild(child).equalTo(value)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

                try {
                    listener.onSuccess(dataSnapshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    public static void updateRequest (Request request, String status){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance()
                .getReference("Request").child(request.getRequestId());
        databaseReference.child("status").setValue(status);
        databaseReference.child("dataTrimiterii").setValue(Calendar.getInstance().getTime());
        request.setStatus(status);

    }

    public static void updateServicii (String idService, ArrayList<Serviciu> servicii){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Service").child(idService);
        databaseReference.child("sevicii").setValue(servicii);




    }

    public static void updateUsernameService (String idService, String username, String proprietar){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Service").child(idService);
        databaseReference.child("username").setValue(username);
        databaseReference.child("proprietar").setValue(proprietar);




    }

    public static void updateUsernameUser (String idUser, String username, String telefon){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User").child(idUser);
        databaseReference.child("username").setValue(username);
        databaseReference.child("telefon").setValue(telefon);




    }
    public static void updateLocatie (String idService, PhysicalLocation locatie){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Service").child(idService);
        databaseReference.child("loc").setValue(locatie);




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
