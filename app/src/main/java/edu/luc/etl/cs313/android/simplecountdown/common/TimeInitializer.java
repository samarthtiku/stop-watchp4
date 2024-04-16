package edu.luc.etl.cs313.android.simplecountdown.common;

/**
 * A startable component.
 *
 * @author laufer
 */
public interface TimeInitializer {
    void initializeTime(int value);
    boolean isInitialState ();
}
