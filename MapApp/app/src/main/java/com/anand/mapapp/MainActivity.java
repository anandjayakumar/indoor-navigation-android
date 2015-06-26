package com.anand.mapapp;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    public SensorManager sensorService;
    public Sensor compass;

    int state=1;
    int first=0;
    double pinYDouble;
    float imgX,imgY,posX,posY,pinX,pinY;
    float degrees1=0,degrees2=0;
    int width,height;

    String message = null;

    int mk[] = new int[100];
    int mkid=0;

    TouchImageView img;
    private Matrix mapMatrix = new Matrix();
    private Matrix pointerMatrix = new Matrix();
    private Bitmap map;
    Bitmap mapp,pointp,pointp2,point;
    BitmapFactory.Options options;

    AssetManager asset;
    DatabaseHandler db;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorService = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        compass = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorService.registerListener(compassListener, compass, SensorManager.SENSOR_DELAY_FASTEST);

        img = (TouchImageView) findViewById(R.id.touchImg);
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache(true);

        asset = getAssets();
        mapp = assetBitmap(asset, "map2.png");
        pointp = assetBitmap(asset, "point.png");
        pointp2 = assetBitmap(asset,"point2.png");

        img.setImageBitmap(mapp);
        img.setMaxZoom(4f);
        img.setMinZoom(1.8f);
        img.setZoom(1.8f);

        width = mapp.getWidth();
        height = mapp.getHeight();

        mk[0]=0;

        //aaaa

        db = new DatabaseHandler(this);

       sp = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
       String restoredText = sp.getString("flag", null);
       if (restoredText == null) {

            getApplicationContext().deleteDatabase("MAIN_DB");
            addEmployee();

            SharedPreferences.Editor editor = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE).edit();
            editor.putString("flag", "set");
            editor.commit();
        }


        options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable= true;

        posX = (float)0.5;
        posY = (float)0.5;

        //Log.d("test", ""+c.getX()+" "+c.getY());

        int m=0;

    }

    public void addEmployee() {
        DBQueries dbQueries = new DBQueries(getApplicationContext());
        dbQueries.insertEmployees(new DBQueries.EmployeeinsertionCompletion() {
            @Override
            public void employeeinsertionCompleted() {
                Log.i("dbb------","inserted");
//                Employee c =db.getEmployeeId(2);
              //  pullDb();

            }
        });

    }

    public Bitmap assetBitmap(AssetManager asset, String file) {
        InputStream open = null;
        Bitmap map = null;
        try {
            open = asset.open(file);
            map = BitmapFactory.decodeStream(open,null,options);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;

    }

    public void callQR(View v) {
        if(first==0) {
            state = 1;
            first=1;
        }
        Intent intent=new Intent(MainActivity.this,DecoderActivity.class);
        startActivityForResult(intent, 2);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        int listcounter=0;
        message=data.getStringExtra("MESSAGE");


        if(requestCode==2 && !message.equals("null"))
        {

           /* int link=data.getStringExtra("link");
            QRcode qr = db.getQRcodeId(link);
            posX=qr.getX();
            posY=qr.getY();*/


        }

        else if(requestCode == 3) {

            mkid = Integer.parseInt(data.getStringExtra("mkid"));
            int pos=mk[0]+1;
            mk[0]++;
            mk[pos]=mkid;

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

    public SensorEventListener compassListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            degrees1 = -2*Math.round(event.values[0]/2);
            degrees2 = 2*Math.round(event.values[0]/2);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();



       /* timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        update();
                    }
                });


            }
        }, 2000, 200);*/

        final Handler handler = new Handler();
        Runnable runnable = new Runnable()
        {

            public void run()
            {
                update();
                handler.postDelayed(this, 150);
            }

        };
        handler.postDelayed(runnable, 2000);

    }



    public void update() {

        if(state!=0){

            if(img.getCurrentZoom()>3)
                point = pointp2;
            else
                point = pointp;

           mapPlot();

        }

    }


    public void mapPlot() {

        if(map!=null){
            map.recycle();
        }

        map = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(map);
        mapMatrix.reset();
        pointerMatrix.reset();

        imgX = posX*width;
        imgY = posY*height;
        pinX = imgX - point.getWidth()/2;
        pinYDouble = imgY - point.getHeight()/1.56;
        pinY = (float)pinYDouble;

        if(state==1) {

            mapMatrix.setRotate(degrees1, imgX, imgY);
            canvas.drawBitmap(mapp, mapMatrix, null);
            canvas.drawBitmap(point,pinX ,pinY, null);

        }

        else if(state==2) {

            pointerMatrix.setTranslate(pinX, pinY);
            pointerMatrix.postRotate(degrees2, imgX, imgY);
            canvas.drawBitmap(mapp, 0,0, null);
            canvas.drawBitmap(point, pointerMatrix, null);

        }

        //mk[1] to mk[mk[0]]




        img.setImageBitmap(map);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.lock) {
            if((state==0)||(state==2)) {
                state=1;
            }

                state=2;
        }

        else if (id == R.id.clear) {
            state=1;
        }

        return super.onOptionsItemSelected(item);
    }


    private void pullDb() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/MAIN_DB";
                String backupDBPath = "maindb.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("pullDb", e.getMessage());
        }
        }

}

