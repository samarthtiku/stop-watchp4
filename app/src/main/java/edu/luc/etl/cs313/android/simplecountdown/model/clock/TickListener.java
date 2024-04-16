package edu.luc.etl.cs313.android.simplecountdown.model.clock;

/**
 * A listener for onTick events coming from the internal clock model.
 *
 * @author laufer
 */
public interface TickListener {
    void onTick();
    void onSetStop();

}
