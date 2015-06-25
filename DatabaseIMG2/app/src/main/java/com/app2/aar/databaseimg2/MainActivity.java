package com.app2.aar.databaseimg2;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    CustomAdapter adapter;
    EditText editsearch;
    SearchView search;
    private List<Employee> employees;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        search=(SearchView) findViewById(R.id.searchView1);
        search.setQueryHint("Search Here");

        DatabaseHandler handler = new DatabaseHandler(this);


        employees = handler.getAllEmployees();
        adapter=new CustomAdapter(this,1,employees,null);
        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adapter);

     //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                String text =newText.toLowerCase(Locale.getDefault());
                adapter.filter(1,text);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                //Here u can get the value "query" which is entered in the search box.
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
        returnIntent.putExtra("name",
                        (employees.get(position).getEmployeeName()));
        returnIntent.putExtra("x_val",
                        (employees.get(position).getXval()));
        returnIntent.putExtra("y_val",
                        (employees.get(position).getYval()));
        returnIntent.putExtra("desg",
                        (employees.get(position).getEmployeeDesg()));
        returnIntent.putExtra("images",
                        (employees.get(position).getEmployeeImg()));
                setResult(StartActivity.RESULT_OK, returnIntent);
                finish();

    }
}