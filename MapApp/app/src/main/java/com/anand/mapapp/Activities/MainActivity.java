package com.anand.mapapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Marker;
import com.anand.mapapp.Classes.Place;
import com.anand.mapapp.Classes.QRcode;
import com.anand.mapapp.Classes.Timelog;
import com.anand.mapapp.Database.DBQueries;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.Libraries.TouchImageView;
import com.anand.mapapp.R;

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


public class MainActivity extends Activity {


    static final float midLevel = 3.6f;
    static final float maxZoom = 5f;
    static final float minZoom = 1.5f;
    static final float startZoom = 2.5f;

    int scaleLevel;
    float scaleLevelf;
    String scalePath;

    float circleHalf;
    float width;
    float height;
    float mapWidth;
    float mapHeight;
    float mapLeft;
    float mapTop;
    int pointBigX;
    int pointSmallX;
    int pointBigY;
    int pointSmallY;

    int WiW, WiH, windowW, windowH, winW, winH, imgW, imgH;
    Point p;

    int skipIntent = 0;
    int Ex, Ey;
    int act, Eid;
    int state = 0;

    RectF box;

    public SensorManager sensorService;
    public Sensor compass;

    float imgX, imgY, posX, posY, pinX, pinY;
    float mkh, phX, phY;
    float degrees1 = 0, degrees2 = 0;

    Paint mPaint;
    String message = null;

    TouchImageView img;
    private Matrix mapMatrix = new Matrix();
    private Matrix pointerMatrix = new Matrix();
    private Matrix markerMatrix = new Matrix();
    private Matrix transform = new Matrix();

    Bitmap mapp, pointp, pointp2, point, marker, map, circle;
    BitmapFactory.Options options;

    DatabaseHandler db;
    SharedPreferences sp;
    DBQueries dbQueries;

    Handler handler;
    Runnable runnable;

    Marker mk;
    List<Marker> mkList;

    PopupWindow popup;
    int POP_PRESENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleLevelf = getResources().getDisplayMetrics().density;
        if(scaleLevelf != 4f) {
            scaleLevelf += 1f;
        }
        scaleLevel = (int)scaleLevelf;

        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        compass = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorService.registerListener(compassListener, compass, SensorManager.SENSOR_DELAY_GAME);

        img = (TouchImageView) findViewById(R.id.touchImg);
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache(true);

        AssetManager asset = getAssets();
        scalePath = "" + scaleLevel;
        mapp = assetBitmap(asset, scalePath + "/map.png");
        pointp = assetBitmap(asset, scalePath + "/point.png");
        pointp2 = assetBitmap(asset, scalePath + "/point2.png");
        circle = assetBitmap(asset, scalePath + "/marker.png");

        updateScaleValues();

        img.setMaxZoom(maxZoom);
        img.setMinZoom(minZoom);
        img.setZoom(startZoom);

        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setFilterBitmap(true);

        mkList = new ArrayList<Marker>();

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
        options.inInputShareable = true;
        options.inScaled = false;
        options.inDither = false;

        posX = 250f;
        posY = 414f;

        imgX = width/2;
        imgY = height/2;
        updateLeftTop();

        handler = new Handler();
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PointF point = img.transformCoordTouchToBitmap(event.getX(), event.getY(), true);
                if (state != 0) {
                    for (Marker mk : mkList) {
                        float x, y;
                        x = mk.getMapx() - mkh;
                        y = mk.getMapy() - mkh;
                        box = new RectF(x, y, x + 2 * mkh+5, y + 2 * mkh+5);
                        if (box.contains(point.x, point.y)) {
                            showPopup(MainActivity.this, mk.getCat(), mk.getId());
                        }
                    }
                } else {
                    for (Marker mk : mkList) {
                        float x, y;
                        x = scaledVal(mk.getX()) - mkh + mapLeft;
                        y = scaledVal(mk.getY()) - mkh + mapTop;
                        box = new RectF(x, y, x + 2 * mkh, y + 2 * mkh);
                        if (box.contains(point.x, point.y)) {
                            showPopup(MainActivity.this, mk.getCat(), mk.getId());
                        }
                    }
                }

