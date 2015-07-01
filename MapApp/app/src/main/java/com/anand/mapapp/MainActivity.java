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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    String namep;
    int x,y;  int act;
    String designation;
    int id;

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
    float xk[] = new float[100];
    float yk[] = new float[100];
    int mkid=0,pos;

    TouchImageView img;
    private Matrix mapMatrix = new Matrix();
    private Matrix pointerMatrix = new Matrix();
    private Matrix markerMatrix = new Matrix();
    private Bitmap map;
    Bitmap mapp,pointp,pointp2,point,marker;
    BitmapFactory.Options options;

    AssetManager asset;
    DatabaseHandler db;
    SharedPreferences sp;
    DBQueries dbQueries;


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
        marker = assetBitmap(asset,"marker.png");

        img.setImageBitmap(mapp);
        img.setMaxZoom(4f);
        img.setMinZoom(1.8f);
        img.setZoom(1.8f);

        width = mapp.getWidth();
        height = mapp.getHeight();

        mk[0]=0;

        //aaaa

        db = new DatabaseHandler(this);
        dbQueries = new DBQueries(getApplicationContext());

       sp = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
       String restoredText = sp.getString("flag", null);
       if (restoredText == null) {

            getApplicationContext().deleteDatabase("MAIN_DB");
            addEmployee();
            addPlace();
            addQR();

            SharedPreferences.Editor editor = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE).edit();
            editor.putString("flag", "set");
            editor.commit();
        }


        options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable= true;

        posX = (float)0.5;
        posY = (float)0.5;


    }

    @Override
    protected void onNewIntent(Intent in) {
        super.onNewIntent(in);
        setIntent(in);
    }




    public void addEmployee() {

        dbQueries.insertEmployees(new DBQueries.EmployeeinsertionCompletion() {
            @Override
            public void employeeinsertionCompleted() {
                Log.i("dbb", "inserted");
                Employee c = db.getEmployeeId(2);
                Toast.makeText(getApplicationContext(), "" + c.getX() + " " + c.getY(), Toast.LENGTH_SHORT).show();

                pullDb();

            }
        });

    }

    public void addPlace() {

        dbQueries.insertPlaces(new DBQueries.PlaceinsertionCompletion() {
            @Override
            public void placeinsertionCompleted() {
                Log.i("dbb", "inserted");

                //pullDb();

            }
        });

    }

    public void addQR() {

        dbQueries.insertQR(new DBQueries.QRinsertionCompletion() {
            @Override
            public void qrinsertionCompleted() {
                Log.i("dbb", "inserted");

                // pullDb();

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

    public void callLog(View v) {
        Intent intent=new Intent(MainActivity.this,LogActivity.class);
        startActivity(intent);
    }

    public void callSearch(View v) {
        Intent intent=new Intent(MainActivity.this,TabsActivity.class);
        startActivity(intent);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        int listcounter=0;
        message=data.getStringExtra("MESSAGE");


        if(requestCode==2 && !message.equals("null"))
        {

            String link=data.getStringExtra("MESSAGE");
            QRcode qr = db.getQRcodeLink(link);
            posX=(float)qr.getX()/10;
            posY=(float)qr.getY()/10;
            List<Timelog> tlogg;
            tlogg = db.getAllTimelog();
            int size = tlogg.size();
            Timelog tlog = new Timelog(size+1,message,date(),time());
            db.insertLog(tlog);

        }

        /*else if(requestCode == 3) {

            mkid = Integer.parseInt(data.getStringExtra("mkid"));
            int pos=mk[0]+1;
            mk[0]++;
            mk[pos]=mkid;

        }*/

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

        String sec,min,hr;
        String time1="";
        long time = System.currentTimeMillis();
        double hours = ((time/3600000)%24)+6.5;
        int hrs = (int)hours;

        if(hrs<10)
            hr="0"+hrs;
        else
            hr=""+hrs;

        if(date.get(Calendar.SECOND)<10)
            sec="0"+date.get(Calendar.SECOND);
        else
            sec=""+date.get(Calendar.SECOND);

        if(date.get(Calendar.MINUTE)<10)
            min="0"+date.get(Calendar.MINUTE);
        else
            min=""+date.get(Calendar.MINUTE);

        time1=""+hr+":"+min+":"+sec;


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

        Intent in=getIntent();
        act=in.getIntExtra("act_val", act);
        id=in.getIntExtra("id",id);
        x=in.getIntExtra("x_val",x);
        y=in.getIntExtra("y_val", y);


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
        if(act==1)
        {
            pos=mk[0]+1;
            mkid = id;
            xk[pos]=(float)x/10;
            yk[pos]=(float)y/10;
            mk[pos]=mkid;
            mk[0]++;
        }

        final Handler handler = new Handler();
        Runnable runnable = new Runnable()
        {

            public void run()
            {
                update();
                handler.postDelayed(this, 100);
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


            Log.i("timetaken1",""+time());
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
            for(int j=1;j<=mk[0];j++){
                markerMatrix.reset();
                float xw = xk[j]*width;
                float yw = yk[j]*height;
                markerMatrix.setTranslate(xw,yw);
                markerMatrix.postRotate(degrees1, pinX, pinY);
                canvas.drawBitmap(marker,markerMatrix,null);
            }
            canvas.drawBitmap(point,pinX ,pinY, null);

        }

        else if(state==2) {

            pointerMatrix.setTranslate(pinX, pinY);
            pointerMatrix.postRotate(degrees2, imgX, imgY);
            canvas.drawBitmap(mapp, 0,0, null);
            for(int j=1;j<=mk[0];j++){
                float xw = xk[j]*width;
                float yw = yk[j]*height;
                canvas.drawBitmap(marker,xw,yw,null);
            }
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
            mk[0]=0;
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

