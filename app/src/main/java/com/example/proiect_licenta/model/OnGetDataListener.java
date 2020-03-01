package com.example.proiect_licenta.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface OnGetDataListener {

    public void onStartFirebaseRequest();
    public void onSuccess(DataSnapshot data);
    public void onFailed(DatabaseError databaseError);

}
