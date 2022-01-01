package com.example.blooddonors;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;

public class DonorDbFireBase implements DonorDbInterface {

    public interface DataObserver {
        public void update();
    }

    private static FirebaseDatabase rootNode;
    private static DatabaseReference reference;
    private static DataObserver observer;
    private static ArrayList<Donor> donors;


    public DonorDbFireBase(DataObserver obs) {
        rootNode = setup();
        observer = obs;
        reference = rootNode.getReference("donors");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donors = new ArrayList<Donor>();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Donor d = dsp.getValue(Donor.class);
                    donors.add(d);
                }
                observer.update();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());

            }
        });
    }
    public static FirebaseDatabase setup() {
        if (rootNode == null) {
            rootNode = FirebaseDatabase.getInstance();
            rootNode.setPersistenceEnabled(true);
        }
        return rootNode;




    }
    @Override
    public ArrayList<Donor> getDonorsList() {
        return donors;
    }

    @Override
    public void addDonor(Donor donor) {
//        DatabaseReference donorsReference = reference.push();
//        donorsReference.setValue(donor);
        reference.child(donor.getName()).setValue(donor);
    }
}
