package com.example.sqllite_shopping;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText des;
    EditText mon;
    Spinner cat;
    EditText date;
    Button add;

    String Sdes;
    String Smon;
    int Scat;
    String Sdat;
    String[] categories = {"select category", "food", "clothes", "electronics", "drugs", "other"};

    public void merrage() {
        des = findViewById(R.id.des);
        mon = findViewById(R.id.mon);
        cat = findViewById(R.id.cat);
        date = findViewById(R.id.date);
        add = findViewById(R.id.add);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        merrage();


        cat.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories){
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // disable first item
            }
        };
        cat.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Scat = pos;
        Log.i("spinner", categories[pos] + " selected");
    }


    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("spinner", "nothing selected");
    }

    public void Push(View view) {
        Sdes = des.getText().toString();
        Smon = mon.getText().toString();
        Sdat = date.getText().toString();
        if (Sdes.equals("") || Smon.equals("") || Sdat.equals("") || Scat == 0 || CheckDate(Sdat)){
            Log.i("error", "user is stupid");
            Log.i("error", Sdes);
            Log.i("error", Smon);
            Log.i("error", Sdat);
            Log.i("error", String.valueOf(Scat));

            // allert going here
        }


    }


    public static boolean CheckDate(String date){
        return true;
    }
}