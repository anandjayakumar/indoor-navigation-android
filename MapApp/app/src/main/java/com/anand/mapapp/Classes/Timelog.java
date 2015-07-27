package com.anand.mapapp.Classes;


public class Timelog {
    int _id;
    String _link;
    String _date;
    String _time;

    // Empttime constructor
    public Timelog(){

    }
    // constructor
    public Timelog(int id, String link, String date, String time){
        this._id = id;
        this._link = link;
        this._date = date;
        this._time = time;
    }

    // constructor

    // getting ID
    public int getId(){
        return this._id;
    }

    // setting id
    public void setId(int id){
        this._id = id;
    }

    // getting name
    public String getLink(){
        return this._link;
    }

    // setting name
    public void setLink(String link){
        this._link = link;
    }

    // getting phone number
    public String getDate(){
        return this._date;
    }
    public String getTime(){
        return this._time;
    }

    // setting phone number
    public void setDate(String date){
        this._date = date;
    }

    public void setTime(String time){
        this._time = time;
    }
}
