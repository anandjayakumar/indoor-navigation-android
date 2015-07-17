package com.anand.mapapp.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anand.mapapp.Activities.EmployeeActivity;
import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_EMPLOYEE=1;
    private static final int ITEM_TYPE_PLACE=2;

    private static final int ITEM_TYPE_LABEL=3;
    private static final int ITEM_TYPE_FAVOURITE=4;


    private static final int ITEM_TYPE_FAVOURITE_EMP=4;

    private static final int ITEM_TYPE_FAVOURITE_PLACE=5;

    DatabaseHandler handler;


    String checkDesg;
    String checkEmail;



    Context mContext;
    ArrayList<Employee> elist;
    ArrayList<Place> plist;
    ArrayList<Label> label_list;
    ArrayList<Favourite> flist;
    List<Label> label;
    List<Employee> empList = null;
    List<Place> plList = null;
    List<Favourite> favList = null;
    int val;
    int imgW,imgH;

    String colors[];
    String colorsPlace[];

    private static LayoutInflater inflater=null;

    public CustomAdapter(Context context, int value, List<Label> arraylist,List<Favourite> listFavourite,int imageWidth, int imageHeight) {




        val=value;
        imgW=imageWidth;
        imgH=imageHeight;
        handler= new DatabaseHandler(context);

        switch(val) {
            case 3:
                mContext = context;
                this.label = arraylist;
                inflater = LayoutInflater.from(mContext);
                this.label_list = new ArrayList<Label>();
                this.label_list.addAll(arraylist);
                break;
            case 4:
                mContext = context;
                this.favList = listFavourite;
                inflater = LayoutInflater.from(mContext);
                this.flist = new ArrayList<Favourite>();
                this.flist.addAll(favList);
                break;
        }

    }

    public CustomAdapter(Context context, int value, List<Employee> employeeList, List<Place> placeList) {
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
    public int getCount() {
        if (val == ITEM_TYPE_EMPLOYEE) {
            return empList.size();
        }else if(val == ITEM_TYPE_PLACE) {
            return plList.size();
        }else if(val==ITEM_TYPE_LABEL){
            return label.size();
        }
        else if(val == ITEM_TYPE_FAVOURITE){
            return favList.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (val == ITEM_TYPE_EMPLOYEE ) {
            return empList.get(position);
        }else if(val == ITEM_TYPE_PLACE) {
            return plList.get(position);
        }else if(val==ITEM_TYPE_LABEL){
            return label.get(position);
        }
        else if(val == ITEM_TYPE_FAVOURITE){
            return favList.get(position);
        }else {
            return null;
        }
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }



    private class ViewHolder
    {
        TextView tv;
        ImageView img;
        TextView tv1;
        TextView tv2;

//        TextView tv3;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ViewHolder holder;
        if(val==ITEM_TYPE_LABEL) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.single_listview_label, null);
                holder.tv = (TextView) convertView.findViewById(R.id.textv1);
                holder.img = (ImageView) convertView.findViewById(R.id.imgv1);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        }
        else {
            if (convertView == null) {
                holder = new ViewHolder();
                if(val == ITEM_TYPE_FAVOURITE){
                    convertView = inflater.inflate(R.layout.favourite_single_item, null);
                    holder.tv = (TextView) convertView.findViewById(R.id.nameFav);
                    holder.tv1 = (TextView) convertView.findViewById(R.id.desgFav);
                    holder.tv2 = (TextView) convertView.findViewById(R.id.emailFav);
                    holder.img = (ImageView) convertView.findViewById(R.id.imageFav);
                    holder.img.getLayoutParams().height=imgH; holder.img.getLayoutParams().width=imgW;
                }
                else {
                    convertView = inflater.inflate(R.layout.single_listview, null);
                    holder.tv = (TextView) convertView.findViewById(R.id.textv);
                    holder.img = (ImageView) convertView.findViewById(R.id.imgv);

                   // holder.fav = (ImageView) convertView.findViewById(R.id.favButton);
                    //holder.info = (ImageView) convertView.findViewById(R.id.infoButton);
                }
              convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        }
        if (val==ITEM_TYPE_LABEL){
            holder.tv.setText(label.get(position).getName());
            holder.img.setImageResource(label.get(position).getImage());
        }
        else if(val == ITEM_TYPE_EMPLOYEE) {
            holder.tv.setText(empList.get(position).getName());
            holder.img.setImageResource(empList.get(position).getPic());
//            holder.info.setVisibility(View.VISIBLE);
//            holder.info.setTag(empList.get(position).getId());
//            holder.fav.setImageResource(R.drawable.fav);
//            holder.info.setImageResource(R.drawable.info1);
//            holder.fav.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (empList.get(position).isFavourite() == 0) {
//                        //favIcon_POSITION=position;
//                        Toast.makeText(mContext, "Added To Favourites", Toast.LENGTH_SHORT).show();
//                        empList.get(position).makeFavourite(1);
//                        handler.setFavourite(empList.get(position).getId(), 1, ITEM_TYPE_FAVOURITE_EMP);
//                    } else {
//                        Toast.makeText(mContext, "Already Added To Favourites", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });
//            holder.info.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                  //  empObject=new EmployeeActivity();
//                   // empObject.showPopup(position);
//                    Toast.makeText(mContext, ""+v.getTag(),Toast.LENGTH_SHORT).show();
//
//
//                    //empObject.showPopup(position);
//
//
//                }});

        }
        else if(val == ITEM_TYPE_FAVOURITE) {

            checkDesg = favList.get(position).getDetail();
            checkEmail = favList.get(position).getExtra();
            if (checkEmail != "") {
                holder.tv2.setVisibility(View.VISIBLE);
                holder.tv.setText(favList.get(position).getName());
                holder.tv1.setText(favList.get(position).getDetail());


                if (checkDesg.equals("Project Manager")) {
                    holder.tv1.setTextColor(Color.parseColor("#f05d55"));

                } else if (checkDesg.equals("Architect")) {
                    holder.tv1.setTextColor(Color.parseColor("#fec300"));

                } else if (checkDesg.equals("Designer")) {
                    holder.tv1.setTextColor(Color.parseColor("#479fde"));

                } else if (checkDesg.equals("Business Analyst")) {
                    holder.tv1.setTextColor(Color.parseColor("#59747f"));

                } else if (checkDesg.equals("Trainee")) {
                    holder.tv1.setTextColor(Color.parseColor("#8ac539"));

                } else if (checkDesg.equals("Intern")) {
                    holder.tv1.setTextColor(Color.parseColor("#c69c6c"));

                }

                holder.tv2.setText(favList.get(position).getExtra());
                holder.img.setImageResource(favList.get(position).getPic());
            } else {
                holder.tv.setText(favList.get(position).getName());
                holder.tv1.setText(favList.get(position).getDetail());

                if (checkDesg.equals("Meeting Room")) {
                    holder.tv1.setTextColor(Color.parseColor("#009889"));

                } else if (checkDesg.equals("Entry/Exit")) {
                    holder.tv1.setTextColor(Color.parseColor("#df3d30"));

                } else if (checkDesg.equals("Refreshment")) {
                    holder.tv1.setTextColor(Color.parseColor("#ffc402"));

                } else if (checkDesg.equals("Washroom")) {
                    holder.tv1.setTextColor(Color.parseColor("#546e7b"));

                } else if (checkDesg.equals("Server")) {
                    holder.tv1.setTextColor(Color.parseColor("#9974d4"));

                }
                holder.tv2.setVisibility(View.GONE);
                holder.img.setImageResource(favList.get(position).getPic());
            }
        }
        else if (val == ITEM_TYPE_PLACE) {
                // holder.info.setVisibility(View.GONE);
                holder.tv.setText(plList.get(position).getName());
                holder.img.setImageResource(plList.get(position).getPic());
//            holder.fav.setImageResource(R.drawable.fav);
//            holder.fav.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (empList.get(position).isFavourite() == 0) {
//                        //favIcon_POSITION=position;
//                        Toast.makeText(mContext, "Added to Favourites", Toast.LENGTH_SHORT).show();
//                        plList.get(position).makeFavourite(1);
//                        handler.setFavourite(plList.get(position).getId(),1,ITEM_TYPE_FAVOURITE_PLACE);
//                        }
//                    else {
//                        Toast.makeText(mContext, "Already Added To Favourites", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });
            }
        return convertView;
    }

//    // Filter Class
//    public void filter(int id, String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        if(id==ITEM_TYPE_LABEL) {
//            label.clear();
//            if (charText.length() == 0) {
//                label.addAll(label_list);
//            }
//        }
//        else if(id==ITEM_TYPE_EMPLOYEE) {
//            empList.clear();
//            if (charText.length() == 0) {
//                empList.addAll(elist);
//            } else {
//                for (Employee wp : elist) {
//                    if (wp.getName().toLowerCase(Locale.getDefault())
//                            .contains(charText)) {
//                        empList.add(wp);
//                    }
//                }
//            }
//        }
//        else if(id==ITEM_TYPE_PLACE) {
//            plList.clear();
//            if (charText.length() == 0) {
//                plList.addAll(plist);
//            } else {
//                for (Place pi : plist) {
//                    if (pi.getName().toLowerCase(Locale.getDefault())
//                            .contains(charText)) {
//                        plList.add(pi);
//                    }
//                }
//            }
//        }
//        else if(id==4){
//            empList.clear();
//            if (charText.length() == 0) {
//                empList.addAll(elist);
//            } else {
//                for (Employee wp : elist) {
//                    if (wp.getDesg().toLowerCase(Locale.getDefault())
//                            .contains(charText)) {
//                        empList.add(wp);
//                    }
//                }
//            }
//        }
//        else if(id==5){
//            plList.clear();
//            if (charText.length() == 0) {
//                plList.addAll(plist);
//            } else {
//                for (Place wp : plist) {
//                    if (wp.getName().toLowerCase(Locale.getDefault())
//                            .contains(charText)) {
//                        plList.add(wp);
//                    }
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }


}