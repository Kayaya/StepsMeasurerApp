package com.example.a1kayat34.stepsmeasurerapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Sensor stepcounter, stepdetector;

    float[] stepcounter_values = new float[3];
    float stepdetector_values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager;
        sensorManager = (SensorManager)this.getSystemService(this.SENSOR_SERVICE);

        stepcounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepdetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        sensorManager.registerListener(this, stepcounter, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, stepdetector, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == stepcounter){
            stepcounter_values = sensorEvent.values.clone();

            TextView tv_totalsteps = (TextView)findViewById(R.id.tv_stepcounter);
            tv_totalsteps.setText(String.valueOf(stepcounter_values[0]));


        }
        if (sensorEvent.sensor == stepdetector){
            stepdetector_values += sensorEvent.values.length;

            TextView tv_step = (TextView)findViewById(R.id.tv_stepdetector);
            tv_step.setText(String.valueOf(stepdetector_values));

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
