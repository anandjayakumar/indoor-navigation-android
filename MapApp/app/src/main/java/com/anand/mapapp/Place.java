package com.anand.mapapp;

/**
 * Created by user on 25/6/15.
 */
public class Place {
    int _id;
    String _name;
    int _x,_y;
    int _pic;
    public Place(){}
    public Place(int id,String name,int X,int Y,int picture){
        this._id=id;
        this._name=name;
        this._x=X;
        this._y=Y;
        this._pic=picture;
    }

    public void setName(String name) {
        this._name=name;
    }
    public void setX(int X) {
        this._x = X;
    }
    public void setY(int Y) {
        this._y = Y;
    }
    public void setPic(int pict) {
        this._pic = pict;
    }
    public void setId(int X) {
        this._id = X;
    }

    public int getId() {

        return this._id;
    }

    public String getName() {
        return this._name;
    }
    public int getX() {
        return this._x;
    }
    public int getY() {
        return this._y;
    }
    public int getPic() {
        return this._pic;
    }
}
