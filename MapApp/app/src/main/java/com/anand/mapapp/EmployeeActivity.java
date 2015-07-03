package com.anand.mapapp;

import
        android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EmployeeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    CustomAdapter adapter;
    EditText search;
    SwipeMenuListView lv;
    SwipeMenuCreator creator=null,creator2=null;
    String designationEmp;
    int currentList=1;
    int state=0;
    int TYPE_NAME=1;
    int TYPE_DESIGNATION=2;
    int TYPE_NAME_DESIGNATION=3;
    int CLICKED=0;

    DatabaseHandler handler;
    int images[];
    String designations[];
    List<Label> arraylist = new ArrayList<Label>();
    public List<Employee> employees;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employee);
        search=(EditText) findViewById(R.id.searchView1);

        search.setHint("Search Here");

        images=new int[] { R.drawable.project_manager, R.drawable.developer,
                R.drawable.architect, R.drawable.software_engineer,
                R.drawable.trainee, R.drawable.intern, R.drawable.designer };
        designations=new String[]{"Project Manager","Developer","Architect","Software Engineer","Trainee","Intern","Designer"};

        // Locate the ListView in listview_main.xml
        lv =(SwipeMenuListView)findViewById(R.id.listView);
        lv.setOnItemClickListener(this);

        for (int i = 0; i < designations.length; i++)
        {
            Label wp = new Label(images[i], designations[i]);
            arraylist.add(wp);
        }
        handler = new DatabaseHandler(this);
        adapter=new CustomAdapter(this,3,arraylist);
        lv.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if (text.length() == 0 || text == "") {
                    if (CLICKED == 1) {
                        currentList = 2;
                        search.setHint(designationEmp);
                        employees = handler.getEmployeesByName(TYPE_NAME_DESIGNATION, text, designationEmp);
                        adapter = new CustomAdapter(EmployeeActivity.this, 1, employees, null);
                        lv.setMenuCreator(creator);
                        lv.setAdapter(adapter);
                        adapter.filter(1, text);
                        state = 1;
                    } else {
                        currentList = 1;
                        search.setHint("Search Here");
                        adapter = new CustomAdapter(EmployeeActivity.this, 3, arraylist);
                        lv.setAdapter(adapter);
                        adapter.filter(3, text);
                        state = 0;
                        lv.setMenuCreator(creator2);
                    }
                } else {
                    if (CLICKED == 1) {
                        employees = handler.getEmployeesByName(TYPE_NAME_DESIGNATION, text, designationEmp);
                        state = 2;

                    } else {
                        employees = handler.getEmployeesByName(TYPE_NAME, text, null);
                        state = 1;

                    }
                    lv.setMenuCreator(creator);
                    adapter = new CustomAdapter(EmployeeActivity.this, 1, employees, null);
                    lv.setAdapter(adapter);
                    currentList = 2;
                    adapter.filter(1, text);


                }
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

        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem favItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                favItem.setBackground(new ColorDrawable(Color.rgb(216, 219, 224)));
                // set item width
                favItem.setWidth(dp2px(90));
                // set item title
                favItem.setIcon(R.drawable.fav);
                // add to menu
                menu.addMenuItem(favItem);

                SwipeMenuItem infoItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                infoItem.setBackground(new ColorDrawable(Color.rgb(120,120,120)));
                // set item width
                infoItem.setWidth(dp2px(90));
                // set a icon
                infoItem.setIcon(R.drawable.info3);
                // add to menu
                menu.addMenuItem(infoItem);
            }
        };


        creator2 = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {


            }
        };



        lv.setMenuCreator(creator2);

        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "open", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        SweetAlertDialog sd= new SweetAlertDialog(EmployeeActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText((employees.get(position).getName()))
                                .setContentText((employees.get(position).getDesg()))
                                .setCustomImage(employees.get(position).getPic());
                        sd.show();
                        break;
                }
                return false;
            }
        });
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee, menu);
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
            designationEmp=(arraylist.get(position).getName()).toLowerCase(Locale.getDefault());
            employees=handler.getEmployeesByName(TYPE_DESIGNATION, designationEmp, null);
            adapter=new CustomAdapter(EmployeeActivity.this,1,employees,null);
            lv.setAdapter(adapter);
            currentList=2;
            CLICKED=1;
            state=1;
            lv.setMenuCreator(creator);
            search.setHint(designationEmp);
            adapter.filter(4,designationEmp);
        }
 else {
            Intent returnIntent = new Intent(this, MainActivity.class);
            returnIntent.putExtra("act_val", 1);
            returnIntent.putExtra("name",
                    (employees.get(position).getName()));
            returnIntent.putExtra("x_val",
                    (employees.get(position).getX()));
            returnIntent.putExtra("y_val",
                    (employees.get(position).getY()));
            returnIntent.putExtra("desg",
                    (employees.get(position).getDesg()));
            returnIntent.putExtra("images",
                    (employees.get(position).getPic()));
            startActivity(returnIntent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && state!=0) {
            if(state==2){
                search.setHint(designationEmp);
                if(search.getText()!=null){
                    search.setText(null);
                    CLICKED=1;
                }
                else{
                    CLICKED=0;

                }
                state=1;
                lv.setMenuCreator(creator);
            }
            else{
                CLICKED=0;
                search.setHint("Search Here");
                search.setText(null);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}
