package com.example.faraaz.monthlybudgetbuddy;

/**
 * Created by faraa on 2018-04-05.
 */

public class Income {


    String type1,desc,date,id;
    double amt;

public Income(){

}

    public Income(String id,String type1, String desc, String date, double  amt) {
        this.type1 = type1;
        this.desc = desc;
        this.date = date;
        this.amt = amt;
        this.id = id;
    }

    public String getType1() {
        return type1;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public double getAmt() {
        return amt;
    }

    public String getId() {
        return id;
    }
}

