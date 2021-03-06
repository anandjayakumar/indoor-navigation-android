package com.anand.mapapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.anand.mapapp.Classes.CustomAdapter;
import com.anand.mapapp.Classes.Label;
import com.anand.mapapp.Classes.ListViewAdapter;
import com.anand.mapapp.Classes.Place;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceActivity extends Activity implements AdapterView.OnItemClickListener{

    CustomAdapter adapter;
    private ListView mListView;
    private ListViewAdapter mAdapter;
    ListView labelList;
    EditText search;
    DatabaseHandler handler;


    private static final int TYPE_PLACE=1;
    private static final int TYPE_LABEL=2;
    private static final int TYPE_PLACE_LABEL=3;

    int currentList=1,state=0;
    int CLICKED=0;
    int posIcon;
    int images[];
    String places[];
    String placeLabel;
    String text;

    List<Label> placeTypeList = new ArrayList<>();
    List<Place> plp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        search=(EditText) findViewById(R.id.searchView2);
        final TextView emptySearch=(TextView) findViewById(R.id.emptySearch);
        search.setHint("Search Here");

        images=new int[] { R.drawable.ic_meeting, R.drawable.ic_entry,
                R.drawable.ic_beverage, R.drawable.ic_toilet,
                R.drawable.ic_server, R.drawable.ic_eatery};
        places=new String[]{"Meeting Room","Entry/Exit","Refreshment","Washroom","Server"};

        mListView = (ListView) findViewById(R.id.listPlace);
        mListView.setOnItemClickListener(this);

        labelList=(ListView)findViewById(R.id.labelList2);
        labelList.setOnItemClickListener(this);

        for (int i = 0; i < places.length; i++)
        {
            Label wp = new Label(images[i], places[i]);
            placeTypeList.add(wp);
        }

        handler = new DatabaseHandler(this);
        adapter=new CustomAdapter(this,3,placeTypeList,null,0,0);
        mListView.setVisibility(View.GONE);
        labelList.setVisibility(View.VISIBLE);
        labelList.setAdapter(adapter);




        final Handler h = new Handler();
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                if (text.length() == 0 || text.equals("")) {
                    if (CLICKED == 1) {
                        currentList = 2;
                        search.setHint(placeLabel);
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, placeTypeList.get(posIcon).getImage(), 0);
                        plp = handler.getPlacesByName(TYPE_PLACE_LABEL, text, placeLabel);
                        if(plp.size()==0){
                            emptySearch.setText("No Match Found");
                        }
                        else{
                            emptySearch.setText(null);
                        }
                        mAdapter = new ListViewAdapter(PlaceActivity.this, 2, null, plp);
                        mListView.setVisibility(View.VISIBLE);
                        labelList.setVisibility(View.GONE);
                        mListView.setAdapter(mAdapter);
                        mAdapter.setMode(Attributes.Mode.Single);
                        state = 1;
                    }
                    else {
                        currentList = 1;
                        search.setHint("Search Here");
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, 0, 0);
                        adapter = new CustomAdapter(PlaceActivity.this, 3, placeTypeList,null,0,0);
                        mListView.setVisibility(View.GONE);
                        labelList.setVisibility(View.VISIBLE);
                        labelList.setAdapter(adapter);
                        state = 0;
                    }
                }
                else {
                    if (CLICKED == 1) {
                        plp = handler.getPlacesByName(TYPE_PLACE_LABEL, text, placeLabel);
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, placeTypeList.get(posIcon).getImage(), 0);
                        state = 2;
                    }
                    else {
                        plp = handler.getPlacesByName(TYPE_PLACE, text, null);
                        search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, 0, 0);
                        state = 1;
                    }
                    if(plp.size()==0){
                        emptySearch.setText("No Match Found");
                    }
                    else{
                        emptySearch.setText(null);
                    }
                    mAdapter = new ListViewAdapter(PlaceActivity.this, 2, null, plp);
                    mListView.setVisibility(View.VISIBLE);
                    labelList.setVisibility(View.GONE);
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);
                    currentList = 2;
                }
            }
        };
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                text = search.getText().toString().toLowerCase(Locale.getDefault());
                h.removeCallbacks(task);
                h.postDelayed(task, 400);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentList == 1) {
            posIcon=position;
            placeLabel = (placeTypeList.get(position).getName()).toLowerCase(Locale.getDefault());
            plp = handler.getPlacesByName(TYPE_LABEL, placeLabel, null);
            mAdapter = new ListViewAdapter(PlaceActivity.this, 2,null,plp);
            mListView.setAdapter(mAdapter);
            mAdapter.setMode(Attributes.Mode.Single);
            currentList = 2;
            CLICKED=1;
            state=1;
            mListView.setVisibility(View.VISIBLE);
            labelList.setVisibility(View.GONE);
            search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ictop_search, 0, placeTypeList.get(posIcon).getImage(), 0);
            search.setHint(placeLabel);
        }
        else {
            Intent returnIntent = new Intent(this, MainActivity.class);
            returnIntent.putExtra("act_val", 2);
            returnIntent.putExtra("id",
                    (plp.get(position).getId()));
            returnIntent.putExtra("x_val",
                    (plp.get(position).getX()));
            returnIntent.putExtra("y_val",
                    (plp.get(position).getY()));
            startActivity(returnIntent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
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
                mListView.setVisibility(View.VISIBLE);
                labelList.setVisibility(View.GONE);
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