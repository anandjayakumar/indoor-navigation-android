package com.anand.mapapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    CustomAdapter adapter;
    SwipeMenuListView lv;
    SwipeMenuCreator creator=null,creator2=null;
    int currentList=1,state=0;
    int CLICKED=0;
    EditText search;

    String placeLabel;
    int TYPE_PLACE=1;
    int TYPE_LABEL=2;
    int TYPE_PLACE_LABEL=3;

    int images[];
    String places[];
    DatabaseHandler handler;
    List<Label> arraylist = new ArrayList<Label>();

    List<Place> plp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        search=(EditText) findViewById(R.id.searchView2);

        search.setHint("Search Here");

        images=new int[] { R.drawable.ic_meeting, R.drawable.ic_entry,R.drawable.ic_entry,
                R.drawable.ic_beverage, R.drawable.ic_toilet,
                R.drawable.ic_server, R.drawable.ic_eatery};
        places=new String[]{"Meeting","Entry","Exit","Beverages","Toilet","Server","Eatery"};

        // Locate the ListView in listview_main.xml
        lv = (SwipeMenuListView)findViewById(R.id.listView2);
        lv.setOnItemClickListener(this);

        for (int i = 0; i < places.length; i++)
        {
            Label wp = new Label(images[i], places[i]);
            arraylist.add(wp);
        }
        handler = new DatabaseHandler(this);
        adapter=new CustomAdapter(this,3,arraylist);
        lv.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
// TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if (text.length() == 0 || text == "") {
                    if (CLICKED == 1) {
                        currentList = 2;
                        search.setHint(placeLabel);
                        plp = handler.getPlacesByName(TYPE_PLACE_LABEL, text, placeLabel);
                        adapter = new CustomAdapter(PlaceActivity.this, 2, null, plp);
                        lv.setMenuCreator(creator);
                        lv.setAdapter(adapter);
                        adapter.filter(2, text);
                        state = 1;
                    } else {
                        currentList = 1;
                        search.setHint("Search Here");
                        adapter = new CustomAdapter(PlaceActivity.this, 3, arraylist);
                        lv.setMenuCreator(creator2);
                        lv.setAdapter(adapter);
                        adapter.filter(3, text);
                        state = 0;
                    }
                } else {
                    if (CLICKED == 1) {
                        plp = handler.getPlacesByName(TYPE_PLACE_LABEL, text, placeLabel);
                        state = 2;
                    } else {
                        plp = handler.getPlacesByName(TYPE_PLACE, text, null);
                        state = 1;
                    }
                    adapter = new CustomAdapter(PlaceActivity.this, 2, null, plp);
                    lv.setMenuCreator(creator);
                    lv.setAdapter(adapter);
                    currentList = 2;
                    adapter.filter(2, text);
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
                // create "open" item
                SwipeMenuItem favItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                favItem.setBackground(new ColorDrawable(Color.rgb(216, 219, 224)));
                // set item width
                favItem.setWidth(dp2px(90));
                // set item title
                favItem.setIcon(R.drawable.fav);
                // set item title fontsize

                // add to menu
                menu.addMenuItem(favItem);
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
                        Toast.makeText(getApplicationContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_place, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        if (currentList == 1) {
            placeLabel = (arraylist.get(position).getName()).toLowerCase(Locale.getDefault());
            plp = handler.getPlacesByName(TYPE_PLACE,placeLabel,null);
            adapter = new CustomAdapter(PlaceActivity.this, 2,null,plp);
            lv.setAdapter(adapter);
            currentList = 2;
            CLICKED=1;
            state=1;
            lv.setMenuCreator(creator);
            search.setHint(placeLabel);
            adapter.filter(5,placeLabel);

        }
        else {
            Intent returnIntent = new Intent(this, MainActivity.class);
            returnIntent.putExtra("act_val", 2);
            returnIntent.putExtra("name",
                    (plp.get(position).getName()));
            returnIntent.putExtra("x_val",
                    (plp.get(position).getX()));
            returnIntent.putExtra("y_val",
                    (plp.get(position).getY()));
            returnIntent.putExtra("images",
                    (plp.get(position).getPic()));
            startActivity(returnIntent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && state!=0) {
           if(state==2){
                search.setHint(placeLabel);
                if(search.getText()!=null){
                    search.setText(null);
                    CLICKED=1;
                }
                else{
                    CLICKED=0;
                }
                state=1;
            }
            else {
               CLICKED=0;
               search.setHint("Search Here");
               search.setText(null);
           }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}