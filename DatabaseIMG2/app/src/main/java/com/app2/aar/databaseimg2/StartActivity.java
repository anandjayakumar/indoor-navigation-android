package com.app2.aar.databaseimg2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StartActivity extends ActionBarActivity implements DBQueries.EmployeeinsertionCompletion{
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
        initializeDataBase();
    }

    public void find_emp(View v){
        Intent i=new Intent(this,MainActivity.class);
        startActivityForResult(i, 2);


    }

 /*   public void find_plc(View v){
        Intent i=new Intent(this,PlaceActivity.class);
        startActivityForResult(i, 4);
        //findViewById(R.id.x_label).setVisibility(View.VISIBLE);
        //findViewById(R.id.y_label).setVisibility(View.VISIBLE);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        TextView name = (TextView) findViewById(R.id.textv);
        TextView x_co = (TextView) findViewById(R.id.x);
        TextView y_co = (TextView) findViewById(R.id.y);
        TextView desg = (TextView) findViewById(R.id.desg);
        ImageView img=(ImageView)findViewById(R.id.imgv);
        // check if the request code is same as what is passed  here it is 2
        if((requestCode==2||requestCode==4)&&resultCode==RESULT_OK)
        {
            findViewById(R.id.x_label).setVisibility(View.VISIBLE);
            findViewById(R.id.y_label).setVisibility(View.VISIBLE);
            namep = data.getStringExtra("name");
            x = data.getIntExtra("x_val", 0);
            y= data.getIntExtra("y_val", 0);
            image = data.getIntExtra("images", image);
            name.setText(namep);
            x_co.setText(String.valueOf(x));
            y_co.setText(String.valueOf(y));
            img.setImageResource(image);
        }
        if(requestCode==2){
            findViewById(R.id.desg_label).setVisibility(View.VISIBLE);
            designation = data.getStringExtra("desg");
            desg.setText(designation);
        }
        if(requestCode==4){
            findViewById(R.id.desg_label).setVisibility(View.INVISIBLE);
            desg.setText("");
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

    private DatabaseHandler handler;

   private void initializeDataBase() {

       handler = new DatabaseHandler(this);
       addEmployees();
       addPlaces();
    }

    private void addEmployees(){
        //DBQueries dbQueries = new DBQueries();
        //dbQueries.insertEmployees(this);
        List<Employee> employees = handler.getAllEmployees();
        for (Employee info : employees) {
            handler.deleteEmployee(info);
        }
        handler.addEmployee(new Employee("Aarathi R", R.drawable.flag, 2, 1, "Software Engineer"));
        handler.addEmployee(new Employee("Anita Grace Daniel", R.drawable.flag, 3, 2, "Project Manager"));
        handler.addEmployee(new Employee("Anand Jaykumar", R.drawable.flag, 2, 4, "Software Engineer"));
        handler.addEmployee(new Employee("Elizabeth George", R.drawable.flag, 3, 1, "Designer"));
        handler.addEmployee(new Employee("Gautham Krishna", R.drawable.flag, 4, 3, "Intern"));
        handler.addEmployee(new Employee("Koushik", R.drawable.flag, 1, 1, "Software Engineer"));
        handler.addEmployee(new Employee("Lekha K P", R.drawable.flag, 2, 3, "Project Manager"));
        handler.addEmployee(new Employee("Sona Thaj", R.drawable.flag, 2, 2, "Designer"));
    }

    private void addPlaces(){
        List<Place> places = handler.getAllPlaces();
        for (Place info : places) {
            handler.deletePlace(info);
        }
        handler.addPlace(new Place("Meeting Room 1",1,3,R.drawable.ic_eatery));
        handler.addPlace(new Place("Meeting Room 2",3,2,R.drawable.ic_eatery));
        handler.addPlace(new Place("Washroom",2,3,R.drawable.ic_eatery));
        handler.addPlace(new Place("Beverages",2,1,R.drawable.ic_eatery));
        handler.addPlace(new Place("Lunch Room", 3, 3, R.drawable.ic_eatery));
    }

    @Override
    public void employeeinsertionCompleted(){

    }
}
