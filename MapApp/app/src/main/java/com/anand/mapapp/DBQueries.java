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

    public void insertPlaces (PlaceinsertionCompletion listener) {

        Placeinsertiontask task = new Placeinsertiontask(listener);
        task.execute();
    }

    public void insertQR (QrinsertionCompletion listener) {

        Qrinsertiontask task = new Qrinsertiontask(listener);
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
            Employee em = new Employee();
            /*em.setId(1);
            em.setName("A1");
            em.setX(3);
            em.setY(4);
            em.setPic(101);
            em.setDesg("d1");
            empList.add(em);
            Employee em2 = new Employee();
            em2.setId(2);
            em2.setName("A2");
            em2.setX(5);
            em2.setY(6);
            em2.setPic(102);
            em2.setDesg("d2");
            empList.add(em2);*/
            em = new Employee(1,"Aarathi R",  2, 1,R.drawable.flag, "Software Engineer");
            empList.add(em);
            em = new Employee(2,"Anita Grace Daniel",  3, 2,R.drawable.flag, "Project Manager");
            empList.add(em);
            em = new Employee(3,"Anand Jaykumar",  2, 4,R.drawable.flag, "Software Engineer");
            empList.add(em);
            em = new Employee(4,"Elizabeth George",  3, 1,R.drawable.flag, "Designer");
            empList.add(em);
            em = new Employee(5,"Goutham Krishna",  4, 3,R.drawable.flag, "Intern");
            empList.add(em);
            em = new Employee(6,"Jithin V",  1, 2,R.drawable.flag, "Architect");
            empList.add(em);
            em = new Employee(7,"Jose Thomas",  5, 4,R.drawable.flag, "Trainee");
            empList.add(em);





            dbh.batchInsertEmployee(empList);
            Log.d("dbbb", "inserted");
            return null;
        }



    }

    public interface EmployeeinsertionCompletion {
        public void employeeinsertionCompleted();
    }

    class Placeinsertiontask extends AsyncTask<Void, Void, Void> {

        private PlaceinsertionCompletion listener;


        public Placeinsertiontask (PlaceinsertionCompletion listener) {
            this.listener = listener;

        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.placeinsertionCompleted();
        }

        @Override
        protected Void doInBackground(Void... params) {

            DatabaseHandler dbh = new DatabaseHandler(mContext);
            List<Place> placeList = new ArrayList<Place>();

            Place pl= new Place();

            placeList.add(pl);


            dbh.batchInsertPlace(placeList);
            Log.d("dbbbb", "inserted place " );

            return null;
        }



    }

    public interface PlaceinsertionCompletion {
        public void placeinsertionCompleted();
    }

    class Qrinsertiontask extends AsyncTask<Void, Void, Void> {

        private QrinsertionCompletion listener;


        public Qrinsertiontask (QrinsertionCompletion listener) {
            this.listener = listener;

        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.qrinsertionCompleted();
        }

        @Override
        protected Void doInBackground(Void... params) {

            DatabaseHandler dbh = new DatabaseHandler(mContext);
            List<QRcode> qrList = new ArrayList<QRcode>();

            QRcode qr= new QRcode();
            qr.setId(1);
            qr.setLink("Qr1");
            qr.setX(6);
            qr.setY(2);

            qrList.add(qr);

            QRcode qr2=new QRcode();
            qr2.setId(2);
            qr2.setLink("Qr2");
            qr2.setX(7);
            qr2.setY(8);

            qrList.add(qr2);

            dbh.batchInsertQRcode(qrList);
            Log.d("dbbbbb", "inserted qr " );

            return null;
        }



    }

    public interface QrinsertionCompletion {
        public void qrinsertionCompleted();
    }
}
