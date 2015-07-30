package com.anand.mapapp.Classes;


public class Timelog {
    int _id;
    String _link;
    String _date;
    String _time;

    public Timelog(){

    }

    public Timelog(int id, String link, String date, String time){
        this._id = id;
        this._link = link;
        this._date = date;
        this._time = time;
    }

    public int getId(){
        return this._id;
    }

    public void setId(int id){
        this._id = id;
    }

    public String getLink(){
        return this._link;
    }

    public void setLink(String link){
        this._link = link;
    }

    public String getDate(){
        return this._date;
    }
    public String getTime(){
        return this._time;
    }

    public void setDate(String date){
        this._date = date;
    }

    public void setTime(String time){
        this._time = time;
    }
}
