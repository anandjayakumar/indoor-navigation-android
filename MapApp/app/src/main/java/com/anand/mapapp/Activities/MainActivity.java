package com.anand.mapapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

import android.graphics.Paint;
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

import com.anand.mapapp.Database.DBQueries;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.Classes.QRcode;
import com.anand.mapapp.R;
import com.anand.mapapp.Classes.Timelog;
import com.anand.mapapp.Libraries.TouchImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {


    static final float circleHalf = 17;
    static final float userHalf = 25;
    static final int width = 2222;
    static final int height = 2400;
    static final int mapLeft = 543;
    static final int mapTop = 450;
    static final int pointBigX = 32;
    static final int pointSmallX = 16;
    static final int pointBigY = 41;
    static final int pointSmallY = 20;
    static final float midlevel = 3;

    int Ex,Ey;  int act,Px,Py;
    int Eid,Pid;
    int state=1;
    int first=0;

    public SensorManager sensorService;
    public Sensor compass;

    float imgX,imgY,posX,posY,pinX,pinY;
    float mkh,phX,phY;
    float degrees1=0,degrees2=0;

    Paint mPaint;
    String message = null;

    int Emk[] = new int[100];
    float Exk[] = new float[100];
    float Eyk[] = new float[100];
    int Emkid=0,Epos;

    int Pmk[] = new int[100];
    float Pxk[] = new float[100];
    float Pyk[] = new float[100];
    int Pmkid=0,Ppos;

    TouchImageView img;
    private Matrix mapMatrix = new Matrix();
    private Matrix pointerMatrix = new Matrix();
    private Matrix markerMatrix = new Matrix();

    Bitmap mapp,pointp,pointp2,point,marker,map,user,circle;
    BitmapFactory.Options options;

    DatabaseHandler db;
    SharedPreferences sp;
    DBQueries dbQueries;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawable(null);
      
        sensorService = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        compass = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorService.registerListener(compassListener, compass, SensorManager.SENSOR_DELAY_NORMAL);

        img = (TouchImageView) findViewById(R.id.touchImg);
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache(true);
        AssetManager asset = getAssets();

        mapp = assetBitmap(asset, "mainMap.png");
        pointp = assetBitmap(asset, "point.png");
        pointp2 = assetBitmap(asset,"point2.png");
        circle = assetBitmap(asset,"marker.png");
        user = assetBitmap(asset, "user.png");

       // img.setImageBitmap(mapp);
        img.setMaxZoom(5.5f);
        img.setMinZoom(2f);
        img.setZoom(2f);

        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setFilterBitmap(true);

        Emk[0]=0;
        Pmk[0]=0;

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
        options.inScaled = false;
        options.inDither = false;

        posX = (float)0.5;
        posY = (float)0.5;

        handler = new Handler();

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
            }
        });
    }

    public void addPlace() {
        dbQueries.insertPlaces(new DBQueries.PlaceinsertionCompletion() {
            @Override
            public void placeinsertionCompleted() {
            }
        });
    }

    public void addQR() {
        dbQueries.insertQR(new DBQueries.QRinsertionCompletion() {
            @Override
            public void qrinsertionCompleted() {
            }
        });
    }

    public Bitmap assetBitmap(AssetManager asset, String file) {
        InputStream open = null;
        Bitmap bmp = null;
        try {
            open = asset.open(file);
            bmp = BitmapFactory.decodeStream(open,null,options);

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
        return bmp;

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        message=data.getStringExtra("MESSAGE");
        if(requestCode==2 && !message.equals("null"))
        {
            String link=data.getStringExtra("MESSAGE");
            QRcode qr = db.getQRcodeLink(link);
            posX=(float)qr.getX()/10;
            posY=(float)qr.getY()/10;
            int size = db.getLogSize();
            Timelog tlog = new Timelog(size+1,message,date(),time());
            db.insertLog(tlog);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onResume() {
        super.onResume();


        Intent in=getIntent();
        act=in.getIntExtra("act_val", act);
        Eid=in.getIntExtra("id",Eid);
        Ex=in.getIntExtra("x_val",Ex);
        Ey=in.getIntExtra("y_val", Ey);


        if(act==1)
        {
            Epos=Emk[0]+1;
            Emkid = Eid;
            Exk[Epos]=(float)Ex/10;
            Eyk[Epos]=(float)Ey/10;
            Emk[Epos]=Emkid;
            Emk[0]++;
        }

        if(act==2){
            Ppos=Pmk[0]+1;
            Pmkid = Pid;
            Pxk[Ppos]=(float)Px/10;
            Pyk[Ppos]=(float)Py/10;
            Pmk[Ppos]=Pmkid;
            Pmk[0]++;
        }


        Runnable runnable = new Runnable()
        {

            public void run()
            {
                update();
                handler.postDelayed(this,40);
            }

        };
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
    }



    public void update() {

        if(state!=0){

            if(img.getCurrentZoom() > midlevel) {
                point = pointp2;
                marker = user;
                mkh = userHalf;
                phX = pointSmallX;
                phY = pointSmallY;

            }
            else {
                point = pointp;
                marker = circle;
                mkh = circleHalf;
                phX = pointBigX;
                phY = pointBigY;

            }
           mapPlot();
        }
    }


    public void mapPlot() {

        if(map!=null){
            map.recycle();
        }

        map = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(map);
        canvas.drawColor(Color.rgb(237,237,237));
        mapMatrix.reset();
        pointerMatrix.reset();

        imgX = posX*width;
        imgY = posY*height;
        pinX = imgX - phX;
        pinY = imgY - phY;

        if(state==1) {

            mapMatrix.setTranslate(mapLeft,mapTop);
            mapMatrix.postRotate(degrees1, imgX, imgY);
            canvas.drawBitmap(mapp,mapMatrix, mPaint);

            for(int j=1;j<=Emk[0];j++){
                markerMatrix.reset();
                float xw = Exk[j]*width - mkh;
                float yw = Eyk[j]*height - mkh;
                markerMatrix.setTranslate(xw,yw);
                markerMatrix.postRotate(degrees1, imgX, imgY);
                canvas.drawBitmap(marker, markerMatrix, mPaint);

            }

            for(int j=1;j<=Pmk[0];j++){
                markerMatrix.reset();
                float xw = Pxk[j]*width;
                float yw = Pyk[j]*height;
                markerMatrix.setTranslate(xw,yw);
                markerMatrix.postRotate(degrees1, imgX, imgY);
                canvas.drawBitmap(marker,markerMatrix,mPaint);

            }

            canvas.drawBitmap(point,pinX ,pinY, mPaint);


        }

        else if(state==2) {

            pointerMatrix.setTranslate(pinX, pinY);
            pointerMatrix.postRotate(degrees2, imgX, imgY);
            canvas.drawBitmap(mapp,mapLeft,mapTop, mPaint);

            for(int j=1;j<=Emk[0];j++){
                float xw = Exk[j]*width - mkh;
                float yw = Eyk[j]*height - mkh;
                canvas.drawBitmap(marker,xw,yw,mPaint);
            }

            for(int j=1;j<=Pmk[0];j++){
                float xw = Pxk[j]*width;
                float yw = Pyk[j]*height;
                canvas.drawBitmap(marker,xw,yw,mPaint);
            }

            canvas.drawBitmap(point, pointerMatrix,mPaint);


        }

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
            else {
                state = 2;
            }
        }

        else if (id == R.id.clear) {
            state=1;
            Emk[0]=0;
            Pmk[0]=0;
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
        if(hrs<10) {
            hr = "0" + hrs;
        }
        else {
            hr = "" + hrs;
        }
        if(date.get(Calendar.SECOND)<10) {
            sec = "0" + date.get(Calendar.SECOND);
        }
        else {
            sec = "" + date.get(Calendar.SECOND);
        }
        if(date.get(Calendar.MINUTE)<10) {
            min = "0" + date.get(Calendar.MINUTE);
        }
        else {
            min = "" + date.get(Calendar.MINUTE);
        }
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

}

