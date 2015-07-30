package com.anand.mapapp.Classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_LABEL=3;
    private static final int ITEM_TYPE_FAVOURITE=4;

    DatabaseHandler handler;


    String checkDesg;
    String checkEmail;



    Context mContext;

    ArrayList<Label> label_list;
    ArrayList<Favourite> flist;
    List<Label> label;

    List<Favourite> favList = null;
    int val;
    int imgW,imgH;

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
                this.label_list = new ArrayList<>();
                this.label_list.addAll(arraylist);
                break;
            case 4:
                mContext = context;
                this.favList = listFavourite;
                inflater = LayoutInflater.from(mContext);
                this.flist = new ArrayList<>();
                this.flist.addAll(favList);
                break;
        }

    }

    @Override
    public int getCount() {
       if(val==ITEM_TYPE_LABEL){
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
       if(val==ITEM_TYPE_LABEL){
            return label.get(position);
       }
       else if(val == ITEM_TYPE_FAVOURITE){
            return favList.get(position);
       }
       else {
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
                    convertView = inflater.inflate(R.layout.favourite_single_item, null);
                    holder.tv = (TextView) convertView.findViewById(R.id.nameFav);
                    holder.tv1 = (TextView) convertView.findViewById(R.id.desgFav);
                    holder.tv2 = (TextView) convertView.findViewById(R.id.emailFav);
                    holder.img = (ImageView) convertView.findViewById(R.id.imageFav);
                    holder.img.getLayoutParams().height=imgH; holder.img.getLayoutParams().width=imgW;
              convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
        }

        if (val==ITEM_TYPE_LABEL){
            holder.tv.setText(label.get(position).getName());
            holder.img.setImageResource(label.get(position).getImage());
        }
        else {
            if (val == ITEM_TYPE_FAVOURITE) {

                checkDesg = favList.get(position).getDetail();
                checkEmail = favList.get(position).getExtra();
                if (!checkEmail.equals("")) {
                    holder.tv2.setVisibility(View.VISIBLE);
                    holder.tv.setText(favList.get(position).getName());
                    holder.tv1.setText(favList.get(position).getDetail());


                    switch (checkDesg) {
                        case "Project Manager":
                            holder.tv1.setTextColor(Color.parseColor("#f05d55"));

                            break;
                        case "Architect":
                            holder.tv1.setTextColor(Color.parseColor("#fec300"));

                            break;
                        case "Designer":
                            holder.tv1.setTextColor(Color.parseColor("#479fde"));

                            break;
                        case "Business Analyst":
                            holder.tv1.setTextColor(Color.parseColor("#59747f"));

                            break;
                        case "Trainee":
                            holder.tv1.setTextColor(Color.parseColor("#8ac539"));

                            break;
                        case "Intern":
                            holder.tv1.setTextColor(Color.parseColor("#c69c6c"));

                            break;
                    }

                    holder.tv2.setText(favList.get(position).getExtra());
                    holder.img.setImageResource(favList.get(position).getPic());
                } else {
                    holder.tv.setText(favList.get(position).getName());
                    holder.tv1.setText(favList.get(position).getDetail());

                    switch (checkDesg) {
                        case "Meeting Room":
                            holder.tv1.setTextColor(Color.parseColor("#009889"));

                            break;
                        case "Entry/Exit":
                            holder.tv1.setTextColor(Color.parseColor("#df3d30"));

                            break;
                        case "Refreshment":
                            holder.tv1.setTextColor(Color.parseColor("#ffc402"));

                            break;
                        case "Washroom":
                            holder.tv1.setTextColor(Color.parseColor("#546e7b"));

                            break;
                        case "Server":
                            holder.tv1.setTextColor(Color.parseColor("#9974d4"));

                            break;
                    }
                    holder.tv2.setVisibility(View.GONE);
                    holder.img.setImageResource(favList.get(position).getPic());
                }

            }
        }
        return convertView;
    }
}