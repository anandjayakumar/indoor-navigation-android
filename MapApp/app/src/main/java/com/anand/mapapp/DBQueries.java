package com.anand.mapapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 25/6/15.
 */
public class DBQueries {

    Context mContext;

    public DBQueries(Context c) {
        mContext=c;
    }

    public void insertEmployees (EmployeeinsertionCompletion listener) {

        Employeeinsertiontask task = new Employeeinsertiontask(listener);
        task.execute();
    }

    class Employeeinsertiontask extends AsyncTask<Void, Void, Void> {

       private EmployeeinsertionCompletion listener;


       public Employeeinsertiontask (EmployeeinsertionCompletion listener) {
           this.listener = listener;

       }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.employeeinsertionCompleted();
        }

        @Override
        protected Void doInBackground(Void... params) {

            DatabaseHandler dbh = new DatabaseHandler(mContext);
            List<Employee> empList = new ArrayList<Employee>();
            Employee qr = new Employee();
            qr.setId(1);
            qr.setName("A1");
            qr.setX(3);
            qr.setY(4);
            empList.add(qr);
            Employee qr2 = new Employee();
            qr2.setId(2);
            qr2.setName("A2");
            qr2.setX(5);
            qr2.setY(6);
            empList.add(qr2);

            dbh.batchInsertEmployee(empList);
            Log.d("dbbb", "inserted");
            return null;
        }



    }

   public interface EmployeeinsertionCompletion {
        public void employeeinsertionCompleted();
    }
}
