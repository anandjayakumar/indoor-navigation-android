package com.anand.mapapp.Classes;

public class Label {
        int image;
        String desgName;

        public Label(int img,String desg){
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
