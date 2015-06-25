package com.app2.aar.databaseimg2;

import android.os.AsyncTask;

/**
 * Created by user on 25/6/15.
 */
public class DBQueries {

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

            return null;
        }


    }

   public interface EmployeeinsertionCompletion {
        public void employeeinsertionCompleted();
    }
}
