package com.example.a1kayat34.stepsmeasurerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyHistory extends AppCompatActivity implements View.OnClickListener {
    //SQLite Helper
    private MyHelper myhelper;

    //Buttons
    Button historybtn;
    Button homebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_h);
        setSupportActionBar(toolbar);

        //Initialize Helper
        myhelper = new MyHelper(this);

        //Seetting buttons
        historybtn = (Button)findViewById(R.id.history_btn);
        historybtn.setOnClickListener(this);
        homebtn = (Button)findViewById(R.id.home_btn);
        homebtn.setOnClickListener(this);

        //Reading from SQLite
        TextView notv = (TextView)findViewById(R.id.no_id);
        TextView datetv = (TextView)findViewById(R.id.date_id);
        TextView nstv = (TextView)findViewById(R.id.ns_id);
        Step mystep;
        String n_steps, mdate;
        long myid = 1;

        String resultsNo = "",resultsDate = "",resultsNS = "";
        String line = "";



        while(myhelper.search(myid) != null){

            mystep = myhelper.search(myid);

            n_steps = mystep.getStep();
            mdate = mystep.getDate();

            resultsNo = resultsNo + myid+"\n";
            resultsDate = resultsDate + mdate + "\n";
            resultsNS = resultsNS + n_steps+ "\n";


            /*line = "  "+myid+"  " +mdate+ "  " +n_steps+ "\n";
            results = results + line;*/


            myid++;
        }
        notv.setText(resultsNo);
        datetv.setText(resultsDate);
        nstv.setText(resultsNS);



    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.home_btn){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        if (view.getId()==R.id.history_btn){
            Toast.makeText(MyHistory.this, "This is History...", Toast.LENGTH_SHORT).show();
        }


    }
}
