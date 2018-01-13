package com.example.a1kayat34.stepsmeasurerapp;

import java.text.SimpleDateFormat;

/**
 * Created by kayaya on 12/01/2018.
 */

public class Step {
    String date;
    String step;

    public Step(String steps, String date){
        this.step = steps;
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    public String getStep() {
        return step;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStep(String steps) {
        this.step = steps;
    }
}
