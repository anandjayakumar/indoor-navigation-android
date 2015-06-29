package com.app2.aar.databaseimg2;

/**
 * Created by user on 25/6/15.
 */
public class Place {
    String _Place_name;
    int _px,_py;
    int _pic;
    public Place(){}
    public Place(String name,int X,int Y,int picture){
        this._Place_name=name;
        this._px=X;
        this._py=Y;
        this._pic=picture;
    }

    public Place getPlaceDetails() {
        Place plc = new Place();
        plc._Place_name = getPlaceName();
        plc._px = getPXval();
        plc._py = getPYval();
        plc._pic = getPic();
        return plc;
    }
    public void setPlaceName(String placeName) {
            this._Place_name=placeName;
    }
    public void setPXval(int X) {
            this._px = X;
        }
    public void setPYval(int Y) {
            this._py = Y;
        }
    public void setPic(int pict) {
        this._pic = pict;
    }

    public String getPlaceName() {
            return this._Place_name;
        }
    public int getPXval() {
            return this._px;
        }
    public int getPYval() {
            return this._py;
        }
    public int getPic() {
        return this._pic;
    }
}
