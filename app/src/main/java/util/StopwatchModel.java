package util;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by thomaswiegand on 2015-11-10.
 */
public class StopwatchModel extends Observable {

    private int _hours;
    private int _minutes;
    private int _seconds;
    private int _hundreds;

    //private float _tenths;
    private boolean running;
    private TimerTask _timerTask;
    private Timer _timer;

    private long _start_time;

    public StopwatchModel()
    {
        this(0, 0, 0, 0);
    }

    public StopwatchModel(int hours, int minutes, int seconds, int hundreds)
    {
        //super();

        _hours = hours;
        _minutes = minutes;
        _seconds = seconds;
        _hundreds = hundreds;

        _timer = new Timer();
    }


    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String toString()
    {
        return String.format("%02d:%02d:%02d.%02d", _hours, _minutes, _seconds, _hundreds);
    }

    public void setMiliseconds(long milis)
    {
        _hundreds = (int)milis/10;
    }

    public void start()
    {
        if(!isRunning()) {
            reset();
            _timerTask = new StopWatchTask();
            _timer.scheduleAtFixedRate(_timerTask, 0L, 10L);
            setRunning(true);
        }
        updateObservers();
    }

    public  void stop()
    {
        if(isRunning())
        {
            _timerTask.cancel();
            setRunning(false);
        }
        updateObservers();
    }

    private void reset()
    {
        _hours = _minutes = _seconds = _hundreds = 0;
    }

    public void updateObservers()
    {
        this.setChanged();
        this.notifyObservers();
    }

    private class StopWatchTask extends TimerTask
    {
        /**
         * Called ever hundreds of a second
         */
        @Override
        public void run() {

            _hundreds++;
            if(_hundreds >= 100)
            {
                _hundreds = 0;
                _seconds++;
                if(_seconds >= 60)
                {
                    _seconds = 0;
                    _minutes++;
                    if(_minutes >= 60)
                    {
                        _minutes = 0;
                        _hours++;
                    }
                }
            }
            //Update every 30th of a second
            if(_hundreds % 3 == 0)
            {
                updateObservers();
            }
        }
    }

    public static void main(String [] args)
    {
        System.out.println("Hello World");
        StopwatchModel stpwtchmdl = new StopwatchModel();
        System.out.println("Starting at: " + stpwtchmdl.toString());
        stpwtchmdl.start();
        try{
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stpwtchmdl.stop();
        System.out.println("Stopped at: " + stpwtchmdl.toString());
    }


}
