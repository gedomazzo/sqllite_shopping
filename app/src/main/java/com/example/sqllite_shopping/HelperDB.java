package com.example.sqllite_shopping;

import static com.example.sqllite_shopping.Percace.KEY_ID;
import static com.example.sqllite_shopping.Percace.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** 
* @author        benjamin rogachevsky 
* @version       1.0
* @since         4/11/26
* Helper class for managing the SQLite database creation and version management.
*/
public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABACE_NAME = "dbexam.db"; // Changed .bd to .db for standard convention
    private static final int DATABACE_VERSION = 1;

    /** 
    * Constructor for HelperDB
    * <p>
    * 
    * @param context the context of the activity
    * @return description HelperDB object
    */
    public HelperDB(Context context) {
        super(context, DATABACE_NAME, null, DATABACE_VERSION);
    }

    /** 
    * Called when the database is created for the first time
    * <p>
    * 
    * @param db the database being created
    * @return description void
    */
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

    /** 
    * Called when the database needs to be upgraded
    * <p>
    * 
    * @param db the database
    * @param oldVersion the old version number
    * @param newVersion the new version number
    * @return description void
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
