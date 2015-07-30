package com.anand.mapapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.anand.mapapp.Classes.Employee;
import com.anand.mapapp.Classes.Place;
import com.anand.mapapp.Classes.QRcode;
import com.anand.mapapp.R;

import java.util.ArrayList;
import java.util.List;

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
            List<Employee> empList = new ArrayList<>();
            Employee em;

            em = new Employee(1,"Aarathi R", 308,35, R.drawable.flag, "Project Manager", "aarathirchandran@gmail.com", 0);
            empList.add(em);
            em = new Employee(2,"Anita Grace Daniel", 415,35 ,R.drawable.flag2, "Project Manager", "animoldaniel@gmail.com", 0);
            empList.add(em);
            em = new Employee(3,"Anand Jayakumar",304,111 ,R.drawable.flag3, "Intern", "anandjayakumar18@gmail.com", 0);
            empList.add(em);
            em = new Employee(4,"Elizabeth George", 342,176 ,R.drawable.flag, "Designer", "elizabethgeorge94@gmail.com", 0);
            empList.add(em);
            em = new Employee(5,"Goutham Krishna", 299,312,R.drawable.flag4, "Intern", "gouthamkrishna.mr@gmail.com", 0);
            empList.add(em);
            em = new Employee(6,"Jithin V", 421,111,R.drawable.flag3, "Architect","jithinsrk14@gmail.com", 0);
            empList.add(em);
            em = new Employee(7,"Jose Thomas", 393,145 ,R.drawable.flag4, "Trainee","josethomas919@gmail.com", 0);
            empList.add(em);
            em = new Employee(8,"Koushik CP", 422,212 ,R.drawable.flag3, "Architect","koushikcp94@gmail.com", 0);
            empList.add(em);
            em = new Employee(9,"Lekha KP", 364,313,R.drawable.flag2, "Business Analyst"," lekhakp1994@gmail.com", 0);
            empList.add(em);
            em = new Employee(10,"Manu Jose", 472,65,R.drawable.flag4, "Trainee","manu.jose9645@gmail.com", 0);
            empList.add(em);
            em = new Employee(11,"Roni Vincent", 472,167,R.drawable.flag3, "Business Analyst","vincentroni93@gmail.com", 0);
            empList.add(em);
            em = new Employee(12,"Sona Thaj KN",  472,268,R.drawable.flag, "Project Manager","sonathaj@gmail.com", 0);
            empList.add(em);
            em = new Employee(13,"Gayathri G B", 386,389,R.drawable.flag2, "Project Manager","gaya3gb@gmail.com", 0);
            empList.add(em);
            em = new Employee(14,"Kamya Krishna", 349,435,R.drawable.flag, "Business Analyst","mekamya94@gmail.com", 0);
            empList.add(em);
            em = new Employee(15,"Akhila Jose", 384,483,R.drawable.flag2, "Trainee","akhijo93@gmail.com", 0);
            empList.add(em);
            em = new Employee(16,"Nandana Nair", 472,379,R.drawable.flag, "Architect","nandanaannedath@gmail.com", 0);
            empList.add(em);
            em = new Employee(17,"NithinDev N", 472,483,R.drawable.flag4, "Project Manager","nithindevnarayanan@gmail.com", 0);
            empList.add(em);
            em = new Employee(18,"Shanthi Shankaran", 383,526,R.drawable.flag2, "Designer","shanthishankaran@gmail.com", 0);
            empList.add(em);
            em = new Employee(19,"Ammu P S", 430,570,R.drawable.flag, "Designer","ammuxxx@gmail.com", 0);
            empList.add(em);
            em = new Employee(20,"Anju R", 354,615,R.drawable.flag2, "Trainee","anjubtech18@gmail.com", 0);
            empList.add(em);
            em = new Employee(21,"Anju Raveendran", 472,562,R.drawable.flag, "Intern","anjur93@gmail.com", 0);
            empList.add(em);
            em = new Employee(22,"Sukanya K M", 472,629,R.drawable.flag2, "Trainee","sukanyakm@gmail.com", 0);
            empList.add(em);

            dbh.batchInsertEmployee(empList);

            return null;
        }



    }

    public interface EmployeeinsertionCompletion {
        void employeeinsertionCompleted();
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
            List<Place> placeList = new ArrayList<>();

            Place pl;

           pl = new Place(101,"Conference Room","Meeting Room",207,160,R.drawable.ic_meeting,0);
            placeList.add(pl);
           pl = new Place(102,"Seminar Hall","Meeting Room",400,755,R.drawable.ic_meeting,0);
            placeList.add(pl);
           pl = new Place(103,"Washroom (Ladies)","Washroom",134,160,R.drawable.ic_toilet,0);
            placeList.add(pl);
           pl = new Place(104,"Washroom (Gents)","Washroom",54,160,R.drawable.ic_toilet,0);
            placeList.add(pl);
           pl = new Place(105,"Entry/Exit 1","Entry/Exit",284,816, R.drawable.ic_entry,0);
            placeList.add(pl);
           pl = new Place(106,"Entry/Exit 2", "Entry/Exit",257,634, R.drawable.ic_entry,0);
            placeList.add(pl);
           pl = new Place(107,"Drinking Water","Refreshment",76,308,R.drawable.ic_beverage,0);
            placeList.add(pl);
           pl = new Place(108,"Server Room","Server", 265,160, R.drawable.ic_server,0);
            placeList.add(pl);


            dbh.batchInsertPlace(placeList);


            return null;
        }



    }

    public interface PlaceinsertionCompletion {
         void placeinsertionCompleted();
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
        public Void doInBackground(Void... params) {

            DatabaseHandler dbh = new DatabaseHandler(mContext);
            List<QRcode> qrList = new ArrayList<>();

            QRcode qr= new QRcode();
            qr.setId(1);
            qr.setLink("QR_01");
            qr.setX(283);
            qr.setY(800);
            qr.setTag("Entry/Exit 1");
            qrList.add(qr);

            QRcode qr2=new QRcode();
            qr2.setId(2);
            qr2.setLink("QR_02");
            qr2.setX(294);
            qr2.setY(771);
            qr2.setTag("Seminar Hall");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(3);
            qr2.setLink("QR_03");
            qr2.setX(298);
            qr2.setY(648);
            qr2.setTag("Workplace 1");
            qrList.add(qr2);
            qr2=new QRcode();

            qr2.setId(4);
            qr2.setLink("QR_04");
            qr2.setX(271);
            qr2.setY(619);
            qr2.setTag("Entry/Exit 2");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(5);
            qr2.setLink("QR_05");
            qr2.setX(292);
            qr2.setY(369);
            qr2.setTag("Workplace 2");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(6);
            qr2.setLink("QR_06");
            qr2.setX(265);
            qr2.setY(304);
            qr2.setTag("Workplace 3");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(7);
            qr2.setLink("QR_07");
            qr2.setX(261);
            qr2.setY(262);
            qr2.setTag("Server Room");
            qrList.add(qr2);
            qr2=new QRcode();
            qr2.setId(8);
            qr2.setLink("QR_08");
            qr2.setX(219);
            qr2.setY(262);
            qr2.setTag("Meeting Room");
            qrList.add(qr2);

            qr2=new QRcode();
            qr2.setId(9);
            qr2.setLink("QR_09");
            qr2.setX(150);
            qr2.setY(262);
            qr2.setTag("Washroom 1");
            qrList.add(qr2);

            qr2=new QRcode();
            qr2.setId(10);
            qr2.setLink("QR_10");
            qr2.setX(73);
            qr2.setY(262);
            qr2.setTag("Washroom 2");
            qrList.add(qr2);

            dbh.batchInsertQRcode(qrList);


            return null;
        }
    }

    public interface QRinsertionCompletion {
         void qrinsertionCompleted();
    }
}
