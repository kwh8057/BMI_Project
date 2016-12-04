package com.example.woooo.bmi_project;


public class Graph_Data {
    private String date;
    private float bmi;

    public Graph_Data(String date, float bmi){
        this.date = date;
        this.bmi = bmi;
    }

    public String getDate(){
        return date;
    }
    public float getBmi(){
        return bmi;
    }
}