package com.example.a1kayat34.stepsmeasurerapp;

import android.support.v7.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Sensor stepcounter, stepdetector;

    float[] stepcounter_values = new float[3];
    float stepdetector_values = 0;


    //Declare imageButtons
    ImageButton addImgBtn, rewindImgBtn;

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

        //Setting up Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        //Setting image buttons
        addImgBtn = (ImageButton)findViewById(R.id.add_btn);
        rewindImgBtn = (ImageButton)findViewById(R.id.rewind_btn);

        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Adding...", Toast.LENGTH_SHORT).show();
            }
        });
        rewindImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Rewind...", Toast.LENGTH_SHORT).show();
            }
        });

        //Setting up Time and Date
        Calendar myCalendar = Calendar.getInstance();
        System.out.println("Current time =>"+myCalendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Add the date to a string
        String current_date = sdf.format(myCalendar.getTime());
        //Now display in Text View
        TextView date_tv = (TextView)findViewById(R.id.date_tv);
        date_tv.setText(current_date);



    }
//Setting up the Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.settings){
            new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("Searching...").show();
            return true;
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == stepcounter){
            stepcounter_values = sensorEvent.values.clone();

            /*TextView tv_totalsteps = (TextView)findViewById(R.id.tv_stepcounter);
            tv_totalsteps.setText(String.valueOf(stepcounter_values[0]));*/

        }
        if (sensorEvent.sensor == stepdetector){
            stepdetector_values += sensorEvent.values.length;

            TextView tv_step = (TextView)findViewById(R.id.steps_id);
            TextView tv_step_message = (TextView)findViewById(R.id.botton_message_tv);
            tv_step.setText(String.valueOf(stepdetector_values));
            tv_step_message.setText("Counting...");

        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
