package com.anand.mapapp.Classes;

public class Marker {
    int _id;
    int _cat;
    int _x;
    int _y;
    float _mapx;
    float _mapy;

    public Marker(int id,int cat,int x,int y){
        this._id=id;
        this._cat=cat;
        this._x=x;
        this._y=y;
    }

    public void setId(int id) {
        this._id = id;
    }
    public int getId(){
        return this._id;
    }

    public void setCat(int cat){
        this._cat=cat;
    }
    public int getCat(){
        return this._cat;
    }

    public void setX(int X) {
        this._x = X;
    }
    public int getX() {
        return this._x;
    }

    public int getY() {
        return this._y;
    }
    public void setY(int Y) {
        this._y = Y;
    }

    public void setMapx(float x){
        this._mapx=x;
    }
    public float getMapx(){
        return this._mapx;
    }

    public void setMapy(float y){
        this._mapy=y;
    }
    public float getMapy(){
        return this._mapy;
    }

}



