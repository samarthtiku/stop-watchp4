package edu.luc.etl.cs313.android.simplecountdown.model.time;

import static edu.luc.etl.cs313.android.simplecountdown.common.Constants.*;

/**
 * An implementation of the stopwatch data model.
 */
public class DefaultTimeModel implements TimeModel {

    private int runningTime = 0;

    private int lapTime = -1;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }


    @Override
    public void setRunTime(int runTime) {
        runningTime = runTime;
    }

    @Override
    public void incRuntime() {
        if (runningTime < MAX_RUN_TIME) {
            runningTime = (runningTime + SEC_PER_TICK) % SEC_PER_HOUR;
        }
    }

    @Override
    public void decRuntime() {
        runningTime = (runningTime - SEC_PER_TICK) % SEC_PER_HOUR;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    /*@Override
    public void setLaptime() {
        lapTime = runningTime;
    }

    @Override
    public int getLaptime() {
        return lapTime;
    }*/
}