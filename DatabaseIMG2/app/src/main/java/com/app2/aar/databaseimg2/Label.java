package com.app2.aar.databaseimg2;

public class Label {
        int image;
        String desgName;

        Label(int img,String desg){
            this.image=img;
            this.desgName=desg;
        }
        public int getImage(){
            return this.image;
        }
        public String getName(){
            return this.desgName;
        }

}
