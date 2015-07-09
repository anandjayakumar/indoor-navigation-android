package com.app2.aar.qr_code;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app2.aar.qr_code.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends Activity {
    public int listcounter;
    DatabaseHandler db = new DatabaseHandler(this);

    public final static String MESSAGE = "com.app1.aaru.myapp1.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*db.addContact(new Contact("http://space.qburst.com/location/ag5zfnFidXJzdC1zcGFjZXIVCxIITG9jYXRpb24YgICAwP2ysAoM/view", date(), time()));
        db.addContact(new Contact("http://space.qburst.com/location/ag5zfnFidXJzdC1zcGFjZXIVCxIITG9jYXRpb24YgICAwP2k4QkM/view", "0.5" , "0.6"));
       db.addContact(new Contact("http://space.qburst.com/location/ag5zfnFidXJzdC1zcGFjZXIVCxIITG9jYXRpb24YgICAwL2_vwoM/view", "0.6", "0.7"));
        db.addContact(new Contact("http://space.qburst.com/location/ag5zfnFidXJzdC1zcGFjZXIVCxIITG9jYXRpb24YgICAwN2UuQoM/view", "0.7" , "0.8"));
    */}

    public String date() {

        GregorianCalendar date = new GregorianCalendar();
        int day, month, year;
        String date1="";


        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH)+1;
        year = date.get(Calendar.YEAR);
        date1=""+day+"/"+month+"/"+year;

        return date1;
    }

    public String time() {

        GregorianCalendar date = new GregorianCalendar();
        int second, minute, hour;
        String time1="";


        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);
        time1=""+hour+":"+minute+":"+second;

        return time1;
    }



    public void getVal(View v){
        Intent intent=new Intent(MainActivity.this,DecoderActivity.class);
        startActivityForResult(intent, 2);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        TextView txt_v=(TextView)findViewById(R.id.textView2);
        txt_v.setTextIsSelectable(true);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String message=data.getStringExtra("MESSAGE");

            db.addContact(new Contact(message, date(), time()));



        }
    }

    public void log(View v)
    {
        Intent intent3=new Intent(MainActivity.this, LogActivity.class);
        startActivity(intent3);

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
