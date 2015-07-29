package com.anand.mapapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anand.mapapp.Classes.CustomAdapter;
import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Label;
import com.anand.mapapp.Classes.ListViewAdapter;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeActivity extends Activity implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private ListViewAdapter mAdapter;
    Point p;
    FrameLayout layout_EMP;
    PopupWindow popup;
    CustomAdapter adapter;
    EditText search;
    ListView labelList;

    private static final int TYPE_NAME=1;
    private static final int TYPE_DESIGNATION=2;
    private static final int TYPE_NAME_DESIGNATION=3;

    int WiW, WiH, windowW, windowH, winW, winH, imgW, imgH, padding;
    int POP_PRESENT=0;
    int posIcon;
    int currentList=1;
    int state=0;

    int CLICKED=0;
    int images[];

    String designationEmp;
    String text;
    String designations[];

    DatabaseHandler handler;

    List<Label> arraylist = new ArrayList<>();
    List<Employee> employees;

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
        WiH = winH * 5;
        imgW = winW * 5;
        imgH = winH * 5;
        padding = winW;
        search.setHint("Search Here");

        images=new int[] { R.drawable.icon_pm, R.drawable.icon_a, R.drawable.icon_de, R.drawable.icon_ba,
                R.drawable.icon_tr, R.drawable.icon_in };
        designations=new String[]{"Project Manager","Architect","Designer","Business Analyst","Trainee","Intern"};


        mListView = (ListView) findViewById(R.id.listEmp);
        mListView.setOnItemClickListener(this);

        labelList=(ListView)findViewById(R.id.labelList);
        labelList.setOnItemClickListener(this);

        for (int i = 0; i < designations.length; i++)
        {
            Label wp = new Label(images[i], designations[i]);
            arraylist.add(wp);
        }


        handler = new DatabaseHandler(this);
        adapter=new CustomAdapter(this,3,arraylist,null,0,0);
        labelList.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        labelList.setAdapter(adapter);

        final Handler h = new Handler();
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                if (text.length() == 0 || text.equals("")) {
                    if (CLICKED == 1) {
                        currentList = 2;
                        search.setHint(designationEmp);
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, arraylist.get(posIcon).getImage(), 0);
                        employees = handler.getEmployeesByName(TYPE_NAME_DESIGNATION, text, designationEmp);
                        mAdapter=new ListViewAdapter(EmployeeActivity.this, 1, employees, null);
                        mListView.setAdapter(mAdapter);
                        mAdapter.setMode(Attributes.Mode.Single);
                        mListView.setVisibility(View.VISIBLE);
                        labelList.setVisibility(View.GONE);
                        state = 1;
                    } else {
                        currentList = 1;
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, 0, 0);
                        search.setHint("Search Here");
                        adapter = new CustomAdapter(EmployeeActivity.this, 3, arraylist,null,0,0);
                        labelList.setAdapter(adapter);
                        mListView.setVisibility(View.GONE);
                        labelList.setVisibility(View.VISIBLE);
                        state = 0;
                    }
                }
                else {
                    if (CLICKED == 1) {
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, arraylist.get(posIcon).getImage(), 0);
                        employees = handler.getEmployeesByName(TYPE_NAME_DESIGNATION, text, designationEmp);
                        state = 2;

                    } else {
                        employees = handler.getEmployeesByName(TYPE_NAME, text, null);
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, 0, 0);
                        state = 1;
                    }

                    mListView.setVisibility(View.VISIBLE);
                    labelList.setVisibility(View.GONE);
                    mAdapter=new ListViewAdapter(EmployeeActivity.this, 1, employees, null);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);
                    currentList = 2;
                }

            }
        };
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
    public void showPopup(final Activity context, int position) {

        POP_PRESENT=1;
        layout_EMP.getForeground().setAlpha(400);
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
                layout_EMP.getForeground().setAlpha(0);
            }
        });
        popup.setFocusable(true);

        int OFFSET_X = winW;
        int OFFSET_Y = winH;

        //noinspection deprecation
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        ImageView img = (ImageView) layout.findViewById(R.id.imageView);
        img.setImageResource(employees.get(position).getPic());
        img.getLayoutParams().width = imgW;
        img.getLayoutParams().height = imgH;

        TextView nameTV=(TextView)layout.findViewById(R.id.nameT);
        nameTV.setText(employees.get(position).getName());

        TextView desgTV=(TextView)layout.findViewById(R.id.desgT);
        desgTV.setText(employees.get(position).getDesg());

        TextView emailTV=(TextView)layout.findViewById(R.id.emailT);
        emailTV.setText(employees.get(position).getEmail());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int[] location = new int[2];
        location[0]=0; location[1]=winH;
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(currentList == 1) {
            posIcon=position;
            search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0,arraylist.get(posIcon).getImage(), 0);
            designationEmp=(arraylist.get(position).getName()).toLowerCase(Locale.getDefault());
            employees = handler.getEmployeesByName(TYPE_DESIGNATION, designationEmp, null);
            mAdapter=new ListViewAdapter(EmployeeActivity.this, 1, employees, null);
            mListView.setVisibility(View.VISIBLE);
            labelList.setVisibility(View.GONE);
            mListView.setAdapter(mAdapter);
            mAdapter.setMode(Attributes.Mode.Single);
            currentList=2;
            CLICKED=1;
            state=1;
            search.setHint(designationEmp);
        }
        else {
                Intent returnIntent = new Intent(this, MainActivity.class);
                returnIntent.putExtra("act_val", 1);
                returnIntent.putExtra("id",
                        (employees.get(position).getId()));
                returnIntent.putExtra("x_val",
                        (employees.get(position).getX()));
                returnIntent.putExtra("y_val",
                        (employees.get(position).getY()));
                startActivity(returnIntent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && state!=0) {
            if(state==2){
                if(POP_PRESENT==0) {
                    search.setHint(designationEmp);
                    if (search.getText() != null) {
                        search.setText(null);
                        CLICKED = 1;
                    } else {
                        CLICKED = 0;

                    }
                    state = 1;
                    mListView.setVisibility(View.VISIBLE);
                    labelList.setVisibility(View.GONE);
                }
                else if(POP_PRESENT==1) {
                    popup.dismiss();
                }
            }
            else{
                if(POP_PRESENT==0) {
                    CLICKED = 0;
                    search.setText(null);
                }
                else if(POP_PRESENT==1) {
                    popup.dismiss();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}