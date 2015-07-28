package com.anand.mapapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anand.mapapp.Activities.EmployeeActivity;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseSwipeAdapter {

    ArrayList<Employee> elist;
    ArrayList<Place> plist;
    List<Employee> empList = null;
    List<Place> plList = null;
    DatabaseHandler handler;
    SwipeLayout swipeLayout;
    int pos;

    private static final int ITEM_TYPE_EMPLOYEE=1;
    private static final int ITEM_TYPE_PLACE=2;

    private static final int ITEM_TYPE_LABEL=3;
    private static final int ITEM_TYPE_FAVOURITE=4;

    private static final int ITEM_TYPE_FAVOURITE_EMP=4;
    private static final int ITEM_TYPE_FAVOURITE_PLACE=5;

    int val;
    private Context mContext;

    private static LayoutInflater inflater=null;

    public ListViewAdapter(Context context, int value, List<Employee> employeeList, List<Place> placeList) {
        val=value;

        handler= new DatabaseHandler(context);
        switch (val){
            case 1:
                mContext = context;
                this.empList = employeeList;
                inflater = LayoutInflater.from(mContext);
                this.elist = new ArrayList<Employee>();
                this.elist.addAll(empList);
                break;
            case 2:
                mContext = context;
                this.plList= placeList;
                inflater = LayoutInflater.from(mContext);
                this.plist = new ArrayList<Place>();
                this.plist.addAll(plList);


        }
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    @Override
    public View generateView(final int position, ViewGroup parent) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, v.findViewById(R.id.swipeAction));
        swipeLayout.setClickToClose(true);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                TextView swipePos=(TextView)layout.findViewById(R.id.pos);
                pos= Integer.parseInt((String) swipePos.getText());
            }

            @Override
            public void onClose(SwipeLayout layout) {
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }


            @Override
            public void onStartClose(SwipeLayout layout) {
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.

            }
        });

        v.findViewById(R.id.fav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (val == ITEM_TYPE_EMPLOYEE) {
                    if (empList.get(pos).isFavourite() == 0) {
                        Toast.makeText(mContext, "Added To Favourites", Toast.LENGTH_SHORT).show();
                        empList.get(pos).makeFavourite(1);
                        handler.setFavourite(empList.get(pos).getId(), 1, ITEM_TYPE_FAVOURITE_EMP);
                    } else {
                        Toast.makeText(mContext, "Removed From Favourites", Toast.LENGTH_SHORT).show();
                        empList.get(pos).makeFavourite(0);
                        handler.setFavourite(empList.get(pos).getId(), 0, ITEM_TYPE_FAVOURITE_EMP);
                    }
                }
                else if (val == ITEM_TYPE_PLACE) {
                    if (plList.get(pos).isFavourite() == 0) {
                        Toast.makeText(mContext, "Added To Favourites", Toast.LENGTH_SHORT).show();
                        plList.get(pos).makeFavourite(1);
                        handler.setFavourite(plList.get(pos).getId(), 1, ITEM_TYPE_FAVOURITE_PLACE);
                    }
                    else {
                        Toast.makeText(mContext, "Removed From Favourites", Toast.LENGTH_SHORT).show();
                        plList.get(pos).makeFavourite(0);
                        handler.setFavourite(plList.get(pos).getId(), 0, ITEM_TYPE_FAVOURITE_PLACE);
                    }
                }
                closeItem(pos);
            }
        });

        v.findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (val == ITEM_TYPE_EMPLOYEE) {
                    if (mContext instanceof EmployeeActivity) {
                        ((EmployeeActivity) mContext).showPopup((EmployeeActivity) mContext, pos);
                    }
                }
                closeItem(pos);
            }
        });
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        TextView itemPos = (TextView) convertView.findViewById(R.id.pos);
        TextView tv = (TextView) convertView.findViewById(R.id.textv);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgv);
        ImageView swipe=(ImageView) convertView.findViewById(R.id.swipeIcon);
        LinearLayout infoLayout=(LinearLayout) convertView.findViewById(R.id.info);

        ImageView favIcon=(ImageView)convertView.findViewById(R.id.favIcon);
        ImageView infoIcon=(ImageView)convertView.findViewById(R.id.infoIcon);
        itemPos.setText(String.valueOf(position));

        if (val == ITEM_TYPE_EMPLOYEE) {
            tv.setText(empList.get(position).getName());
            img.setImageResource(empList.get(position).getPic());

            //infoIcon.setVisibility(View.VISIBLE);
           infoLayout.setVisibility(View.VISIBLE);
            if (empList.get(position).isFavourite() == 0) {
                favIcon.setImageResource(R.drawable.fav_inactive);
            }
            else  if (empList.get(position).isFavourite() == 1) {
                favIcon.setImageResource(R.drawable.fav_active);
            }
            infoIcon.setImageResource(R.drawable.info_icon);
        }
        else if (val == ITEM_TYPE_PLACE) {
            tv.setText(plList.get(position).getName());
            img.setImageResource(plList.get(position).getPic());
            infoLayout.setVisibility(View.GONE);
            if (plList.get(position).isFavourite() == 0) {
                favIcon.setImageResource(R.drawable.fav_inactive);
            }
            else  if (plList.get(position).isFavourite() == 1) {
                favIcon.setImageResource(R.drawable.fav_active);
            }
        }
    }


    @Override
    public int getCount() {
        if (val == ITEM_TYPE_EMPLOYEE) {
            return empList.size();
        }
        else  if (val == ITEM_TYPE_PLACE) {
            return plList.size();
        }
        else{
            return 0;
        }
    }
    @Override
    public Object getItem(int position) {
        if (val == ITEM_TYPE_EMPLOYEE) {
            return empList.get(position);
        }
        else  if (val == ITEM_TYPE_PLACE) {
            return plList.get(position);
        }
        else{
            return null;
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}