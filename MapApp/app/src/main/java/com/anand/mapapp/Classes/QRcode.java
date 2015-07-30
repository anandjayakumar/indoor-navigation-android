package com.anand.mapapp.Classes;


public class QRcode {
    int _id;
    String _link;
    int _x,_y;
    String _tag;

    public QRcode(){}


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
    public String geTag(){
        return this._tag;
    }

    public void setTag(String tag){
        this._tag = tag;
    }
}
