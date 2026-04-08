package com.example.sqllite_shopping;

import static com.example.sqllite_shopping.Percace.KEY_ID;
import static com.example.sqllite_shopping.Percace.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABACE_NAME = "dbexam.db"; // Changed .bd to .db for standard convention
    private static final int DATABACE_VERSION = 1;

    public HelperDB(Context context) {
        super(context, DATABACE_NAME, null, DATABACE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strCreate = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + Percace.DESCRIPTION + " TEXT, "
                + Percace.AMOUNT + " INTEGER, "
                + Percace.CATEGORY + " TEXT, "
                + Percace.DATE + " TEXT"
                + ");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
