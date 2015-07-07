package com.anand.mapapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

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
        //TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


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
        /*mytabs = getTabHost();
        mytabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                int index = mytabs.getCurrentTab();
                switch (index) {
                    case 0:
                        Intent i = new Intent().setClass(TabsActivity.this, MainActivity.class);

                        break;
                    case 1:
                        Intent i = new Intent().setClass(TabsActivity.this, MainActivity.class);
                        startActivityForResult(i,3);
                        break;
                }
            }
        });*/
    }



    /*private void changetabs(TabWidget tabWidget) {
        // Change background
        for(int i=0; i < tabWidget.getChildCount(); i++)
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_selector);
    }*/
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