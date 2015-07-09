package com.app2.aar.qr_code;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.app2.aar.qr_code.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LogActivity extends Activity {
    public int listcounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        TextView txt=(TextView)findViewById(R.id.textView3);
        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();
        listcounter=0; int i=1;
        String check=date();


        while(listcounter<contacts.size())
        {
            Contact cn = contacts.get(listcounter);
            if(check.equals(cn.getX())) {
                txt.append("\n" + "      " + i + "          " + cn.getLink() + "        " + cn.getY());
                i++;
            }
            listcounter++;

        }



    }

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
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
