package com.app2.aar.databaseimg;

/**
 * Created by user on 22/6/15.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    List<Employee> empList;
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

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.single_listview, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textv);
        holder.img=(ImageView) rowView.findViewById(R.id.imgv);
        holder.tv.setText(empList.get(position).getEmployeeName());
        holder.img.setImageResource(empList.get(position).getEmployeeImg());
        //byte[] data=empList.get(position).getEmployeeImg();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //holder.img.setImageBitmap(bitmap);
        //holder.img.setImageResource(empList.get(position).getEmployeeImg());
        /*rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
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