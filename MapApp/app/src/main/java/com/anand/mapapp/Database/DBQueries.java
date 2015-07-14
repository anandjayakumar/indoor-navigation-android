package com.anand.mapapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Place;
import com.anand.mapapp.Classes.QRcode;
import com.anand.mapapp.R;

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
            Employee em;

            em = new Employee(1,"Aarathi R", 5,5, R.drawable.flag, "Software Engineer", "aarathirchandran@gmail.com");
            empList.add(em);
            em = new Employee(2,"Anita Grace Daniel", 6,5,R.drawable.flag, "Project Manager", "animoldaniel@gmail.com");
            empList.add(em);
            em = new Employee(3,"Anand Jayakumar",6,6,R.drawable.flag, "Intern", "anandjayakumar18@gmail.com");
            empList.add(em);
            em = new Employee(4,"Elizabeth George", 5,4,R.drawable.flag, "Designer", "elizabethgeorge94@gmail.com");
            empList.add(em);
            em = new Employee(5,"Goutham Krishna", 5,6,R.drawable.flag, "Intern", "gouthamkrishna.mr@gmail.com");
            empList.add(em);
            em = new Employee(6,"Jithin V", 4,5,R.drawable.flag, "Architect","jithinsrk14@gmail.com");
            empList.add(em);
            em = new Employee(7,"Jose Thomas", 4,4,R.drawable.flag, "Trainee","josethomas919@gmail.com");
            empList.add(em);
            em = new Employee(8,"Koushik CP", 6,4,R.drawable.flag, "Software Engineer","koushikcp94@gmail.com");
            empList.add(em);
            em = new Employee(9,"Lekha KP", 5,6,R.drawable.flag, "Developer"," lekhakp1994@gmail.com");
            empList.add(em);
            em = new Employee(10,"Manu Jose", 4,4,R.drawable.flag, "Trainee","manu.jose9645@gmail.com");
            empList.add(em);
            em = new Employee(11,"Roni Vincent", 5,6,R.drawable.flag, "Designer","vincentroni93@gmail.com");
            empList.add(em);
            em = new Employee(12,"Sona Thaj KN",  6,4,R.drawable.flag, "Software Engineer","sonathaj@gmail.com");
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

           pl = new Place(1,"Meeting Room 1",4,6,R.drawable.ic_meeting);
            placeList.add(pl);
           pl = new Place(2,"Meeting Room 2",5,5,R.drawable.ic_meeting);
            placeList.add(pl);
           pl = new Place(3,"Meeting Room 3",4,6,R.drawable.ic_meeting);
            placeList.add(pl);
           pl = new Place(4,"Toilet (Ladies)",5,4,R.drawable.ic_toilet);
            placeList.add(pl);
           pl = new Place(5,"Toilet (Gents)",6,6,R.drawable.ic_toilet);
            placeList.add(pl);
           pl = new Place(6,"Entry 1", 6, 4, R.drawable.ic_entry);
            placeList.add(pl);
           pl = new Place(7,"Entry 2", 6, 5, R.drawable.ic_entry);
            placeList.add(pl);
           pl = new Place(8,"Exit 1", 5, 6, R.drawable.ic_entry);
            placeList.add(pl);
           pl = new Place(9,"Exit 2",4, 5, R.drawable.ic_entry);
            placeList.add(pl);
           pl = new Place(10,"Beverages 1",5,6,R.drawable.ic_beverage);
            placeList.add(pl);
           pl = new Place(11,"Beverages 2",5,5,R.drawable.ic_beverage);
            placeList.add(pl);
           pl = new Place(12,"Server", 4, 4, R.drawable.ic_server);
            placeList.add(pl);
           pl = new Place(13,"Eatery 1", 4, 5, R.drawable.ic_eatery);
            placeList.add(pl);
           pl = new Place(14,"Eatery 2", 6, 4, R.drawable.ic_eatery);
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
            qr.setX(4);
            qr.setY(6);
            qr.setTag("tag1");
            qrList.add(qr);

            QRcode qr2=new QRcode();
            qr2.setId(2);
            qr2.setLink("QR_02");
            qr2.setX(5);
            qr2.setY(4);
            qr2.setTag("tag2");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(3);
            qr2.setLink("QR_03");
            qr2.setX(6);
            qr2.setY(5);
            qr2.setTag("tag3");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(4);
            qr2.setLink("QR_04");
            qr2.setX(5);
            qr2.setY(6);
            qr2.setTag("tag4");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(5);
            qr2.setLink("QR_05");
            qr2.setX(4);
            qr2.setY(5);
            qr2.setTag("tag5");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(6);
            qr2.setLink("QR_06");
            qr2.setX(6);
            qr2.setY(4);
            qr2.setTag("tag6");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(7);
            qr2.setLink("QR_07");
            qr2.setX(6);
            qr2.setY(6);
            qr2.setTag("tag7");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(8);
            qr2.setLink("QR_08");
            qr2.setX(4);
            qr2.setY(4);
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
