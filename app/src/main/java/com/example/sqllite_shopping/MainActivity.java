package com.example.sqllite_shopping;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/** 
* @author        benjamin rogachevsky 
* @version       1.0
* @since           4/11/26
* Main Activity class for the shopping list application, handles item input and navigation.
*/
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText des;
    private EditText mon;
    private Spinner cat;
    private EditText date;
    private Button add;

    private int id = 0;
    private String Sdes;
    private String Smon;
    private int Scat;
    private String Sdat;
    private final String[] categories = {"select category:", "food", "clothes", "electronics", "drugs", "other"};

    private SQLiteDatabase db;
    private HelperDB hlp;


    /** 
    * link xml objects to java objects
    * <p>
    * 
    */
    public void merrage() {
        des = findViewById(R.id.des2);
        mon = findViewById(R.id.mon);
        cat = findViewById(R.id.cat);
        date = findViewById(R.id.date);
        add = findViewById(R.id.add);
    }

    /** 
    * onCreate method to initialize the activity
    * <p>
    * 
    * @param savedInstanceState
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        merrage();


        cat.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        cat.setAdapter(adapter);
    }


    /** 
    * listener for the spinner
    * <p>
    * 
    * @param parent
    * @param view
    * @param pos
    * @param id
    */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Scat = pos;
        Log.i("spinner", categories[pos] + " selected");
    }


    /** 
    * listener for the spinner when nothing is selected
    * <p>
    * 
    * @param parent
    */
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("spinner", "nothing selected");
    }

    /** 
    * method to push data to the database
    * <p>
    * 
    * @param view
    */
    public void Push(View view) {
        Sdes = des.getText().toString();
        Smon = mon.getText().toString();
        Sdat = date.getText().toString();
        id ++;
        if (Sdes.equals("") || Smon.equals("") || Sdat.equals("") || Scat == 0 || CheckDate(Sdat)){
            Log.i("SQL", "user is stupid, in the put is all wrong");
            Log.i("SQL", Sdes);
            Log.i("SQL", Smon);
            Log.i("SQL", Sdat);
            Log.i("SQL", String.valueOf(Scat));


        Log.i("SQL", "everything is fine");


            AlertDialog.Builder err = new AlertDialog.Builder(this);

            err.setTitle("Oops, something is wrong");
            err.setMessage("Please check your input");
            err.setPositiveButton("Ok", (dialogInterface, i) -> {
                dialogInterface.cancel();
            });
            err.show();

            return;
            }


            hlp = new HelperDB(this);
            db = hlp.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Percace.DESCRIPTION, Sdes);
            values.put(Percace.AMOUNT, Smon);
            values.put(Percace.CATEGORY, categories[Scat]);
            values.put(Percace.DATE, Sdat);
            values.put(Percace.KEY_ID, id);

            db.insert(Percace.TABLE_NAME, null, values);
            db.close();
            Log.i("SQL", "everything is fine, table created");




    }


    /** 
    * method to create the options menu
    * <p>
    * 
    * @param menu
    * @return description true
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(1 , 1, 100, "Show");
        menu.add(1, 1, 200, "Filter");
        menu.add(1, 1, 300, "Credit");
        return super.onCreateOptionsMenu(menu);
    }

    /** 
    * method to handle menu item selection
    * <p>
    * 
    * @param item
    * @return description true
    */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        String tit = item.getTitle().toString();

        Intent shaw;

        if (tit.equals("Show")) {
            shaw = new Intent(this, Show.class);
        } else if (tit.equals("Filter")) {
            shaw = new Intent(this, Filter.class);
        } else {
            shaw = new Intent(this, Credits.class);
        }
        startActivity(shaw);

        return super.onOptionsItemSelected(item);
    }


    /** 
    * method to check if the date is valid
    * <p>
    * 
    * @param date
    * @return description boolean
    */
    public static boolean CheckDate(@NonNull String date){
        String[] split = date.split("/");

        if ((Integer.parseInt(split[1]) > 12) || (Integer.parseInt(split[1]) == 0)){
            return true;
        }

        if (split.length == 3) {
            int day = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);

            if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
                if (day > 30) {
                    return true;
                }
            } else if (month == 2) {
                if (year % 4 == 0) {
                    if (day > 29) {
                        return true;
                    }
                } else {
                    if (day > 28) {
                        return true;
                    }
                }
            } else {
                if (day > 31) {
                    return true;
                }
            }
        } else return true;

        return false;
    }



}
