package com.app2.aar.databaseimg;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class StartActivity extends ActionBarActivity {
    //public final static String name = "com.app1.aaru.myapp1.name";
    //public final static String x_val = "com.app1.aaru.myapp1.x";
    //public final static String y_val = "com.app1.aaru.myapp1.y";
    //public final static String desg = "com.app1.aaru.myapp1.desg";
    //public final static String images = "com.app1.aaru.myapp1.image";

    String namep;
    int x,y;
    String designation;
    int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void find_emp(View v){
        Intent i=new Intent(this,MainActivity.class);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        findViewById(R.id.x_label).setVisibility(View.VISIBLE);
        findViewById(R.id.y_label).setVisibility(View.VISIBLE);
        findViewById(R.id.desg_label).setVisibility(View.VISIBLE);
        TextView name = (TextView) findViewById(R.id.textv);
        TextView x_co = (TextView) findViewById(R.id.x);
        TextView y_co = (TextView) findViewById(R.id.y);
        TextView desg = (TextView) findViewById(R.id.desg);
        ImageView img=(ImageView)findViewById(R.id.imgv);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2&&resultCode==RESULT_OK)
        {
            namep = data.getStringExtra("name");
            x = data.getIntExtra("x_val", 0);
            y= data.getIntExtra("y_val",0);
            designation = data.getStringExtra("desg");
            image = data.getIntExtra("images", image);
            name.setText(namep);
            x_co.setText(String.valueOf(x));
            y_co.setText(String.valueOf(y));
            desg.setText(designation);
            img.setImageResource(image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
