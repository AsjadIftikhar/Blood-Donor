package com.example.blooddonors;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Hashtable;

public class DonorDbSqlite implements DonorDbInterface {
    private Context context;

    public DonorDbSqlite(Context ctx) {
        context = ctx;
    }

    @SuppressLint("Range")
    @Override
    public ArrayList<Donor> getDonorsList() {
        DonorDbHelper dbHelper = new DonorDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Donors";
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Donor> objects = new ArrayList<Donor>();
        while (cursor.moveToNext()) {
            Hashtable<String, String> obj = new Hashtable<String, String>();
            String[] columns = cursor.getColumnNames();
            for (String col : columns) {
                if (!col.equals("id")) {
                    String s = cursor.getString(cursor.getColumnIndex(col));
                    if (s != null) {
                        obj.put(col, s);
                    }

                }
            }
            Donor donor = Donor.HashTableToObj(obj);
            objects.add(donor);
        }
        cursor.close();
        db.close();
        return objects;
    }

    @Override
    public void addDonor(Donor donor) {

        DonorDbHelper dbHelper = new DonorDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Hashtable<String, String> hashtable = donor.ObjToHashTable();
        for (String key : hashtable.keySet()) {
            content.put(key, hashtable.get(key));
        }

        long res = db.insert("Donors", null, content);
        if (res == -1)
            System.out.println("Something Went Wrong");
    }
}
