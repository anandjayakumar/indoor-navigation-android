package com.anand.mapapp.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.anand.mapapp.Classes.Timelog;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LogActivity extends Activity {
    public int listcounter;
    DatabaseHandler db;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

       db = new DatabaseHandler(this);
        List<Timelog> tlog = db.getAllTimelog();
        listcounter = 0;
        int i = 0,j;
        TextView tv1,tv2,tv3;
        int sp18 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());

        String check = date();
        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        View line = new View(this);
        line.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 3));
        line.setBackgroundColor(Color.parseColor("#A7CFDB"));
        ll.addView(line);

        while (listcounter < tlog.size()) {
            Timelog cn = tlog.get(listcounter);
            if (check.equals(cn.getDate())) {
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
                j=i+1;
                tv1.setText("" + j);
                tv2.setText(cn.getLink());
                tv3.setText(cn.getTime());
                tv1.setTextColor(Color.parseColor("#2C2C2C"));
                tv2.setTextColor(Color.parseColor("#2C2C2C"));
                tv3.setTextColor(Color.parseColor("#2C2C2C"));
                tv1.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                tv2.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                tv3.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                tv1.setGravity(Gravity.CENTER_HORIZONTAL);
                tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                tv3.setGravity(Gravity.CENTER_HORIZONTAL);
                tv1.setPadding(0,sp18,0,sp18);
                tv2.setPadding(0,sp18,0,sp18);
                tv3.setPadding(0,sp18,0,sp18);
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);
                ll.addView(row);
                tv1.setTextAppearance(this, android.R.style.TextAppearance_Material_Medium);
                tv2.setTextAppearance(this, android.R.style.TextAppearance_Material_Medium);
                tv3.setTextAppearance(this, android.R.style.TextAppearance_Material_Medium);
                TableRow.LayoutParams param = (TableRow.LayoutParams)tv1.getLayoutParams();
                param.span=1;
                param.weight=1;
                tv1.setLayoutParams(param);
                tv2.setLayoutParams(param);
                tv3.setLayoutParams(param);
                i++;
                View line2 = new View(this);
                line2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 3));
                line2.setBackgroundColor(Color.parseColor("#DEDEDE"));
                ll.addView(line2);
            }
            listcounter++;
        }
    }


    public void clearLog(View v){
        v.startAnimation(AnimationUtils.loadAnimation(LogActivity.this, R.anim.image_click));
        ScrollView view = (ScrollView)findViewById(R.id.scrollView2);
        view.setVisibility(View.GONE);
        db.deleteLog();

    }

    public void callCancel(View v){
        v.startAnimation(AnimationUtils.loadAnimation(LogActivity.this, R.anim.image_click));
        finish();
    }

    public String date() {
        GregorianCalendar date = new GregorianCalendar();
        int day, month, year;
        String date1;
        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH) + 1;
        year = date.get(Calendar.YEAR);
        date1 = "" + day + "/" + month + "/" + year;
        return date1;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }
}