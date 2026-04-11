package com.example.sqllite_shopping;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Filter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText des2, mon2;
    Spinner cat2;
    Button fil;
    ListView res;
    
    HelperDB hlp;
    SQLiteDatabase db;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter<String> adt;

    String selectedCategory = "";
    String[] categories = {"All Categories", "food", "clothes", "electronics", "drugs", "other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);

        hlp = new HelperDB(this);

        des2 = findViewById(R.id.des2);
        mon2 = findViewById(R.id.mon2);
        cat2 = findViewById(R.id.cat2);
        fil = findViewById(R.id.fil);
        res = findViewById(R.id.res);

        adt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        cat2.setAdapter(adt);
        cat2.setOnItemSelectedListener(this);

        adt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tbl);
        res.setAdapter(adt);





    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = categories[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedCategory = "";
    }

    public void Filler(View v) {
        tbl.clear();
        db = hlp.getReadableDatabase();

        String selection = "";
        ArrayList<String> selectionArgs = new ArrayList<>();

        String descInput = des2.getText().toString().trim();
        int monInput = Integer.parseInt(mon2.getText().toString().trim());

        if (!descInput.isEmpty()) {
            selection += Percace.DESCRIPTION + " LIKE ?";
            selectionArgs.add("%" + descInput + "%");
        }



        if (!selectedCategory.equals("All Categories")) {
            if (!selection.isEmpty()) selection += " AND ";
            selection += Percace.CATEGORY + " = ?";
            selectionArgs.add(selectedCategory);
        }

        String[] args = selectionArgs.isEmpty() ? null : selectionArgs.toArray(new String[0]);
        String selectionStr = selection.isEmpty() ? null : selection;

        String orderBy = Percace.AMOUNT + " ASC";

        Cursor cursor = db.query(Percace.TABLE_NAME, null, selectionStr, args, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                String d = cursor.getString(cursor.getColumnIndexOrThrow(Percace.DESCRIPTION));
                int a = cursor.getInt(cursor.getColumnIndexOrThrow(Percace.AMOUNT));
                String c = cursor.getString(cursor.getColumnIndexOrThrow(Percace.CATEGORY));
                tbl.add(d + " | " + c + " | $" + a);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        adt.notifyDataSetChanged();
    }


}
