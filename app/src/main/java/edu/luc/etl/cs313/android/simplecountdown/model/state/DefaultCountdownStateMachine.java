package edu.luc.etl.cs313.android.simplecountdown.model.state;

import edu.luc.etl.cs313.android.simplecountdown.common.CountdownModelListener;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplecountdown.model.time.TimeModel;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultCountdownStateMachine implements CountdownStateMachine {

    public DefaultCountdownStateMachine(final TimeModel timeModel, final ClockModel clockModel) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
    }

    private final TimeModel timeModel;

    private final ClockModel clockModel;

    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private CountdownState state;

    protected void setState(final CountdownState state) {
        this.state = state;
        listener.onStateUpdate(state.getId());
    }

    private CountdownModelListener listener;

    @Override
    public void setModelListener(final CountdownModelListener listener) {
        this.listener = listener;
    }

    // forward event uiUpdateListener methods to the current state
    // these must be synchronized because events can come from the
    // UI thread or the timer thread
    @Override public synchronized void onSetStop() { state.onSetStop(); }

    //@Override public synchronized void onLapReset()  { state.onLapReset(); }
    @Override public synchronized void onTick()      { state.onTick(); }

    @Override public void updateUIRuntime() { listener.onTimeUpdate(timeModel.getRuntime()); }
    //@Override public void updateUILaptime() { listener.onTimeUpdate(timeModel.getLaptime()); }

    // known states
    private final CountdownState STOPPED     = new StoppedState(this);
    private final CountdownState RUNNING     = new RunningState(this);
    private final CountdownState SETTING     = new SettingState(this);
    private final CountdownState ALARMING    = new AlarmingState(this);
    private final CountdownState TIMING      = new TimingState(this);
    //private final CountdownState LAP_RUNNING = new LapRunningState(this);
    //private final CountdownState LAP_STOPPED = new LapStoppedState(this);

    // transitions
    @Override public void toRunningState()    { setState(RUNNING); }
    @Override public void toStoppedState()    { setState(STOPPED); }
    @Override public void toSettingState(){    setState(SETTING);}
    @Override public void toTimingState(){    setState(TIMING);}
    @Override public void toAlarmingState(){    setState(ALARMING);}

    //@Override public void toLapRunningState() { setState(LAP_RUNNING); }
    //@Override public void toLapStoppedState() { setState(LAP_STOPPED); }

    // actions
    @Override public void actionInit()       { toStoppedState(); actionReset(); }
    @Override public void actionReset()      { timeModel.resetRuntime(); actionUpdateView(); }
    @Override public void actionStart()      { clockModel.start(); }
    @Override public void actionStop()       { clockModel.stop(); }
    //@Override public void actionLap()        { timeModel.setLaptime(); }
    @Override public void actionInc()        { timeModel.incRuntime(); actionUpdateView(); }
    @Override public void actionSetRunTime(int value)        { timeModel.setRunTime(value); actionUpdateView(); }
    @Override public void actionDec()        { timeModel.decRuntime(); actionUpdateView(); }
    @Override public int actionGetRuntime() {return timeModel.getRuntime();}


    @Override public void actionPlayBeep() {listener.onPlayBeep();}
    @Override public void actionStartAlarm() {listener.onStartAlarm();}
    @Override public void actionStopAlarm() {listener.onStopAlarm();}

    @Override
    public boolean isStopped() {return state.getId() == STOPPED.getId();}


    @Override
    public void timeSince() {

    }

    @Override public void actionUpdateView() { state.updateView(); }
}
