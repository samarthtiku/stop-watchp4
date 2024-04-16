package edu.luc.etl.cs313.android.simplecountdown.model.state;

import edu.luc.etl.cs313.android.simplecountdown.common.CountdownModelSource;
import edu.luc.etl.cs313.android.simplecountdown.common.CountdownUIListener;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.TickListener;

/**
 * The state machine for the state-based dynamic model of the stopwatch.
 * This interface is part of the State pattern.
 *
 * @author laufer
 */
public interface CountdownStateMachine extends CountdownUIListener, TickListener, CountdownModelSource, CountdownSMStateView {

}
