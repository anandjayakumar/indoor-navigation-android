package com.anand.mapapp.Activities;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anand.mapapp.Classes.CustomAdapter;
import com.anand.mapapp.Classes.SwipeListener;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Label;
import com.anand.mapapp.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class EmployeeActivity extends Activity implements AdapterView.OnItemClickListener {

    Point p;
    FrameLayout layout_EMP;
    PopupWindow popup;
    CustomAdapter adapter;
    EditText search;
    SwipeMenuListView lv;
    ListView labelList;

    SwipeMenuCreator creator=null;
    SwipeMenuItem favItem;
    SwipeMenuItem infoItem;

    private static final int ITEM_TYPE_FAVOURITE_EMP=4;

    int WiW, WiH, windowW, windowH, winW, winH, imgW, imgH, padding;
    int POP_PRESENT=0;
    int posIcon;
    int currentList=1;
    int state=0;
    int TYPE_NAME=1;
    String designationEmp;
    String text;

    ImageView swipe;

    int TYPE_DESIGNATION=2;
    int TYPE_NAME_DESIGNATION=3;
    int CLICKED=0;
    int images[];
    String designations[];

    DatabaseHandler handler;

    List<Label> arraylist = new ArrayList<Label>();
    List<Employee> employees;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        View layout = getLayoutInflater().inflate(R.layout.single_listview,null);
        swipe=(ImageView) layout.findViewById(R.id.swipe);
        search=(EditText) findViewById(R.id.searchView1);
        layout_EMP = (FrameLayout) findViewById(R.id.emp_lay);
        layout_EMP.getForeground().setAlpha(0);
        windowW = getWindowManager().getDefaultDisplay().getWidth();
        windowH = getWindowManager().getDefaultDisplay().getHeight();
        winW = windowW / 10;
        winH = windowH / 10;
        WiW = winW * 8;
        WiH = (int)(winH * 5.3);
        imgW = winW * 5;
        imgH = winH * 5;
        padding = winW;
        search.setHint("Search Here");

        images=new int[] { R.drawable.icon_pm, R.drawable.icon_a, R.drawable.icon_de, R.drawable.icon_ba,
                R.drawable.icon_tr, R.drawable.icon_in };
        designations=new String[]{"Project Manager","Architect","Designer","Business Analyst","Trainee","Intern"};

        lv =(SwipeMenuListView)findViewById(R.id.listView);
        lv.setOnItemClickListener(this);

        labelList=(ListView)findViewById(R.id.labelList);
        labelList.setOnItemClickListener(this);

        for (int i = 0; i < designations.length; i++)
        {
            Label wp = new Label(images[i], designations[i]);
            arraylist.add(wp);
        }

        handler = new DatabaseHandler(this);
        adapter=new CustomAdapter(this,3,arraylist,null,0,0);
        lv.setVisibility(View.GONE);
        labelList.setVisibility(View.VISIBLE);
        labelList.setAdapter(adapter);

        final Handler h = new Handler();
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                if (text.length() == 0 || text == "") {
                    if (CLICKED == 1) {
                        currentList = 2;
                        search.setHint(designationEmp);
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, arraylist.get(posIcon).getImage(), 0);
                        employees = handler.getEmployeesByName(TYPE_NAME_DESIGNATION, text, designationEmp);
                        adapter = new CustomAdapter(EmployeeActivity.this, 1, employees, null);
                        lv.setVisibility(View.VISIBLE);
                        labelList.setVisibility(View.GONE);
                        lv.setAdapter(adapter);
                        lv.setMenuCreator(creator);
                        state = 1;
                    } else {
                        currentList = 1;
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, 0, 0);
                        search.setHint("Search Here");
                        adapter = new CustomAdapter(EmployeeActivity.this, 3, arraylist,null,0,0);
                        labelList.setAdapter(adapter);
                        lv.setVisibility(View.GONE);
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

                    lv.setVisibility(View.VISIBLE);
                    labelList.setVisibility(View.GONE);
                    adapter = new CustomAdapter(EmployeeActivity.this, 1, employees, null);
                    lv.setAdapter(adapter);
                    lv.setMenuCreator(creator);
                    currentList = 2;
                }

            }
        };

        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                favItem = new SwipeMenuItem(getApplicationContext());
                favItem.setBackground(new ColorDrawable(Color.rgb(255, 178, 0)));
                favItem.setWidth(dp2px(60));
                favItem.setIcon(R.drawable.fav_inactive);
                menu.addMenuItem(favItem);

                infoItem = new SwipeMenuItem(getApplicationContext());
                infoItem.setBackground(new ColorDrawable(Color.rgb(0, 172, 192)));
                infoItem.setWidth(dp2px(60));
                infoItem.setIcon(R.drawable.info_icon);
                menu.addMenuItem(infoItem);
            }

        };


        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        adapter = new CustomAdapter(EmployeeActivity.this, 1, employees, null);
                        lv.setAdapter(adapter);
                        if (employees.get(position).isFavourite() == 0) {
                            Toast.makeText(getApplicationContext(), "Added To Favourites", Toast.LENGTH_SHORT).show();
                            employees.get(position).makeFavourite(1);
                            handler.setFavourite(employees.get(position).getId(), 1, ITEM_TYPE_FAVOURITE_EMP);
                        } else {
                            Toast.makeText(getApplicationContext(), "Removed From Favourites", Toast.LENGTH_SHORT).show();
                            employees.get(position).makeFavourite(0);
                            handler.setFavourite(employees.get(position).getId(), 0, ITEM_TYPE_FAVOURITE_EMP);
                        }
                        lv.setMenuCreator(creator);
                        break;
                    case 1:
                        showPopup(EmployeeActivity.this, position);
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


    private void showPopup(final Activity context, int position) {

        POP_PRESENT=1;
        layout_EMP.getForeground().setAlpha(400);
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if(currentList == 1) {
            posIcon=position;
            search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0,arraylist.get(posIcon).getImage(), 0);
            designationEmp=(arraylist.get(position).getName()).toLowerCase(Locale.getDefault());
            employees = handler.getEmployeesByName(TYPE_DESIGNATION, designationEmp, null);
            adapter=new CustomAdapter(EmployeeActivity.this, 1, employees, null);
            lv.setVisibility(View.VISIBLE);
            labelList.setVisibility(View.GONE);
            lv.setAdapter(adapter);
            currentList=2;
            CLICKED=1;
            state=1;
            lv.setMenuCreator(creator);
            search.setHint(designationEmp);
        }
        else {
            Intent returnIntent = new Intent(this, MainActivity.class);
            returnIntent.putExtra("act_val", 1);
            returnIntent.putExtra("id",(employees.get(position).getId()));
            returnIntent.putExtra("x_val",(employees.get(position).getX()));
            returnIntent.putExtra("y_val",(employees.get(position).getY()));
            startActivity(returnIntent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
                    lv.setVisibility(View.VISIBLE);
                    labelList.setVisibility(View.GONE);
                    lv.setMenuCreator(creator);
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