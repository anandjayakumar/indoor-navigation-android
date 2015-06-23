package com.app2.aar.databaseimg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends ActionBarActivity{

    CustomAdapter adapter;
    EditText editsearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DatabaseHandler handler = new DatabaseHandler(this);
        List<Employee> employees = handler.getAllContacts();
        for (Employee info : employees) {
            handler.deleteContact(info);
        }
        handler.addContact(new Employee("Aarathi R", R.drawable.flag,2,1,"Software Engineer"));
        handler.addContact(new Employee("Anita Grace Daniel", R.drawable.flag,3,2,"Software Engineer"));
        handler.addContact(new Employee("Anand Jaykumar", R.drawable.flag,2,4,"Software Engineer"));
        handler.addContact(new Employee("Elizabeth George", R.drawable.flag,3,1,"Software Engineer"));
        handler.addContact(new Employee("Gautham Krishna", R.drawable.flag,4,3,"Software Engineer"));
        handler.addContact(new Employee("Koushik", R.drawable.flag,1,1,"Software Engineer"));
        handler.addContact(new Employee("Lekha K P", R.drawable.flag,2,3,"Software Engineer"));
        handler.addContact(new Employee("Sona Thaj", R.drawable.flag,2,2,"Software Engineer"));
        // get image from drawable
        /*Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.flag);
        Bitmap image1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.orange);
// convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        image1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
        byte imageInByte1[] = stream1.toByteArray();
        */


        //List<Employee> employees = handler.getAllContacts();
        /*for (Employee info : employees) {
            String employeeDetails = "Name    "+ info.getEmployeeName() + "   Image    " + info.getEmployeeImg();
            Log.d("Employee information ", employeeDetails);
        }*/
        List<Employee> emp = handler.getAllContacts();
        adapter=new CustomAdapter(this,emp);
        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        editsearch = (EditText) findViewById(R.id.editv);

// Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
// TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
// TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
// TODO Auto-generated method stub
            }
        });
    }
    public void just_fun(View v){
        int i;
        //List<Employee> emp=new ArrayList<Employee>();
        /*DatabaseHandler handler = new DatabaseHandler(this);
        List<Employee> emp = handler.getAllContacts();
        adapter=new CustomAdapter(this,emp);
        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);*/
        //tv.setText(employees.size());
        /*for (Employee info : emp) {
            tv.setText("NAME: " + info.getEmployeeName() + "\n");
            //byte[] data=info.getEmployeeImg();
               // Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //im.setImageBitmap(bitmap);
            //im.setImageResource(getResources().getIdentifier("flag", "drawable", getPackageName()));
            im.setImageResource(info.getEmployeeImg());

        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        /*if (id == id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}