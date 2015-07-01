package com.anand.mapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    CustomAdapter adapter;
    ListView lv;
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
        lv = (ListView) findViewById(R.id.listView2);
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
                if(text.length()==0||text==""){
                    currentList=1;
                    search.setHint("Search Here");
                    adapter=new CustomAdapter(PlaceActivity.this,3,arraylist);
                    lv.setAdapter(adapter);
                    adapter.filter(3, text);
                    state=0;
                }
                else{
                    if(CLICKED==1){
                        plp=handler.getPlacesByName(TYPE_PLACE_LABEL,text,placeLabel);
                    }
                    else {
                        plp = handler.getPlacesByName(TYPE_PLACE,text,null);
                    }
                    adapter=new CustomAdapter(PlaceActivity.this,2,null,plp);
                    lv.setAdapter(adapter);
                    currentList=2;
                    adapter.filter(2,text);
                    state=1;
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

//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            public boolean onQueryTextChange(String newText) {
//                // this is your adapter that will be filtered
//                String text =newText.toLowerCase(Locale.getDefault());
//                if(text.length()==0){
//                    currentList=1;
//                    adapter=new CustomAdapter(PlaceActivity.this,3,arraylist);
//                    lv.setAdapter(adapter);
//                    adapter.filter(3, text);
//                    state=1;
//                }
//                else{
//                    plp = handler.getAllPlaces();
//                    adapter=new CustomAdapter(PlaceActivity.this,2,null,plp);
//                    lv.setAdapter(adapter);
//                    currentList=2;
//                    adapter.filter(2,text);
//                    state=1;
//                }
//                return true;
//            }
//
//            public boolean onQueryTextSubmit(String query) {
//                // TODO Auto-generated method stub
//                //Here u can get the value "query" which is entered in the search box.
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == id.action_settings) {
            return true;
        }*/

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
//        setResult(StartActivity.RESULT_OK, returnIntent);
//        finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && state!=0) {
           // adapter=new CustomAdapter(this,3,arraylist);
           // lv.setAdapter(adapter);
            search.setText(null);
            search.setHint("Search Here");
            CLICKED=0;
            //currentList=1;
            state=0;
            return true;

        }
        return super.onKeyDown(keyCode, event);

    }
}