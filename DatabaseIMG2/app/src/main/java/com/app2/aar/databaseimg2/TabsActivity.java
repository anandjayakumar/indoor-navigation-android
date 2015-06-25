package com.app2.aar.databaseimg2;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;


import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class TabsActivity extends TabActivity {

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

        Resources ressources = getResources();
        TabHost tabHost = getTabHost();
        //TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        Intent intentEmployee = new Intent().setClass(this, MainActivity.class);
        TabSpec tabSpecEmployee = tabHost
                .newTabSpec("Employees")
                .setIndicator("", ressources.getDrawable(R.drawable.emp_layout))
                .setContent(intentEmployee);


        Intent intentPlace = new Intent().setClass(this, PlaceActivity.class);
        TabSpec tabSpecPlace = tabHost
                .newTabSpec("Places")
                .setIndicator("", ressources.getDrawable(R.drawable.plc_layout))
                .setContent(intentPlace);


        // add all tabs
        tabHost.addTab(tabSpecEmployee);
        tabHost.addTab(tabSpecPlace);

        //set Windows tab as default (zero based)
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
        }}
}