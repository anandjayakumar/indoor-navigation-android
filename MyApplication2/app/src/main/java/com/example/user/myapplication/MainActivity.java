package com.example.user.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    int i=0;
    Timer timer = new Timer();
    TouchImageView image;
    //Bitmap myBitmap;
    //ImageView img;
    int x1,x2,y1,y2;
    float imgX,imgY,angle=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TouchImageView img = (TouchImageView) findViewById(R.id.img);
        img.setImageResource(R.drawable.a1);
        img.setMaxZoom(4f);
        img.setMinZoom(2f);
        img.setZoom(2f);
       // SurfaceView surface = (SurfaceView)findViewById(R.id.img);
       // DrawingPanel panel = (DrawingPanel)findViewById(R.id.panel);

     /*   img = (ImageView)findViewById(R.id.imageView);
        Bitmap tempBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        myPaint.setARGB(1,100,100,100);
        tempCanvas.drawBitmap(tempBitmap, 0, 0, null);
        x1=50;
        y1=50;
        x2=100;
        y2=100;


        tempCanvas.drawRoundRect(new RectF(x1,y1,x2,y2), 2, 2, myPaint);

        img.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));*/



       /* Bitmap map = Bitmap.createBitmap(img.getWidth(),img.getHeight(),Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(map);
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.puppy);
        canvas.setBitmap(bitmap);
        canvas.drawLine(10, 10, 100, 100, myPaint);
        Drawable obj = new BitmapDrawable(getResources(), map);
        img.setImageDrawable(obj);*/




    }



    @Override
    protected void onResume() {
        super.onResume();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        update();
                    }
                });

            }
        }, 2000, 100);

    }

     public void update() {

        //image = (TouchImageView) findViewById(R.id.img);
        /*if (i % 2 == 0) {
            //image.setImageResource(R.drawable.a1);
            mapDraw1(image);
        } else {
            //image.setImageResource(R.drawable.a2);
            mapDraw2(image);
        }*/
         mapDraw();
        i++;

    }

    public void mapDraw() {

        TouchImageView img = (TouchImageView) findViewById(R.id.img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
        Bitmap map = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(map);
        Paint myPaint = new Paint();
        Matrix matrix = new Matrix();
        imgX = getWindowManager().getDefaultDisplay().getWidth() / 2;
        imgY = getWindowManager().getDefaultDisplay().getHeight() / 2;
        angle += 10;
        angle = angle % 359;

        matrix.setRotate(angle, 600, 800);
        canvas.drawRoundRect(new RectF(600, 630, 800, 840), 2, 2, myPaint);
        canvas.drawBitmap(bitmap, matrix, null);
        img.setImageBitmap(map);

    }

    public void mapDraw1(View v) {

        TouchImageView img = (TouchImageView) findViewById(R.id.img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
        Bitmap map = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(map);
        Paint myPaint = new Paint();
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawRoundRect(new RectF(20, 20, 80, 80), 2, 2, myPaint);
        //canvas.rotate(10);
        img.setImageBitmap(map);

    }

    public void mapDraw2(View v) {

        TouchImageView image = (TouchImageView) findViewById(R.id.img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
        image.setImageBitmap(bitmap);


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
