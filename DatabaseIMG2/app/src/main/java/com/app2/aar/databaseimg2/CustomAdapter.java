package com.app2.aar.databaseimg2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_EMPLOYEE=1;
    private static final int ITEM_TYPE_PLACE=2;
    Context mContext;
    ArrayList<Employee> elist;
    ArrayList<Place> plist;
    List<Employee> empList = null;
    List<Place> plList = null;
    int val;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context context, int value, List<Employee> employeeList, List<Place> placeList) {
        val=value;
        switch(value) {
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
        }else {
           return 0;
        }
}

    @Override
    public Object getItem(int position) {
        if (val == ITEM_TYPE_EMPLOYEE) {
            return empList.get(position);
        }else if(val == ITEM_TYPE_PLACE) {
            return plList.get(position);
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
        TextView tv3;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ViewHolder holder;
        if (convertView == null) {
                convertView = inflater.inflate(R.layout.single_listview, null);
                holder = new ViewHolder();
                holder.tv=(TextView) convertView.findViewById(R.id.textv);
                holder.img=(ImageView) convertView.findViewById(R.id.imgv);
                holder.tv1=(TextView) convertView.findViewById(R.id.x);
                holder.tv2=(TextView) convertView.findViewById(R.id.y);
                holder.tv3=(TextView) convertView.findViewById(R.id.desg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(val == ITEM_TYPE_EMPLOYEE) {
            holder.tv.setText(empList.get(position).getEmployeeName());
            holder.img.setImageResource(empList.get(position).getEmployeeImg());
            holder.tv1.setText(String.valueOf(empList.get(position).getXval()));
            holder.tv2.setText(String.valueOf(empList.get(position).getYval()));
            holder.tv3.setText(empList.get(position).getEmployeeDesg());

        }else if(val == ITEM_TYPE_PLACE) {
            holder.tv.setText(plList.get(position).getPlaceName());
            holder.img.setImageResource(plList.get(position).getPic());
            holder.tv1.setText(String.valueOf(plList.get(position).getPXval()));
            holder.tv2.setText(String.valueOf(plList.get(position).getPYval()));
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
        if(id==ITEM_TYPE_EMPLOYEE) {
            empList.clear();
            if (charText.length() == 0) {
                empList.addAll(elist);
            } else {
                for (Employee wp : elist) {
                    if (wp.getEmployeeName().toLowerCase(Locale.getDefault())
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
                    if (pi.getPlaceName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        plList.add(pi);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


}