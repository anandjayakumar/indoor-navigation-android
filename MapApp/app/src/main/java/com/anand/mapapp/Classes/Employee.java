package com.anand.mapapp.Classes;

/**
 * Created by user on 25/6/15.
 */
public class Employee {

    int _id;
    String _name;
    int _pic;
    int _x,_y;
    String _designation;
    String _email;

    public Employee(){}
    public Employee(int id,String name,int X,int Y,int img,String desg,String email){
        this._id =id;
        this._name =name;
        this._pic=img;
        this._x=X;
        this._y=Y;
        this._designation=desg;
        this._email=email;
    }

    public void setId(int X) {
        this._id = X;
    }

    public void setName(String employeeName) {
        this._name = employeeName;
    }

    public void setPic(int employeeImg) {
        this._pic = employeeImg;
    }
    public void setX(int X) {
        this._x = X;
    }
    public void setY(int Y) {
        this._y = Y;
    }
    public void setEmail(String employeeEmail ) {
        this._email = employeeEmail;
    }
    public void setDesg(String employeeDesg) {
        this._designation=employeeDesg;
    }
    public String getName() {
        return this._name;
    }


    public int getId() {

        return this._id;
    }
    public int getPic() {
        return this._pic;
    }
    public int getX() {

        return this._x;
    }
    public int getY() {

        return this._y;
    }
    public String getDesg() {

        return this._designation;
    }
    public String getEmail() {

        return this._email;
    }
}