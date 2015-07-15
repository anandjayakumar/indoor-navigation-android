package com.anand.mapapp.Activities;


import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.anand.mapapp.Classes.Timelog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LogActivity extends Activity{
    public int listcounter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        //getSupportActionBar().hide();
        TextView txt = (TextView) findViewById(R.id.textView3);
        DatabaseHandler db = new DatabaseHandler(this);
        List<Timelog> tlog = new ArrayList<Timelog>();
        tlog = db.getAllTimelog();
        listcounter = 0;
        int i = 0,j;
        int a,b,c;
        TextView t1,tv1,tv2,tv3;

        String check = date();

        while (listcounter < tlog.size()) {
            Timelog cn = tlog.get(listcounter);
            if (check.equals(cn.getDate())) {

                LinearLayout ll = (LinearLayout) findViewById(R.id.linear);

                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);

                row.setLayoutParams(lp);
                row.setBackgroundColor(Color.WHITE);



                tv1 = new TextView(this);
                tv2 = new TextView(this);
                tv3 = new TextView(this);


                tv1.setWidth(0);
                tv2.setWidth(0);
                tv3.setWidth(0);

                tv1.setTextSize(20);
                tv2.setTextSize(20);
                tv3.setTextSize(20);

                tv1.setPadding(0,10,0,10);
                tv2.setPadding(0,10,0,10);
                tv3.setPadding(0,10,0,10);

                j=i+1;
                tv1.setText("" + j);
                tv2.setText(cn.getLink());
                tv3.setText(cn.getTime());

                tv1.setTextColor(Color.parseColor("#000000"));
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setTextColor(Color.parseColor("#000000"));

//                tv1.setTextColor(0x000);
//                tv2.setTextColor(0x000);
//                tv3.setTextColor(0x000);

                tv1.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                tv2.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                tv3.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);

                tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                tv3.setGravity(Gravity.CENTER_HORIZONTAL);



                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);

                ll.addView(row);

                TableRow.LayoutParams param = (TableRow.LayoutParams)tv1.getLayoutParams();
                //param.width=0;
                //param.height=30;
                param.span=1;
                param.weight=1;

                //param.gravity= Gravity.CENTER_HORIZONTAL;

                tv1.setLayoutParams(param);
                tv2.setLayoutParams(param);
                tv3.setLayoutParams(param);



                i++;
            }
            listcounter++;

        }


    }

    public String date() {

        GregorianCalendar date = new GregorianCalendar();
        int day, month, year;
        String date1 = "";
        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH) + 1;
        year = date.get(Calendar.YEAR);
        date1 = "" + day + "/" + month + "/" + year;

        return date1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }
}
