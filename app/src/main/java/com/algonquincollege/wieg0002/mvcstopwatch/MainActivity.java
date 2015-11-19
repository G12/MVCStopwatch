package com.algonquincollege.wieg0002.mvcstopwatch;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

import util.CustomDialogFragment;
import util.StopwatchModel;

public class MainActivity extends AppCompatActivity implements Observer {

    StopwatchModel _stopWatch;
    TextView textViewStopwatch;
    TextView textViewStopCheck;
    TextView textViewMilis;

    Runnable stopwatchRunnable;
    long startTime;
    long elapsedTime;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewStopwatch = (TextView) findViewById(R.id.textViewStopwatch);
        textViewStopCheck = (TextView) findViewById(R.id.textViewStopCheck);
        textViewMilis = (TextView) findViewById(R.id.textViewMilis);

        _stopWatch = new StopwatchModel();
        _stopWatch.addObserver(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(_stopWatch.isRunning())
                {
                    elapsedTime = SystemClock.elapsedRealtime() - startTime;
                    _stopWatch.stop();

                    Date date = new Date(elapsedTime);
                    DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
                    String dateFormatted = formatter.format(date);
                    textViewStopCheck.setText(dateFormatted);
                    textViewStopwatch.setText(_stopWatch.toString());
                    textViewMilis.setText(Objects.toString(elapsedTime) + " miliseconds");
                }
                else
                {
                    textViewStopCheck.setText("");
                    textViewMilis.setText("");
                    startTime = SystemClock.elapsedRealtime();
                    _stopWatch.start();
                }
            }
        });

        stopwatchRunnable = new Runnable() {
            @Override
            public void run() {
                elapsedTime = SystemClock.elapsedRealtime() - startTime;
                if(elapsedTime < 1000)
                {
                    _stopWatch.setMiliseconds(elapsedTime);
                }
                textViewStopwatch.setText(_stopWatch.toString());
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            String msg = "Just the fact that some geniuses were laughed at does not imply that all who are laughed at are geniuses. They laughed at Columbus, they laughed at Fulton, they laughed at the Wright brothers. But they also laughed at Bozo the Clown.\n" +
                    "—Carl Sagan";
            DialogFragment frag = CustomDialogFragment.newInstance(R.string.dialog_title, msg);
            frag.show(getFragmentManager(), CustomDialogFragment.TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object data) {

        runOnUiThread(stopwatchRunnable);

    }
}
