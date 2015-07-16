package com.anand.mapapp.Classes;

public class Favourite {
    int id;
    String name;
    int type;
    int pic;
    String detail;
    String extra;
    int px,py;

    public Favourite(){}
    public void setName(String favName){
        this.name=favName;
    }

    public void setPic(int favPic){
        this.pic=favPic;
    }
    public void setId(int favID){
        this.id=favID;
    }
    public void setX(int x){
        this.px=x;
    }


    public void setY(int y){
        this.py=y;
    }
    public void setDetail(String favDetail){
        this.detail=favDetail;
    }

    public void setExtra(String favExtra){
        this.extra=favExtra;
    }
    public int getPic(){
         return this.pic;
    }
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDetail(){
        return this.detail;
    }
    public String getExtra(){
        return this.extra;
    }

    public int getX(){
        return this.px;
    }

    public int getY(){
        return this.py;
    }

    public void setType(int type){
        this.type=type;
    }
    public int getType(){
        return this.type;
    }

}
