package com.anand.mapapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.anand.mapapp.Classes.CustomAdapter;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Label;
import com.anand.mapapp.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class EmployeeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    Point p;
    FrameLayout layout_EMP;
    private Inflater inflater;
    int WiW, WiH, windowW, windowH, winW, winH, imgW, imgH, padding;

    CustomAdapter adapter;
    EditText search;
    SwipeMenuListView lv;
    SwipeMenuCreator creator=null,creator2=null;
    String designationEmp;
    int currentList=1;
    int state=0;
    int TYPE_NAME=1;

    String text;

    int TYPE_DESIGNATION=2;
    int TYPE_NAME_DESIGNATION=3;
    int CLICKED=0;
    int keyCountGlobal=0,keyCountLocal=0;

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

        layout_EMP = (FrameLayout) findViewById(R.id.emp_lay);
        layout_EMP.getForeground().setAlpha(0);
        windowW = getWindowManager().getDefaultDisplay().getWidth();
        windowH = getWindowManager().getDefaultDisplay().getHeight();
        winW = windowW / 10;
        winH = windowH / 10;
        WiW = winW * 8;
        WiH = winH * 7;
        imgW = winW * 4;
        imgH = winH * 4;
        padding = winW;
        search.setHint("Search Here");

        images=new int[] { R.drawable.project_manager, R.drawable.developer,
                R.drawable.architect, R.drawable.software_engineer,
                R.drawable.trainee, R.drawable.intern, R.drawable.designer };
        designations=new String[]{"Project Manager","Developer","Architect","Software Engineer","Trainee","Intern","Designer"};


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

        final Handler h = new Handler();
        final Runnable task = new Runnable() {
            @Override
            public void run() {


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
        };

        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem favItem = new SwipeMenuItem(getApplicationContext());
                favItem.setBackground(new ColorDrawable(Color.rgb(216, 219, 224)));
                favItem.setWidth(dp2px(90));
                favItem.setIcon(R.drawable.fav);
                menu.addMenuItem(favItem);

                SwipeMenuItem infoItem = new SwipeMenuItem(getApplicationContext());
                infoItem.setBackground(new ColorDrawable(Color.rgb(120,120,120)));
                infoItem.setWidth(dp2px(90));
                infoItem.setIcon(R.drawable.info3);
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

//                        if (p != null)
                        showPopup(EmployeeActivity.this);
//                        SweetAlertDialog sd= new SweetAlertDialog(EmployeeActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                            .setTitleText((employees.get(position).getName()))
//                                .setContentText((employees.get(position).getDesg()))
//                                .setCustomImage(employees.get(position).getPic());
//                        sd.show();


                        break;
                }
                return false;
            }
        });

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                text = search.getText().toString().toLowerCase(Locale.getDefault());
                h.removeCallbacks(task);
                h.postDelayed(task, 400);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        //button = (Button) findViewById(R.id.show_popup);
        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        //button.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
//        p = new Point();
//        p.x = location[0];
//        p.y = location[1];
    }

    // The method that displays the popup.
    private void showPopup(final Activity context) {

        layout_EMP.getForeground().setAlpha(400); LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(WiW);
        popup.setHeight(WiH);
        popup.setFocusable(false);
        popup.setAnimationStyle(R.style.dialog_blue_button);

        int OFFSET_X = winW;
        int OFFSET_Y = winH;

        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
        ImageView img = (ImageView) layout.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.profile);
        img.setPadding(padding, padding, padding, 10);
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                layout_EMP.getForeground().setAlpha(0);
                popup.dismiss();
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
