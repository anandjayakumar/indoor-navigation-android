package com.app2.aar.databaseimg2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;
import java.util.Locale;

public class PlaceActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    CustomAdapter adapter;
    EditText editsearch;
    SearchView search;
    List<Place> plp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place);
        search=(SearchView) findViewById(R.id.searchView2);
        search.setQueryHint("Search Here");

        DatabaseHandler handler = new DatabaseHandler(this);

        plp= handler.getAllPlaces();
        adapter=new CustomAdapter(this,2,null,plp);
        ListView lv=(ListView) findViewById(R.id.listView2);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                String text =newText.toLowerCase(Locale.getDefault());
                adapter.filter(2,text);
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
        Intent returnIntent = new Intent();
        returnIntent.putExtra("name",
                (plp.get(position).getPlaceName()));
        returnIntent.putExtra("x_val",
                (plp.get(position).getPXval()));
        returnIntent.putExtra("y_val",
                (plp.get(position).getPYval()));
        returnIntent.putExtra("images",
                (plp.get(position).getPic()));
        setResult(StartActivity.RESULT_OK, returnIntent);
        finish();
    }
}