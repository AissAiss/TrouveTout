package com.example.trouvetout.Helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.trouvetout.models.Annonce;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Annonce> annonces = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public Boolean save(Annonce annonce)
    {
        if(annonce==null)
        {
            saved=false;
        }else {

            try
            {
                db.child("annonces").push().setValue(annonce);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }

        }

        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        //annonces.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Annonce annonce = ds.getValue(Annonce.class);
            annonces.add(annonce);
        }
    }

    //READ
    public ArrayList<Annonce> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return annonces;
    }



}