                return true;
            }
        });
    }

    public void updateScaleValues() {

        mapWidth = scaledVal(500);
        mapHeight = scaledVal(828);
        width = scaledVal(2000);
        height = scaledVal(2000);
        circleHalf = circle.getWidth() / 2;
        pointBigX = pointp.getWidth() / 2;
        pointBigY = pointp.getHeight() * 5 / 8;
        pointSmallX = pointp2.getWidth() / 2;
        pointSmallY = pointp2.getHeight() * 5 / 8;

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
            bmp = BitmapFactory.decodeStream(open, null, options);

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

        Intent intent = new Intent(MainActivity.this, DecoderActivity.class);
        startActivityForResult(intent, 2);
    }

    public void callLog(View v) {

        Intent intent = new Intent(MainActivity.this, LogActivity.class);
        startActivity(intent);
    }

    public void callSearch(View v) {

        Intent intent = new Intent(MainActivity.this, TabsActivity.class);
        startActivity(intent);
    }

    public void callFavourite(View v) {

        Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
        startActivity(intent);
    }


    public void callLock(View v) {

        if (state == 2) {
            state = 1;
        } else if(state==1){
            state = 2;
            degrees2 = degrees1;
        }
        setValues();
    }

    public void callClear(View v) {

        mkList.clear();
        mkList = new ArrayList<Marker>();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        message = data.getStringExtra("MESSAGE");
        if (requestCode == 2 && !message.equals("null")) {
            String link = data.getStringExtra("MESSAGE");
            QRcode qr = db.getQRcodeLink(link);
            if (state == 0  ) {
                state = 1;
            }
            posX = (float) qr.getX();
            posY = (float) qr.getY();
            updateLeftTop();
            img.setScrollPosition(imgX / width, imgY / height);
            int size = db.getLogSize();
            Timelog tlog = new Timelog(size + 1, qr.geTag(), date(), time());
            db.insertLog(tlog);
            skipIntent = 1;

        }

    }

    public void setValues() {

        mkh = circleHalf;
        phX = pointBigX;
        phY = pointBigY;
        pinX = imgX - phX;
        pinY = imgY - phY;

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);


    }


    @Override
    protected void onResume() {
        super.onResume();

        if(POP_PRESENT==1 ){
            popup.dismiss();
        }

        if (skipIntent == 0) {
            Intent in = getIntent();
            act = in.getIntExtra("act_val", act);
            if (act == 1 || act == 2) {

                Eid = in.getIntExtra("id", Eid);
                Ex = in.getIntExtra("x_val", Ex);
                Ey = in.getIntExtra("y_val", Ey);
                mk = new Marker(Eid, act, Ex, Ey);
                float x = scaledVal(mk.getX());
                float y = scaledVal(mk.getY());
                float xw = x + mapLeft;
                float yw = y + mapTop;
                PointF temp = rotateP(xw, yw, degrees2, imgX, imgY);
                mk.setMapx(temp.x);
                mk.setMapy(temp.y);
                if (!mkList.contains(mk)) {
                    mkList.add(mk);
                }
            }
        }

        skipIntent = 0;
        setValues();

        runnable = new Runnable() {

            public void run() {
                update();
                handler.postDelayed(this, 30);

            }
        };
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 500);

    }


    public void update() {

        if (img.getCurrentZoom() > midLevel) {
            point = pointp2;
            marker = circle;
            mkh = circleHalf;
            phX = pointSmallX;
            phY = pointSmallY;

        } else {
            point = pointp;
            marker = circle;
            mkh = circleHalf;
            phX = pointBigX;
            phY = pointBigY;

        }

        if (state == 1) {
            updateMarker();
        }
        mapPlot();

    }

    public void updateMarker() {

        for (Marker marker : mkList) {
            int x = scaledVal(marker.getX());
            int y = scaledVal(marker.getY());
            float xw = (float) x + mapLeft;
            float yw = (float) y + mapTop;
            PointF temp = rotateP(xw, yw, degrees1, imgX, imgY);
            marker.setMapx(temp.x);
            marker.setMapy(temp.y);
        }
    }


    public void mapPlot() {

        if (map != null) {
            map.recycle();
        }

        map = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(map);
        mapMatrix.reset();
        pinX = imgX - phX;
        pinY = imgY - phY;

        if (state == 0) {

            mapMatrix.setTranslate(mapLeft, mapTop);
            canvas.drawBitmap(mapp, mapMatrix, mPaint);
            for (Marker mk : mkList) {
                float mkx, mky;
                mkx = mk.getX();
                mky = mk.getY();
                markerMatrix.setTranslate(scaledVal(mkx) + mapLeft - mkh, scaledVal(mky) + mapTop - mkh);
                try {
                    canvas.drawBitmap(marker, markerMatrix, mPaint);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } else if (state == 1) {

            mapMatrix.setTranslate(mapLeft, mapTop);
            mapMatrix.postRotate(degrees1, imgX, imgY);
            canvas.drawBitmap(mapp, mapMatrix, mPaint);
            for (Marker mk : mkList) {
                float mkx, mky;
                mkx = mk.getMapx();
                mky = mk.getMapy();
                markerMatrix.setTranslate(mkx - mkh, mky - mkh);
                try {
                    canvas.drawBitmap(marker, markerMatrix, mPaint);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            pointerMatrix.setTranslate(pinX, pinY);
            canvas.drawBitmap(point, pointerMatrix, mPaint);


        } else if (state == 2) {

            mapMatrix.setTranslate(mapLeft, mapTop);
            mapMatrix.postRotate(degrees2, imgX, imgY);
            canvas.drawBitmap(mapp, mapMatrix, mPaint);
            for (Marker mk : mkList) {
                float mkx, mky;
                mkx = mk.getMapx();
                mky = mk.getMapy();
                try {
                    canvas.drawBitmap(marker, mkx - mkh, mky - mkh, mPaint);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            pointerMatrix.reset();
            pointerMatrix.setTranslate(pinX, pinY);
            pointerMatrix.postRotate(-degrees1+degrees2, imgX, imgY);
            canvas.drawBitmap(point, pointerMatrix, mPaint);

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
        String date1 = "";
        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH) + 1;
        year = date.get(Calendar.YEAR);
        date1 = "" + day + "/" + month + "/" + year;
        return date1;
    }

    public String time() {

        GregorianCalendar date = new GregorianCalendar();
        String sec, min, hr;
        String time1 = "";
        long time = System.currentTimeMillis();
        double hours = ((time / 3600000) % 24) + 6.5;
        int hrs = (int) hours;
        if (hrs < 10) {
            hr = "0" + hrs;
        } else {
            hr = "" + hrs;
        }
        if (date.get(Calendar.SECOND) < 10) {
            sec = "0" + date.get(Calendar.SECOND);
        } else {
            sec = "" + date.get(Calendar.SECOND);
        }
        if (date.get(Calendar.MINUTE) < 10) {
            min = "0" + date.get(Calendar.MINUTE);
        } else {
            min = "" + date.get(Calendar.MINUTE);
        }
        time1 = "" + hr + ":" + min + ":" + sec;
        return time1;
    }


    public SensorEventListener compassListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            degrees1 = -1 * event.values[0] - 58;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public PointF rotateP(float a, float b, float degrees, float x, float y) {
        transform.setRotate(degrees, x, y);
        float[] pts = new float[2];
        pts[0] = a;
        pts[1] = b;
        transform.mapPoints(pts);
        PointF newPoint = new PointF(pts[0], pts[1]);
        return newPoint;
    }

    private void showPopup(final Activity context, int cat, int position) {

        POP_PRESENT = 1;
        windowW = getWindowManager().getDefaultDisplay().getWidth();
        windowH = getWindowManager().getDefaultDisplay().getHeight();
        winW = windowW / 10;
        winH = windowH / 10;
        WiW = winW * 6;
        WiH = (int) (winH * 3.8);
        imgW = winW * 3;
        imgH = winH * 3;
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(WiW);
        popup.setHeight(WiH);



        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                POP_PRESENT = 0;
            }
        });

        popup.setOutsideTouchable(true);
        int OFFSET_X = winW * 2;
        int OFFSET_Y = winH;

        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
        ImageView img = (ImageView) layout.findViewById(R.id.imageView);
        img.getLayoutParams().width = imgW;
        TextView nameTV = (TextView) layout.findViewById(R.id.nameT);
        TextView desgTV = (TextView) layout.findViewById(R.id.desgT);
        TextView emailTV = (TextView) layout.findViewById(R.id.emailT);


        if (cat == 1) {
            Employee emp = db.getEmployeeId(position);
            img.setImageResource(emp.getPic());
            nameTV.setText(emp.getName());
            desgTV.setText(emp.getDesg());
            emailTV.setText(emp.getEmail());
        } else {
            Place plc = db.getPlaceId(position);
            Bitmap temp = BitmapFactory.decodeResource(getResources(), plc.getPic());
            Bitmap pic = Bitmap.createScaledBitmap(temp, imgW + 10, imgH + 10, true);
            img.setImageBitmap(pic);
            nameTV.setText(plc.getName());
            desgTV.setText(plc.getType());
            emailTV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        location[0] = 0;
        location[1] = winH;
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    public int scaledVal(int x) {
        float val = (float) x;
        return (int) scaledVal(val);
    }

    public float scaledVal(float x) {
        return x * scaleLevel / 4;
    }

    public void updateLeftTop(){
        float x,y;
        x=scaledVal(posX);
        y=scaledVal(posY);
        mapLeft=imgX-x;
        mapTop=imgY-y;
    }


    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

}
