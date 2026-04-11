package com.example.sqllite_shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Show extends AppCompatActivity implements AdapterView.OnItemClickListener{


    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ListView dshow;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter<String> adp;

    AlertDialog.Builder allert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();
        crsr = db.query(Percace.TABLE_NAME, null, null, null, null, null, null);
        dshow = findViewById(R.id.dshow);

        int col0 = crsr.getColumnIndex(Percace.KEY_ID);
        int col1 = crsr.getColumnIndex(Percace.DESCRIPTION);
        int col2 = crsr.getColumnIndex(Percace.AMOUNT);
        int col3 = crsr.getColumnIndex(Percace.CATEGORY);
        int col4 = crsr.getColumnIndex(Percace.DATE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col0);
            String des = crsr.getString(col1);
            int mon = crsr.getInt(col2);
            String cat = crsr.getString(col3);
            String dat = crsr.getString(col4);

            String tmp = "" + key + ", " + des + ", " + mon + ", " + cat + ", " + dat;
            tbl.add(tmp);
            crsr.moveToNext();
        }
        Log.i("SQL", "everything is fine, table read");
        crsr.close();
        db.close();

        adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tbl);
        dshow.setOnItemClickListener(this);
        dshow.setAdapter(adp);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id){
        String tble = tbl.get(pos);

        allert = new AlertDialog.Builder(this);
        allert = new AlertDialog.Builder(this);
        allert.setTitle("Are you sure ?");
        allert.setMessage("Are you sure you want to delete " + tble + "?");

        allert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = hlp.getWritableDatabase();
                db.delete(Percace.TABLE_NAME, Percace.KEY_ID+"=?", new String[]{Integer.toString(pos + 1)});

                db.close();
                tbl.remove(pos);
                adp.notifyDataSetChanged();
            }
        });
        Log.i("SQL", "everything is fine, item deleted");


        allert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        allert.show();
    }

}