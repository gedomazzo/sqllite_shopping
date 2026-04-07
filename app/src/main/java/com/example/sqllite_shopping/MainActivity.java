package com.example.sqllite_shopping;

import android.app.AlertDialog;
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
    String[] categories = {"select category:", "food", "clothes", "electronics", "drugs", "other"};

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
                return position != 0;
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


            AlertDialog.Builder err = new AlertDialog.Builder(this);

            err.setTitle("Oops, something is wrong");
            err.setMessage("Please check your input");
            err.setPositiveButton("Ok", (dialogInterface, i) -> {
                dialogInterface.cancel();
            });
            err.show();

            return;
            }

        




    }


    public static boolean CheckDate(String date){

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