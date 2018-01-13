package com.example.a1kayat34.stepsmeasurerapp;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    Sensor stepcounter, stepdetector;

    float[] stepcounter_values = new float[3];
    float stepdetector_values = 0;

    //SQLite Helper
    private MyHelper myhelper;
    

    //Declare imageButtons
    ImageButton addImgBtn, rewindImgBtn, walkBtn;

    //Buttons
    Button historybtn, homebtn;


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

        //Initialize Helper
        myhelper = new MyHelper(this);

        //Setting up Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        //Setting image buttons
        addImgBtn = (ImageButton)findViewById(R.id.add_btn);
        rewindImgBtn = (ImageButton)findViewById(R.id.rewind_btn);
        walkBtn = (ImageButton)findViewById(R.id.walk_btn);

        walkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Walk to start the  counter...", Toast.LENGTH_SHORT).show();
            }
        });

        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long returned_id=0;
                String nsteps = String.valueOf(stepdetector_values);
                //Setting up Date
                Calendar myCalendar = Calendar.getInstance();
                System.out.println("Current time =>"+myCalendar.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String cdate = sdf.format(myCalendar.getTime());

                returned_id = myhelper.add(nsteps,cdate);

                //etid.setText(String.valueOf(returned_id));
                Toast.makeText(MainActivity.this, "Adding...Steps:"+stepdetector_values+" No: "+returned_id, Toast.LENGTH_SHORT).show();

            }
        });
        rewindImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Rewind...", Toast.LENGTH_SHORT).show();

                stepdetector_values = 0;

                TextView tv_step = (TextView)findViewById(R.id.steps_id);
                TextView tv_step_message = (TextView)findViewById(R.id.botton_message_tv);
                tv_step.setText(String.valueOf(stepdetector_values));

            }
        });

        //Seetting buttons
        historybtn = (Button)findViewById(R.id.history_btn);
        historybtn.setOnClickListener(this);
        homebtn = (Button)findViewById(R.id.home_btn);
        homebtn.setOnClickListener(this);



    }

    @Override
    public void onResume() {
        super.onResume();

        //Setting up Time and Date
        Calendar myCalendar = Calendar.getInstance();
        System.out.println("Current time =>"+myCalendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        if(item.getItemId()==R.id.about){
            new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("ConterStep or Step Measurer is an app that counts the steps that a user makes which, also allows you to record your steps activity.").show();
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.history_btn){
            Toast.makeText(MainActivity.this, "Going to History...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MyHistory.class);
            startActivityForResult(intent,0);

            //send values to another activity

        }
        if (view.getId()==R.id.home_btn){
            Toast.makeText(MainActivity.this, "This is Home...", Toast.LENGTH_SHORT).show();

        }

    }
}
