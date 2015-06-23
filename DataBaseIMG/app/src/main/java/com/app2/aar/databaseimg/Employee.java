package com.app2.aar.databaseimg;

/**
 * Created by user on 22/6/15.
 */
public class Employee {
    String _Emp_name;
    int _Prof_Pic;
    int _x,_y;
    String _designation;

    public Employee(){}
    public Employee(String name,int img,int X,int Y,String desg){
        this._Emp_name=name;
        this._Prof_Pic=img;
        this._x=X;
        this._y=Y;
        this._designation=desg;
    }

    public void setEmployeeName(String employeeName) {
        this._Emp_name = employeeName;
    }

    public void setEmployeeImg(int employeeImg) {
        this._Prof_Pic = employeeImg;
    }
    public void setXval(int X) {
        this._x = X;
    }
    public void setYval(int Y) {
        this._y = Y;
    }
    public void setEmployeeDesg(String employeeDesg) {
        this._designation=employeeDesg;
    }
    public String getEmployeeName() {
        return this._Emp_name;
    }

    public int getEmployeeImg() {
        return this._Prof_Pic;
    }
    public int getXval() {
        return this._x;
    }
    public int getYval() {
        return this._y;
    }
    public String getEmployeeDesg() {
        return this._designation;
    }
}



