package com.example.a1kayat34.stepsmeasurerapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;

/**
 * Created by kayaya on 12/01/2018.
 */

public class MyHelper extends SQLiteOpenHelper{
    static final int VERSION =1;
    static final String DATABASE_NAME = "stepmeasurerDB";
    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Steps (id INTEGER PRIMARY KEY, nsteps VARCHAR(255), date VARCHAR(255))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Steps");
        onCreate(db);

    }

    //Add a step
    public long add(String steps, String date){

        SQLiteDatabase db = getWritableDatabase();


        SQLiteStatement stmt = db.compileStatement
                ("INSERT INTO Steps(nsteps,date) VALUES (?, ?)");
        stmt.bindString (1, steps);
        stmt.bindString (2, date);
        long id = stmt.executeInsert();
        return id;
    }

    //Find record with id
    public Step search(long id){

        Step step = null;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Steps WHERE id=?",
                new String[] {""+id});
        if(cursor.moveToFirst()){
            step = new Step(cursor.getString(cursor.getColumnIndex("nsteps")),
                    cursor.getString(cursor.getColumnIndex("date")));
        }
        //Return song object if the song was found or null if it has not.
        return step;
    }



}
