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

    public void insertQR (QRinsertionCompletion listener) {

        QRinsertiontask task = new QRinsertiontask(listener);
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

            em = new Employee(1,"Aarathi R",  2, 1,R.drawable.flag, "Software Engineer");
            empList.add(em);
            em = new Employee(2,"Anita Grace Daniel",  3, 2,R.drawable.flag, "Project Manager");
            empList.add(em);
            em = new Employee(3,"Anand Jaykumar",  2, 4,R.drawable.flag, "Intern");
            empList.add(em);
            em = new Employee(4,"Elizabeth George",  3, 1,R.drawable.flag, "Designer");
            empList.add(em);
            em = new Employee(5,"Goutham Krishna",  4, 3,R.drawable.flag, "Intern");
            empList.add(em);
            em = new Employee(6,"Jithin V",  1, 2,R.drawable.flag, "Architect");
            empList.add(em);
            em = new Employee(7,"Jose Thomas",  5, 4,R.drawable.flag, "Trainee");
            empList.add(em);
            em = new Employee(8,"Koushik CP",  3, 3,R.drawable.flag, "Software Engineer");
            empList.add(em);
            em = new Employee(9,"Lekha KP",  2, 4,R.drawable.flag, "Developer");
            empList.add(em);
            em = new Employee(10,"Manu Jose",  5, 3,R.drawable.flag, "Trainee");
            empList.add(em);
            em = new Employee(11,"Roni Vincent",  1, 4,R.drawable.flag, "Designer");
            empList.add(em);
            em = new Employee(12,"Sona Thaj KN",  5, 2,R.drawable.flag, "Trainee");
            empList.add(em);

            dbh.batchInsertEmployee(empList);

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

            Place pl;

           pl = new Place(1,"Meeting Room 1",1,3,R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(2,"Meeting Room 2",3,2,R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(3,"Meeting Room 3",5,2,R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(4,"Toilet (Ladies)",2,3,R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(5,"Toilet (Gents)",2,3,R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(6,"Entry 1", 4, 3, R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(7,"Entry 2", 5, 4, R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(8,"Exit 1", 1, 4, R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(9,"Exit 2", 2, 4, R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(10,"Beverages 1",2,1,R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(11,"Beverages 2",5,1,R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(12,"Server", 3, 3, R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(13,"Eatery 1", 3, 3, R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(14,"Eatery 2", 3, 3, R.drawable.ic_eatery);
            placeList.add(pl);


            dbh.batchInsertPlace(placeList);


            return null;
        }



    }

    public interface PlaceinsertionCompletion {
        public void placeinsertionCompleted();
    }

    class QRinsertiontask extends AsyncTask<Void, Void, Void> {

        private QRinsertionCompletion listener;


        public QRinsertiontask (QRinsertionCompletion listener) {
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
            qr.setLink("QR_01");
            qr.setX(6);
            qr.setY(2);
            qr.setTag("tag1");
            qrList.add(qr);

            QRcode qr2=new QRcode();
            qr2.setId(2);
            qr2.setLink("QR_02");
            qr2.setX(7);
            qr2.setY(8);
            qr2.setTag("tag2");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(3);
            qr2.setLink("QR_03");
            qr2.setX(7);
            qr2.setY(8);
            qr2.setTag("tag3");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(4);
            qr2.setLink("QR_04");
            qr2.setX(7);
            qr2.setY(8);
            qr2.setTag("tag4");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(5);
            qr2.setLink("QR_05");
            qr2.setX(7);
            qr2.setY(8);
            qr2.setTag("tag5");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(6);
            qr2.setLink("QR_06");
            qr2.setX(7);
            qr2.setY(8);
            qr2.setTag("tag6");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(7);
            qr2.setLink("QR_07");
            qr2.setX(7);
            qr2.setY(8);
            qr2.setTag("tag7");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(8);
            qr2.setLink("QR_08");
            qr2.setX(5);
            qr2.setY(5);
            qr2.setTag("tag8");
            qrList.add(qr2);

            dbh.batchInsertQRcode(qrList);


            return null;
        }



    }

    public interface QRinsertionCompletion {
        public void qrinsertionCompleted();
    }
}
