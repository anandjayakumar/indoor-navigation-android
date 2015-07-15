package com.anand.mapapp.Classes;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anand.mapapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_EMPLOYEE=1;
    private static final int ITEM_TYPE_PLACE=2;

    private static final int ITEM_TYPE_LABEL=3;
    private static final int ITEM_TYPE_FAVOURITE=4;


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
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context context, int value, List<Label> arraylist,List<Favourite> listFavourite,int imageWidth, int imageHeight) {


        val=value;
        imgW=imageWidth;
        imgH=imageHeight;

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
                }
//                holder.tv1 = (TextView) convertView.findViewById(R.id.x);
//                holder.tv2 = (TextView) convertView.findViewById(R.id.y);
//                holder.tv3 = (TextView) convertView.findViewById(R.id.desg);
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
//            holder.tv1.setText(String.valueOf(empList.get(position).getX()));
//            holder.tv2.setText(String.valueOf(empList.get(position).getY()));
//            holder.tv3.setText(empList.get(position).getDesg());

        }
        else if(val == ITEM_TYPE_FAVOURITE) {
            holder.tv.setText(favList.get(position).getName());
            holder.tv1.setText(favList.get(position).getDetail());
            holder.tv2.setText(favList.get(position).getExtra());
            holder.img.setImageResource(favList.get(position).getPic());
//            holder.tv1.setText(String.valueOf(empList.get(position).getX()));
//            holder.tv2.setText(String.valueOf(empList.get(position).getY()));
//            holder.tv3.setText(empList.get(position).getDesg());

        }
//            holder.tv1.setText(String.valueOf(empList.get(position).getX()));
//            holder.tv2.setText(String.valueOf(empList.get(position).getY()));
//            holder.tv3.setText(empList.get(position).getDesg());
        else if(val == ITEM_TYPE_PLACE) {
            holder.tv.setText(plList.get(position).getName());
            holder.img.setImageResource(plList.get(position).getPic());
//            holder.tv1.setText(String.valueOf(plList.get(position).getX()));
//            holder.tv2.setText(String.valueOf(plList.get(position).getY()));
        }


//holder.img.setImageResource(empList.get(position).getEmployeeImg());
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent intent=((Activity) mContext).getIntent();;
//                intent.putExtra("name",
//                        (empList.get(position).getEmployeeName()));
//                intent.putExtra("x_val",
//                        (empList.get(position).getXval()));
//                intent.putExtra("y_val",
//                        (empList.get(position).getYval()));
//                intent.putExtra("desg",
//                        (empList.get(position).getEmployeeDesg()));
//                intent.putExtra("images",
//                        (empList.get(position).getEmployeeImg()));
//                ((Activity) v.getContext()).setResult(StartActivity.RESULT_OK, intent);
//                ((Activity)v.getContext()).finish();
//                //setResult(intent);
//                //finish();
//            }
//        });

        return convertView;
    }

    // Filter Class
    public void filter(int id, String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if(id==ITEM_TYPE_LABEL) {
            label.clear();
            if (charText.length() == 0) {
                label.addAll(label_list);
            }
        }
        else if(id==ITEM_TYPE_EMPLOYEE) {
            empList.clear();
            if (charText.length() == 0) {
                empList.addAll(elist);
            } else {
                for (Employee wp : elist) {
                    if (wp.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        empList.add(wp);
                    }
                }
            }
        }
        else if(id==ITEM_TYPE_PLACE) {
            plList.clear();
            if (charText.length() == 0) {
                plList.addAll(plist);
            } else {
                for (Place pi : plist) {
                    if (pi.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        plList.add(pi);
                    }
                }
            }
        }
        else if(id==4){
            empList.clear();
            if (charText.length() == 0) {
                empList.addAll(elist);
            } else {
                for (Employee wp : elist) {
                    if (wp.getDesg().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        empList.add(wp);
                    }
                }
            }
        }
        else if(id==5){
            plList.clear();
            if (charText.length() == 0) {
                plList.addAll(plist);
            } else {
                for (Place wp : plist) {
                    if (wp.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        plList.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


}