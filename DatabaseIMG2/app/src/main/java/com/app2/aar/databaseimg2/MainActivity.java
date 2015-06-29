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
    SearchView search;
    ListView lv;
    int currentList;

    DatabaseHandler handler;
    int images[];
    String designations[];
    List<Label> arraylist = new ArrayList<Label>();

    public MainActivity(){
        this.currentList=1;
    }
    private List<Employee> employees;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        search=(SearchView) findViewById(R.id.searchView1);
        search.setQueryHint("Search Here");

        images=new int[] { R.drawable.project_manager, R.drawable.developer,
                R.drawable.architect, R.drawable.software_engineer,
                R.drawable.trainee, R.drawable.intern, R.drawable.designer };
        designations=new String[]{"Project Manager","Developer","Architect","Software Engineer","Trainee","Intern","Designer"};

        // Locate the ListView in listview_main.xml
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);

        for (int i = 0; i < designations.length; i++)
        {
            Label wp = new Label(images[i], designations[i]);
            arraylist.add(wp);
        }
        handler = new DatabaseHandler(this);
        adapter=new CustomAdapter(this,3,arraylist);
        lv.setAdapter(adapter);

     //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                String text =newText.toLowerCase(Locale.getDefault());
                if(text.length()==0){
                    currentList=1;
                    adapter=new CustomAdapter(MainActivity.this,3,arraylist);
                    lv.setAdapter(adapter);
                    adapter.filter(3,text);
                }
                else{
                    employees = handler.getAllEmployees();
                    adapter=new CustomAdapter(MainActivity.this,1,employees,null);
                    lv.setAdapter(adapter);
                    currentList=2;
                    adapter.filter(1,text);
                }
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
        if(currentList==1) {
            String designationEmp=(arraylist.get(position).getName()).toLowerCase(Locale.getDefault());
            ArrayList <Employee> employeeList=new ArrayList<Employee>();
            employees=handler.getAllEmployees();
            adapter=new CustomAdapter(MainActivity.this,1,employees,null);
            lv.setAdapter(adapter);
            currentList=2;
            adapter.filter(4,designationEmp);
        }
 else {

            Intent returnIntent = new Intent(this, StartActivity.class);
            returnIntent.putExtra("act_val", 1);
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
            startActivity(returnIntent);
//                setResult(StartActivity.RESULT_OK, returnIntent);
//                finish();

        }

    }
}