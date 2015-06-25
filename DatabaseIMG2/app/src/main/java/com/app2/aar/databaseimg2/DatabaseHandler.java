package com.app2.aar.databaseimg2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

import static java.lang.String.*;
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Emp_Place_DB";

    // Contacts table name
    private static final String TABLE_EMPLOYEE = "employees";
    private static final String TABLE_PLACE = "places";

    // Employees Table Columns names
    //private static final String KEY_ID = "id";
    private static final String EMP_NAME = "name";
    private static final String EMP_IMG = "profile";
    private static final String EMP_X = "X_Cord";
    private static final String EMP_Y = "Y_Cord";
    private static final String EMP_DESG = "Designation";

    // Places Table Columns names
    //private static final String KEY_ID = "id";
    private static final String PLACE_NAME = "name";
    private static final String PLACE_X = "X_Cordinate";
    private static final String PLACE_Y = "Y_Cordinate";
    private static final String PLACE_PIC = "pic";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE + "("
                + EMP_NAME + " TEXT,"
                + EMP_IMG + " INTEGER," + EMP_X + " INTEGER," + EMP_Y + " INTEGER," + EMP_DESG + " TEXT" + ")";

        String CREATE_PLACES_TABLE= "CREATE TABLE IF NOT EXISTS " + TABLE_PLACE + "("
                + PLACE_NAME + " TEXT,"
                 + PLACE_X + " INTEGER," + PLACE_Y + " INTEGER," +  PLACE_PIC + " INTEGER" + ")";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
        db.execSQL(CREATE_PLACES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMP);
        if (oldVersion == 1 && newVersion == 2) {
            onCreate(db);
        }

        // Create tables again
        //onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addEmployee(Employee employee_o){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMP_NAME, employee_o.getEmployeeName()); // Contact Name
        values.put(EMP_IMG,employee_o.getEmployeeImg()); // Contact Phone
        values.put(EMP_X,employee_o.getXval()); // Contact Name
        values.put(EMP_Y,employee_o.getYval()); // Contact Name
        values.put(EMP_DESG,employee_o.getEmployeeDesg()); // Contact Name
        // Inserting Row
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close(); // Closing database connection
    }


    void addPlace(Place place_o){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLACE_NAME, place_o.getPlaceName());
        values.put(PLACE_X,place_o.getPXval());
        values.put(PLACE_Y,place_o.getPYval());
        values.put(PLACE_PIC,place_o.getPic());
        // Inserting Row
        db.insert(TABLE_PLACE, null, values);
        db.close(); // Closing database connection
    }
    // Getting single contact
    /*Employee getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_NAME, KEY_PH_NO }, KEY_NAME + "=?",
                new String[] { KEY_NAME}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee contact = new Employee(cursor.getString(0), cursor.getInt(1));
        // return contact
        return contact;
    }*/

    // Getting All Contacts
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee emp_o = new Employee();
                emp_o.setEmployeeName(cursor.getString(0));
                emp_o.setEmployeeImg(cursor.getInt(1));
                emp_o.setXval(cursor.getInt(2));
                emp_o.setYval(cursor.getInt(3));
                emp_o.setEmployeeDesg(cursor.getString(4));
                // Adding contact to list
                employeeList.add(emp_o);
            } while (cursor.moveToNext());
        }

        // return contact list
        return employeeList;
    }

    public List<Place> getAllPlaces() {
        List<Place> placeList = new ArrayList<Place>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Place pl=new Place();
                pl.setPlaceName(cursor.getString(0));
                pl.setPXval(cursor.getInt(1));
                pl.setPYval(cursor.getInt(2));
                pl.setPic(cursor.getInt(3));
                // Adding contact to list
                placeList.add(pl);
            } while (cursor.moveToNext());
        }

        // return contact list
        return placeList;
    }
    // Updating single contact
    /*public int updateContact(Employee contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getEmployeeName());
        values.put(KEY_PH_NO, contact.getEmployeeImg());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_NAME+ " = ?",
                new String[] { contact.getEmployeeName() });
    }
    */
    // Deleting single contact
    public void deleteEmployee(Employee employee_o) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, EMP_NAME + " = ?",
                new String[] { employee_o.getEmployeeName() });
        db.close();
    }

    public void deletePlace(Place place_o) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACE, PLACE_NAME + " = ?",
                new String[] { place_o.getPlaceName()});
        db.close();
    }
    // Getting contacts Count
    /*public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }*/
}
