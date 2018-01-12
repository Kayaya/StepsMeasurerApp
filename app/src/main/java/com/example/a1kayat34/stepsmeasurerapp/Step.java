package com.example.a1kayat34.stepsmeasurerapp;

import java.text.SimpleDateFormat;

/**
 * Created by kayaya on 12/01/2018.
 */

public class Step {
    String date;
    Long steps;

    public Step(Long steps, String date){
        this.steps = steps;
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    public float getSteps() {
        return steps;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }
}
