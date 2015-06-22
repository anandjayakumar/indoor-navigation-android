package com.app2.aar.databaseimg;

/**
 * Created by user on 22/6/15.
 */
public class Employee {
    String _Emp_name;
    int _Prof_Pic;

    public Employee(){}
    public Employee(String name,int img){
        this._Emp_name=name;
        this._Prof_Pic=img;
    }

    public void setEmployeeName(String employeeName) {
        this._Emp_name = employeeName;
    }

    public void setEmployeeImg(int employeeImg) {
        this._Prof_Pic = employeeImg;
    }

    public String getEmployeeName() {
        return this._Emp_name;
    }

    public int getEmployeeImg() {
        return this._Prof_Pic;
    }
}



