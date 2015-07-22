package com.anand.mapapp.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anand.mapapp.Classes.CustomAdapter;
import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Favourite;
import com.anand.mapapp.Classes.Label;
import com.anand.mapapp.Classes.Place;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.GrowEffect;

import java.nio.channels.NonReadableChannelException;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.graphics.Color.TRANSPARENT;


public class FavouriteActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final int ITEM_TYPE_EMPLOYEE=1;
    private static final int ITEM_TYPE_PLACE=2;
    private static final int ITEM_TYPE_LABEL=3;
    private static final int ITEM_TYPE_FAVOURITE=4;
    private static final int ITEM_TYPE_FAVOURITE_EMP=4;
    private static final int ITEM_TYPE_FAVOURITE_PLACE=5;

    int windowW, windowH, winW, winH, imgW, imgH, padding;

    JazzyListView listFavourite;
    CustomAdapter adapter;
    DatabaseHandler handler=new DatabaseHandler(this);
    List<Favourite> favouriteList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        windowW = getWindowManager().getDefaultDisplay().getWidth();
        windowH = getWindowManager().getDefaultDisplay().getHeight();
        winW = windowW / 10;
        winH = windowH / 10;
        imgW = winW * 2;
        imgH = winH * 2;
        padding = winW;

        listFavourite = (JazzyListView) findViewById(R.id.listEmployee);
        listFavourite.setOnItemClickListener(this);

        favouriteList.addAll(handler.getFavEmployees());
        favouriteList.addAll(handler.getFavPlaces());

        listFavourite.setTransitionEffect(new GrowEffect());
        adapter = new CustomAdapter(this,ITEM_TYPE_FAVOURITE,null,favouriteList,imgW,imgH);
        listFavourite.setAdapter(adapter);

        listFavourite.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CustomAdapter clickAdapter = (CustomAdapter) listFavourite.getAdapter();
                removeItemAt(clickAdapter, position); // you need to implement this method
                clickAdapter.notifyDataSetChanged();
                return true;
            }
        });
        }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void removeItemAt(CustomAdapter customAdapter, final int position) {
        final int[] id = new int[1];
        final int[] type=new int[1];
        final String[] extra= new String[1];
        final int pos = position;
        final Button YesButton,NoButton;

        AlertDialog.Builder alertBox=new AlertDialog.Builder(this);
//        TextView titleText = new TextView(this);
//        titleText.setText("CONFIRM DELETION");
//        titleText.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
//        titleText.setGravity(Gravity.CENTER);
//        titleText.setPadding(0,15,0,0);
//        titleText.setTextAppearance(this, android.R.style.TextAppearance_Medium);
//        titleText.setTextColor(Color.parseColor("#1976d3"));
//

        alertBox.setTitle(R.string.confirmation);
        //alertBox.setCustomTitle(titleText);
        alertBox.setMessage("Are you sure you want to delete " + favouriteList.get(position).getName() + " ?");
        alertBox.setInverseBackgroundForced(true);
        alertBox.setNegativeButton("NO", null);
        alertBox.setPositiveButton("YES", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                id[0] = favouriteList.get(position).getId();
                extra[0] = favouriteList.get(position).getExtra();
                if (extra[0].equals("")) {
                    type[0] = ITEM_TYPE_FAVOURITE_PLACE;
                } else {
                    type[0] = ITEM_TYPE_FAVOURITE_EMP;
                }

                handler.setFavourite(id[0], 0, type[0]);
                favouriteList.remove(pos);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alertBox.create();

        dialog.show();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        YesButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        NoButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        YesButton.setTextAppearance(this,android.R.style.TextAppearance_Small);
        YesButton.setTextColor(Color.parseColor("#1976d3"));

        NoButton.setTextAppearance(this, android.R.style.TextAppearance_Small);
        NoButton.setTextColor(Color.parseColor("#1976d3"));

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        returnIntent.putExtra("act_val", (favouriteList.get(position).getType()));
        returnIntent.putExtra("id",
                (favouriteList.get(position).getId()));
        returnIntent.putExtra("x_val",
                (favouriteList.get(position).getX()));
        returnIntent.putExtra("y_val",
                (favouriteList.get(position).getY()));
        startActivity(returnIntent);
    }
}

