package com.anand.mapapp;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {


    int TYPE_NAME=1;
    int TYPE_PLACE=1;
    int TYPE_LABEL=2;
    int TYPE_DESIGNATION=2;
    int TYPE_NAME_DESIGNATION=3;
    int TYPE_PLACE_LABEL=3;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "MAIN_DB";

    // Table name
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String TABLE_PLACE = "place";
    private static final String TABLE_LOG = "log";
    private static final String TABLE_QR = "qr";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LINK = "link";
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESIGNATION = "designation";
    private static final String KEY_PIC = "pic";
    private static final String KEY_TAG = "tag";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_X + " INTEGER,"
                + KEY_Y + " INTEGER," + KEY_PIC + " INTEGER," +KEY_DESIGNATION + " TEXT" + ")";
        db.execSQL(CREATE_EMPLOYEE_TABLE);

        String CREATE_PLACE_TABLE = "CREATE TABLE " + TABLE_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_X + " INTEGER,"
                + KEY_Y + " INTEGER," + KEY_PIC + " INTEGER" + ")";
        db.execSQL(CREATE_PLACE_TABLE);

        String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LINK + " TEXT,"
                + KEY_DATE + " INTEGER," + KEY_TIME + " INTEGER" + ")";
        db.execSQL(CREATE_LOG_TABLE);

        String CREATE_QR_TABLE = "CREATE TABLE " + TABLE_QR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LINK + " TEXT," + KEY_X + " INTEGER,"
                + KEY_Y + " INTEGER," + KEY_TAG + " TEXT" + ")";
        db.execSQL(CREATE_QR_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == 1 && newVersion == 2) {
            onCreate(db);
        }
    }

    void insertLog(Timelog tlog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,tlog.getId());
        cv.put(KEY_LINK,tlog.getLink());
        cv.put(KEY_DATE, tlog.getDate());
        cv.put(KEY_TIME, tlog.getTime());
        db.insert(TABLE_LOG, null, cv);
        db.close();

    }


    void batchInsertEmployee(List<Employee> EmployeeList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for(int i=0, j=EmployeeList.size(); i<j; i++) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID,EmployeeList.get(i).getId());
            cv.put(KEY_NAME,EmployeeList.get(i).getName());
            cv.put(KEY_X,EmployeeList.get(i).getX());
            cv.put(KEY_Y,EmployeeList.get(i).getY());
            cv.put(KEY_PIC,EmployeeList.get(i).getPic());
            cv.put(KEY_DESIGNATION,EmployeeList.get(i).getDesg());
            db.insert(TABLE_EMPLOYEE, null, cv);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    void batchInsertPlace(List<Place> PlaceList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for(int i=0, j=PlaceList.size(); i<j; i++) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID,PlaceList.get(i).getId());
            cv.put(KEY_NAME,PlaceList.get(i).getName());
            cv.put(KEY_X,PlaceList.get(i).getX());
            cv.put(KEY_Y,PlaceList.get(i).getY());
            cv.put(KEY_PIC,PlaceList.get(i).getPic());
            db.insert(TABLE_PLACE, null, cv);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    void batchInsertTimelog(List<Timelog> TimelogList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for(int i=0, j=TimelogList.size(); i<j; i++) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID,TimelogList.get(i).getId());
            cv.put(KEY_LINK,TimelogList.get(i).getLink());
            cv.put(KEY_DATE,TimelogList.get(i).getDate());
            cv.put(KEY_TIME,TimelogList.get(i).getTime());
            db.insert(TABLE_LOG, null, cv);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    void batchInsertQRcode(List<QRcode> QRcodeList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for(int i=0, j=QRcodeList.size(); i<j; i++) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID,QRcodeList.get(i).getId());
            cv.put(KEY_LINK,QRcodeList.get(i).getLink());
            cv.put(KEY_X,QRcodeList.get(i).getX());
            cv.put(KEY_Y,QRcodeList.get(i).getY());
            cv.put(KEY_TAG,QRcodeList.get(i).geTag());
            db.insert(TABLE_QR, null, cv);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    public Employee getEmployeeId(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE +" WHERE id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        Employee employee = new Employee();
        employee.setId(cursor.getInt(0));
        employee.setName(cursor.getString(1));
        employee.setX(cursor.getInt(2));
        employee.setY(cursor.getInt(3));
        employee.setPic(cursor.getInt(4));
        employee.setDesg(cursor.getString(5));
        db.close();
        return employee;


    }

    public Place getPlaceId(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE +" WHERE id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        Place place = new Place();
        place.setId(cursor.getInt(0));
        place.setName(cursor.getString(1));
        place.setX(cursor.getInt(2));
        place.setY(cursor.getInt(3));
        place.setPic(cursor.getInt(4));
        db.close();
        return place;

    }

    public Timelog getTimelogId(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_LOG +" WHERE id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        Timelog log = new Timelog();
        log.setId(cursor.getInt(0));
        log.setLink(cursor.getString(1));
        log.setDate(cursor.getString(2));
        log.setTime(cursor.getString(3));
        db.close();
        return log;

    }

    public QRcode getQRcodeId(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_QR +" WHERE id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        QRcode qr = new QRcode();
        qr.setId(cursor.getInt(0));
        qr.setLink(cursor.getString(1));
        qr.setX(cursor.getInt(2));
        qr.setY(cursor.getInt(3));
        qr.setTag(cursor.getString(4));
        db.close();
        return qr;

    }

    public QRcode getQRcodeLink(String link) {
        String selectQuery = "SELECT  * FROM " + TABLE_QR +" WHERE link LIKE '"+link+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        QRcode qr = new QRcode();
        qr.setId(cursor.getInt(0));
        qr.setLink(cursor.getString(1));
        qr.setX(cursor.getInt(2));
        qr.setY(cursor.getInt(3));
        qr.setTag(cursor.getString(4));
        db.close();
        return qr;

    }

    public List<Timelog> getAllTimelog() {
        List<Timelog> logList = new ArrayList<Timelog>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Timelog log = new Timelog();
                log.setId(cursor.getInt(0));
                log.setLink(cursor.getString(1));
                log.setDate(cursor.getString(2));
                log.setTime(cursor.getString(3));
                logList.add(log);
            } while (cursor.moveToNext());
        }
        db.close();
        return logList;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Employee emp_o = new Employee();
                emp_o.setId(cursor.getInt(0));
                emp_o.setName(cursor.getString(1));
                emp_o.setX(cursor.getInt(2));
                emp_o.setY(cursor.getInt(3));
                emp_o.setPic(cursor.getInt(4));
                emp_o.setDesg(cursor.getString(5));
                employeeList.add(emp_o);
            } while (cursor.moveToNext());
        }

        return employeeList;
    }

    public List<Place> getAllPlaces() {
        List<Place> placeList = new ArrayList<Place>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Place pl=new Place();
                pl.setId(cursor.getInt(0));
                pl.setName(cursor.getString(1));
                pl.setX(cursor.getInt(2));
                pl.setY(cursor.getInt(3));
                pl.setPic(cursor.getInt(4));
                placeList.add(pl);
            } while (cursor.moveToNext());
        }

        return placeList;
    }

    public List<Employee> getEmployeesByName(int type, String text, String desgName){
        String selectQuery = null;
        List<Employee> employeeList = new ArrayList<Employee>();
        if(type==TYPE_NAME){
            selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " WHERE " + KEY_NAME +" LIKE " + "'%"+text+"%'";
        }
        else if(type==TYPE_DESIGNATION){
            selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " WHERE " + KEY_DESIGNATION +" LIKE " + "'"+text+"'";
        }
        else if(type==TYPE_NAME_DESIGNATION){
            selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE + " WHERE " + KEY_DESIGNATION +" LIKE " + "'"+desgName+"'"+" AND "+ KEY_NAME +" LIKE "+"'%"+text+"%'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Employee emp_o = new Employee();
                emp_o.setId(cursor.getInt(0));
                emp_o.setName(cursor.getString(1));
                emp_o.setX(cursor.getInt(2));
                emp_o.setY(cursor.getInt(3));
                emp_o.setPic(cursor.getInt(4));
                emp_o.setDesg(cursor.getString(5));
                employeeList.add(emp_o);
            } while (cursor.moveToNext());
        }
        return employeeList;
    }

    public List<Place> getPlacesByName(int type, String text, String place) {
        List<Place> placeList = new ArrayList<Place>();
        String selectQuery = null;
        if(type==TYPE_PLACE){
            selectQuery = "SELECT  * FROM " + TABLE_PLACE + " WHERE " + KEY_NAME +" LIKE " + "'%"+text+"%'";
        }
        else if(type==TYPE_LABEL){
            selectQuery = "SELECT  * FROM " + TABLE_PLACE+ " WHERE " + KEY_NAME +" LIKE " + "'"+text+"'";
        }
        else if(type==TYPE_PLACE_LABEL){
            selectQuery = "SELECT  * FROM " + TABLE_PLACE+ " WHERE " + KEY_NAME +" LIKE " + "'"+place+"%'"+" AND "+ KEY_NAME +" LIKE "+"'%"+text+"%'";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Place pl=new Place();
                pl.setId(cursor.getInt(0));
                pl.setName(cursor.getString(1));
                pl.setX(cursor.getInt(2));
                pl.setY(cursor.getInt(3));
                pl.setPic(cursor.getInt(4));
                placeList.add(pl);
            } while (cursor.moveToNext());
        }

        return placeList;
    }

}