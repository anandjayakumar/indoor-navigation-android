package com.anand.canvas1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    Bitmap bitmap,map;
    Canvas canvas;
    ImageView img;
    public Paint myPaint = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // img = (ImageView)findViewById(R.id.img);

        //map = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
       // canvas = new Canvas(map);
      //  onDraw();
       // Drawable obj = new BitmapDrawable(getResources(), map);
       // img.setImageDrawable(obj);




    }

    @Override
    protected void onResume() {
        super.onResume();
        img = (ImageView)findViewById(R.id.img);
        //map = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
        map = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
        canvas = new Canvas(map);



        onDraw();

      /*  Drawable obj = new BitmapDrawable(getResources(), map);
        img.setImageDrawable(obj);*/

    }

    protected void onDraw() {



        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawRoundRect(new RectF(20,20,80,80), 2, 2, myPaint);
        img.setImageBitmap(map);
       // canvas.drawLine(10, 10, 100, 100, myPaint);

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
