package edu.luc.etl.cs313.android.simplecountdown.model;

import edu.luc.etl.cs313.android.simplecountdown.common.CountdownModelListener;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplecountdown.model.state.DefaultCountdownStateMachine;
import edu.luc.etl.cs313.android.simplecountdown.model.state.CountdownStateMachine;
import edu.luc.etl.cs313.android.simplecountdown.model.time.DefaultTimeModel;
import edu.luc.etl.cs313.android.simplecountdown.model.time.TimeModel;

/**
 * An implementation of the model facade.
 *
 * @author laufer
 */
public class ConcreteCountdownModelFacade implements CountdownModelFacade {

    private final CountdownStateMachine stateMachine;

    private final ClockModel clockModel;

    private final TimeModel timeModel;

    public ConcreteCountdownModelFacade() {
        timeModel = new DefaultTimeModel();
        clockModel = new DefaultClockModel();
        stateMachine = new DefaultCountdownStateMachine(timeModel, clockModel);
        clockModel.setTickListener(stateMachine);
    }

    @Override
    public void start() {
        stateMachine.actionInit();
    }

    @Override
    public void setModelListener(final CountdownModelListener listener) {
        stateMachine.setModelListener(listener);
    }

    @Override
    public void onSetStop() {
        stateMachine.onSetStop();
    }

    @Override
    public void initializeTime(int value) {
        stateMachine.actionSetRunTime(value);
    }

    @Override
    public boolean isInitialState() {
        return stateMachine.isStopped();
    }

    /*@Override
    public void onLapReset() {
        stateMachine.onLapReset();
    }*/

}
