package com.anand.mapapp.Activities;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


import android.widget.TextView;
import android.widget.Toast;

import com.anand.mapapp.R;

public class TabsActivity extends TabActivity {

    TabHost mytabs;
    TabHost tabHost;

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

        tabHost = getTabHost();
        final View view1 = LayoutInflater.from(this).inflate(R.layout.tab1, null);
        final TextView tv1=(TextView) view1.findViewById(R.id.tabsText);
        final View line1= view1.findViewById(R.id.underLine);

        Intent intentEmployee = new Intent().setClass(this, EmployeeActivity.class);
        TabSpec tabSpecEmployee = tabHost
                .newTabSpec("Employees")
                .setIndicator(view1)
                .setContent(intentEmployee);

        final View view2 = LayoutInflater.from(this).inflate(R.layout.tab2, null);
        final TextView tv2=(TextView) view2.findViewById(R.id.tabsText);
        final View line2= view2.findViewById(R.id.underLine);
        line2.setVisibility(View.GONE);

        Intent intentPlace = new Intent().setClass(this, PlaceActivity.class);
        TabSpec tabSpecPlace = tabHost
                .newTabSpec("Places")
                .setIndicator(view2)
                .setContent(intentPlace);

        tabHost.addTab(tabSpecEmployee);
        tabHost.addTab(tabSpecPlace);
        tabHost.setCurrentTab(2);

        mytabs = getTabHost();
        mytabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int index = mytabs.getCurrentTab();
                switch (index) {
                    case 0:
                        tv1.setTextColor(Color.parseColor("#EEE516"));
                        line1.setVisibility(View.VISIBLE);
                        tv2.setTextColor(Color.parseColor("#FFFFFF"));
                        line2.setVisibility(View.GONE);
                        break;
                    case 1:
                        tv1.setTextColor(Color.parseColor("#FFFFFF"));
                        line1.setVisibility(View.GONE);
                        tv2.setTextColor(Color.parseColor("#EEE516"));
                        line2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
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