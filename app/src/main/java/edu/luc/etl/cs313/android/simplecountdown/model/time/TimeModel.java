package edu.luc.etl.cs313.android.simplecountdown.model.time;

/**
 * The passive data model of the stopwatch.
 * It does not emit any events.
 *
 * @author laufer
 */
public interface TimeModel {
    void resetRuntime();
    void incRuntime();
    void setRunTime(int time);
    void decRuntime();
    int getRuntime();
    //void setLaptime();
    //int getLaptime();
}
