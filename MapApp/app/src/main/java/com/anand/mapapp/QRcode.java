package com.anand.mapapp;


public class QRcode {
    int _id;
    String _link;
    int _x,_y;

    public QRcode(){}
    public QRcode(int id,String link,int X,int Y){
        this._id =id;
        this._x=X;
        this._y=Y;
        this._link=link;
    }

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

    public void setX(int X) {
        this._x = X;
    }
    public void setY(int Y) {
        this._y = Y;
    }

    public int getX() {

        return this._x;
    }
    public int getY() {

        return this._y;
    }
}
