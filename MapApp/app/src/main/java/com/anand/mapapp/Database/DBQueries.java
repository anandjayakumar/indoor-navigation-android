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

            em = new Employee(1,"Aarathi R", 250,250, R.drawable.flag, "Software Engineer", "aarathirchandran@gmail.com", 0);
            empList.add(em);
            em = new Employee(2,"Anita Grace Daniel", 400,250,R.drawable.flag, "Project Manager", "animoldaniel@gmail.com", 0);
            empList.add(em);
            em = new Employee(3,"Anand Jayakumar",400,400,R.drawable.flag, "Project Manager", "anandjayakumar18@gmail.com", 0);
            empList.add(em);
            em = new Employee(4,"Elizabeth George", 250,350,R.drawable.flag, "Designer", "elizabethgeorge94@gmail.com", 0);
            empList.add(em);
            em = new Employee(5,"Goutham Krishna", 250,400,R.drawable.flag, "Intern", "gouthamkrishna.mr@gmail.com", 0);
            empList.add(em);
            em = new Employee(6,"Jithin V", 350,250,R.drawable.flag, "Architect","jithinsrk14@gmail.com", 0);
            empList.add(em);
            em = new Employee(7,"Jose Thomas", 400,350,R.drawable.flag, "Trainee","josethomas919@gmail.com", 0);
            empList.add(em);
            em = new Employee(8,"Koushik CP", 400,350,R.drawable.flag, "Software Engineer","koushikcp94@gmail.com", 0);
            empList.add(em);
            em = new Employee(9,"Lekha KP", 250,400,R.drawable.flag, "Business Analyst"," lekhakp1994@gmail.com", 0);
            empList.add(em);
            em = new Employee(10,"Manu Jose", 350,350,R.drawable.flag, "Trainee","manu.jose9645@gmail.com", 0);
            empList.add(em);
            em = new Employee(11,"Roni Vincent", 250,400,R.drawable.flag, "Business Analyst","vincentroni93@gmail.com", 0);
            empList.add(em);
            em = new Employee(12,"Sona Thaj KN",  350,350,R.drawable.flag, "Business Analyst","sonathaj@gmail.com", 0);
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

           pl = new Place(101,"Meeting Room 1","Meeting Room",350,350,R.drawable.ic_meeting,0);
            placeList.add(pl);
           pl = new Place(102,"Meeting Room 2","Meeting Room",250,250,R.drawable.ic_meeting,0);
            placeList.add(pl);
           pl = new Place(103,"Meeting Room 3","Meeting Room",350,350,R.drawable.ic_meeting,0);
            placeList.add(pl);
           pl = new Place(104,"Toilet (Ladies)","Toilet",250,350,R.drawable.ic_toilet,0);
            placeList.add(pl);
           pl = new Place(105,"Toilet (Gents)","Toilet",350,350,R.drawable.ic_toilet,0);
            placeList.add(pl);
           pl = new Place(106,"Entry 1","Entry", 350, 350, R.drawable.ic_entry,0);
            placeList.add(pl);
           pl = new Place(107,"Entry 2", "Entry",350, 250, R.drawable.ic_entry,0);
            placeList.add(pl);
           pl = new Place(108,"Exit 1","Exit", 250, 350, R.drawable.ic_entry,0);
            placeList.add(pl);
           pl = new Place(109,"Exit 2","Exit",350, 250, R.drawable.ic_entry,0);
            placeList.add(pl);
           pl = new Place(1010,"Beverages 1","Beverages",250,350,R.drawable.ic_beverage,0);
            placeList.add(pl);
           pl = new Place(1011,"Beverages 2","Beverages",250,250,R.drawable.ic_beverage,0);
            placeList.add(pl);
           pl = new Place(1012,"Server","Server", 350, 350, R.drawable.ic_server,0);
            placeList.add(pl);
           pl = new Place(1013,"Eatery 1","Eatery", 350, 250, R.drawable.ic_eatery,0);
            placeList.add(pl);
           pl = new Place(1014,"Eatery 2","Eatery", 350, 350, R.drawable.ic_eatery,0);
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
            qr.setX(400);
            qr.setY(600);
            qr.setTag("Tag1");
            qrList.add(qr);

            QRcode qr2=new QRcode();
            qr2.setId(2);
            qr2.setLink("QR_02");
            qr2.setX(500);
            qr2.setY(400);
            qr2.setTag("Tag2");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(3);
            qr2.setLink("QR_03");
            qr2.setX(400);
            qr2.setY(500);
            qr2.setTag("Tag3");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(4);
            qr2.setLink("QR_04");
            qr2.setX(400);
            qr2.setY(600);
            qr2.setTag("Tag4");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(5);
            qr2.setLink("QR_05");
            qr2.setX(400);
            qr2.setY(500);
            qr2.setTag("Tag5");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(6);
            qr2.setLink("QR_06");
            qr2.setX(400);
            qr2.setY(400);
            qr2.setTag("Tag6");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(7);
            qr2.setLink("QR_07");
            qr2.setX(300);
            qr2.setY(300);
            qr2.setTag("Tag7");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(8);
            qr2.setLink("QR_08");
            qr2.setX(350);
            qr2.setY(400);
            qr2.setTag("Tag8");
            qrList.add(qr2);

            dbh.batchInsertQRcode(qrList);


            return null;
        }



    }

    public interface QRinsertionCompletion {
        public void qrinsertionCompleted();
    }
}
