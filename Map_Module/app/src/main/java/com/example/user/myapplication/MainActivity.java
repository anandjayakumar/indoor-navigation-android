package com.example.user.myapplication;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    int i=0;
    Timer timer = new Timer();
    TouchImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TouchImageView img = (TouchImageView) findViewById(R.id.img);
        img.setImageResource(R.drawable.a1);
        img.setMaxZoom(4f);

    }
    protected void onResume() {
        super.onResume();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        update();
                    }
                });

            }
        }, 2000, 2000);

    }
     public void update() {

        img = (TouchImageView) findViewById(R.id.img);
        if (i % 2 == 0) {
            img.setImageResource(R.drawable.a1);
        } else {
            img.setImageResource(R.drawable.a2);
        }
        i++;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
