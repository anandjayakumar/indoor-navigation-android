package com.app2.aar.databaseimg;

/**
 * Created by user on 22/6/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter{
    Context mContext;
    ArrayList<Employee> elist;
    List<Employee> empList = null;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context,
                         List<Employee> employeeList) {
        mContext = context;
        this.empList= employeeList;
        inflater = LayoutInflater.from(mContext);
        this.elist = new ArrayList<Employee>();
        this.elist.addAll(empList);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return empList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class ViewHolder
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
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.single_listview, null);
        holder.tv=(TextView) convertView.findViewById(R.id.textv);
        holder.img=(ImageView) convertView.findViewById(R.id.imgv);
        holder.tv1=(TextView) convertView.findViewById(R.id.x);
        holder.tv2=(TextView) convertView.findViewById(R.id.y);
        holder.tv3=(TextView) convertView.findViewById(R.id.desg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(empList.get(position).getEmployeeName());
        holder.img.setImageResource(empList.get(position).getEmployeeImg());
        holder.tv1.setText(String.valueOf(empList.get(position).getXval()));
        holder.tv2.setText(String.valueOf(empList.get(position).getYval()));
        holder.tv3.setText(empList.get(position).getEmployeeDesg());
        //byte[] data=empList.get(position).getEmployeeImg();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //holder.img.setImageBitmap(bitmap);
        //holder.img.setImageResource(empList.get(position).getEmployeeImg());
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=((Activity) mContext).getIntent();;
                intent.putExtra("name",
                        (empList.get(position).getEmployeeName()));
                intent.putExtra("x_val",
                        (empList.get(position).getXval()));
                intent.putExtra("y_val",
                        (empList.get(position).getYval()));
                intent.putExtra("desg",
                        (empList.get(position).getEmployeeDesg()));
                intent.putExtra("images",
                        (empList.get(position).getEmployeeImg()));
                ((Activity) v.getContext()).setResult(StartActivity.RESULT_OK, intent);
                ((Activity)v.getContext()).finish();
                //setResult(intent);
                //finish();
            }
        });
        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
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
        notifyDataSetChanged();
    }


}