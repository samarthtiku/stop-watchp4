package edu.luc.etl.cs313.android.simplecountdown.model.state;

import edu.luc.etl.cs313.android.simplecountdown.R;
/**
 * this will set alarm start and stop
 *
 * it will call when timer reachs to 00.
 *
 *
 */
public class AlarmingState implements CountdownState {

    public AlarmingState(final CountdownSMStateView sm) {
        this.sm = sm;
    }

    private final CountdownSMStateView sm;

    @Override
    public void onSetStop() {
        sm.actionStopAlarm();
        sm.actionReset();
        sm.toStoppedState();
    }


    @Override
    public void onTick() {throw new UnsupportedOperationException("onTick");}

    @Override
    public void updateView() { sm.updateUIRuntime(); }

    @Override
    public int getId() {
        return R.string.ALARMING;
    }
}
