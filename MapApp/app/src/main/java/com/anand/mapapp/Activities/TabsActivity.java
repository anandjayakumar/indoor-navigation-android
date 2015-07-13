package com.anand.mapapp.Activities;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


import android.widget.Toast;

import com.anand.mapapp.R;

public class TabsActivity extends TabActivity {

    int indx,act;
    String name,desig;
    int x,y,img;
    TabHost mytabs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        Intent i;
        Resources resources = getResources();
        TabHost tabHost = getTabHost();

        Intent intentEmployee = new Intent().setClass(this, EmployeeActivity.class);
        TabSpec tabSpecEmployee = tabHost
                .newTabSpec("Employees")
                .setIndicator("EMPLOYEES")
                .setContent(intentEmployee);


        Intent intentPlace = new Intent().setClass(this, PlaceActivity.class);
        TabSpec tabSpecPlace = tabHost
                .newTabSpec("Places")
                .setIndicator("PLACES")
                .setContent(intentPlace);


        // add all tabs
        tabHost.addTab(tabSpecEmployee);
        tabHost.addTab(tabSpecPlace);

        tabHost.setCurrentTab(2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings",
                        Toast.LENGTH_LONG).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}