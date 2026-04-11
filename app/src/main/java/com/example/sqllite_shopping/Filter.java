package com.example.sqllite_shopping;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

/** 
* @author        benjamin rogachevsky 
* @version       1.0
* @since          4/11/26
* Activity class that handles filtering shopping items by category and description.
*/
public class Filter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText des2;
    private Spinner cat2;
    private Button fil;
    private ListView res;
    
    private HelperDB hlp;
    private SQLiteDatabase db;
    private ArrayList<String> tbl = new ArrayList<>();
    private ArrayAdapter<String> adt;

    private String selectedCategory = "";
    private final String[] categories = {"All Categories", "food", "clothes", "electronics", "drugs", "other"};

    /** 
    * Initializes the activity, sets up the UI components and adapters.
    * <p>
    * 
    * @param savedInstanceState
    * @return description void
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);

        hlp = new HelperDB(this);

        des2 = findViewById(R.id.des2);
        cat2 = findViewById(R.id.cat2);
        fil = findViewById(R.id.fil);
        res = findViewById(R.id.res);

        adt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        cat2.setAdapter(adt);
        cat2.setOnItemSelectedListener(this);

        adt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tbl);
        res.setAdapter(adt);
    }


    /** 
    * Callback method for when a category is selected in the spinner.
    * <p>
    * 
    * @param parent
    * @param view
    * @param position
    * @param id
    * @return description void
    */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = categories[position];
    }

    /** 
    * Callback method for when no item is selected in the spinner.
    * <p>
    * 
    * @param parent
    * @return description void
    */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedCategory = "";
    }

    /** 
    * Filters the database based on the user's input and updates the results list.
    * <p>
    * 
    * @param v
    * @return description void
    */
    public void Filler(View v) {
        tbl.clear();
        db = hlp.getReadableDatabase();

        String selection = "";
        ArrayList<String> selectionArgs = new ArrayList<>();

        String descInput = des2.getText().toString().trim();

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

        Log.i("SQL", "everything is fine, table filreted");
    }

    /** 
    * Creates the options menu for the activity.
    * <p>
    * 
    * @param menu
    * @return description true
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(1 , 1, 100, "Back");
        return super.onCreateOptionsMenu(menu);
    }

    /** 
    * Handles selection of items from the options menu.
    * <p>
    * 
    * @param item
    * @return description true
    */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


}
