package edu.luc.etl.cs313.android.simplecountdown.common;

/**
 * A listener for stopwatch events coming from the UI.
 *
 * @author laufer
 */
public interface CountdownUIListener {
    void onSetStop();
    //void onLapReset(); //not needed
}
