package edu.luc.etl.cs313.android.simplecountdown.model.state;

import edu.luc.etl.cs313.android.simplecountdown.common.CountdownUIListener;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.TickListener;

/**
 * A state in a state machine. This interface is part of the State pattern.
 *
 * @author laufer
 */
interface CountdownState extends CountdownUIListener, TickListener {
    void updateView();
    int getId();
}
