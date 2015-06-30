package com.anand.mapapp;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LogActivity extends Activity {
    public int listcounter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
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
              /*  a=3*i+1;
                b=3*i+2;
                c=3*i+3;
                String buttonID="txtView"+a;
                int resID = getResources().getIdentifier(buttonID, "id", "com.anand.mapapp");
                t1=(TextView)findViewById(resID);
                t1.setText(""+i);
                buttonID="txtView"+b;
                resID = getResources().getIdentifier(buttonID, "id", "com.anand.mapapp");
                t1=(TextView)findViewById(resID);
                t1.setText(cn.getLink());
                buttonID="txtView"+c;
                resID = getResources().getIdentifier(buttonID, "id","com.anand.mapapp");
                t1=(TextView)findViewById(resID);
                t1.setText(cn.getTime());*/

               LinearLayout ll = (LinearLayout) findViewById(R.id.linear);

                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);

                row.setLayoutParams(lp);




                tv1 = new TextView(this);
                tv2 = new TextView(this);
                tv3 = new TextView(this);


                tv1.setWidth(0);
                tv2.setWidth(0);
                tv3.setWidth(0);

                tv1.setTextSize(20);
                tv2.setTextSize(20);
                tv3.setTextSize(20);


               /* tv1.setPadding(130,0,0,0);
                tv2.setPadding(30,0,0,0);*/

                j=i+1;
                tv1.setText("" + j);
                tv2.setText(cn.getLink());
                tv3.setText(cn.getTime());

                tv1.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                tv2.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                tv3.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);

                tv1.setGravity(Gravity.CENTER_HORIZONTAL);



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
