package com.anand.mapapp.Classes;

public class Place {
    int _id;
    String _name;
    String _type;
    int _x,_y;
    int _pic;
    int _Favourite;
    public Place(){}
    public Place(int id,String name,String type,int X,int Y,int picture,int fav){
        this._id=id;
        this._name=name;
        this._type=type;
        this._x=X;
        this._y=Y;
        this._pic=picture;
        this._Favourite=fav;

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
    public void makeFavourite(int fav){
        this._Favourite=fav;
    }
    public int isFavourite(){
        return this._Favourite;
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

    public void setType(String type){
        this._type=type;
    }
    public String getType(){
        return this._type;
    }
}