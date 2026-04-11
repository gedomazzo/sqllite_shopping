package com.example.sqllite_shopping;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/** 
* @author        benjamin rogachevsky 
* @version       1.0
* @since           4/11/26
* Activity class that displays the credits information for the application.
*/
public class Credits extends AppCompatActivity {

    /** 
    * Initializes the activity and sets the content view.
    * <p>
    * 
    * @param savedInstanceState 
    * @return description void
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    /** 
    * Creates the options menu for the credits activity.
    * <p>
    * 
    * @param menu 
    * @return description true if the menu is displayed
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
    * @return description boolean
    */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
