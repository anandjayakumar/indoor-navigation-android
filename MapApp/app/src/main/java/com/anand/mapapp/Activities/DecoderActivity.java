package com.anand.mapapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.KeyEvent;

import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DecoderActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private QRCodeReaderView mydecoderview;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

    }

    public final static String MESSAGE = "com.app1.aaru.myapp1.MESSAGE";
    public static String data;

    @Override
    public void onQRCodeRead(String text, PointF[] points)
    {
        data=text;
       /* List<Timelog> tlogg;
        tlogg = db.getAllTimelog();
        int size = tlogg.size();
        Timelog tlog = new Timelog(size+1,data,date(),time());*/

        Intent intent=new Intent();
        intent.putExtra("MESSAGE",data);
        setResult(2,intent);
        finish();

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




    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent=new Intent();
            intent.putExtra("MESSAGE","null");
            setResult(2,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}