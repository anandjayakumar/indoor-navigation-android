package com.anand.mapapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anand.mapapp.Classes.CustomAdapter;
import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Label;
import com.anand.mapapp.Classes.Place;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.GrowEffect;

import java.util.ArrayList;
import java.util.List;


public class FavouriteActivity extends Activity {

    private static final int ITEM_TYPE_EMPLOYEE=1;
    private static final int ITEM_TYPE_PLACE=2;
    int windowW, windowH, winW, winH, imgW, imgH, padding;
    CustomAdapter adapter;
    DatabaseHandler handler=new DatabaseHandler(this);
    List<Employee> favEmployees = new ArrayList<Employee>();
    List<Place> favPlaces= new ArrayList<Place>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        windowW = getWindowManager().getDefaultDisplay().getWidth();
        windowH = getWindowManager().getDefaultDisplay().getHeight();
        winW = windowW / 10;
        winH = windowH / 10;
        imgW = winW * 2;
        imgH = winH * 2;
        padding = winW;

        JazzyListView listEmployee = (JazzyListView) findViewById(R.id.listEmployee);

        favEmployees=handler.getFavEmployees();
        listEmployee.setTransitionEffect(new GrowEffect());
        adapter = new CustomAdapter(this,ITEM_TYPE_EMPLOYEE,favEmployees,null,imgW,imgH);
        listEmployee.setAdapter(adapter);

        favPlaces=handler.getFavPlaces();
        listEmployee.setTransitionEffect(new GrowEffect());
        adapter = new CustomAdapter(this,ITEM_TYPE_PLACE,null,favPlaces,imgW,imgH);
        listEmployee.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
