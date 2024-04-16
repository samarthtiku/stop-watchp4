package edu.luc.etl.cs313.android.simplecountdown.model.state;

import edu.luc.etl.cs313.android.simplecountdown.R;

class RunningState implements CountdownState {

    public RunningState(final CountdownSMStateView sm) {
        this.sm = sm;
    }

    private final CountdownSMStateView sm;

    @Override
    public void onSetStop() {
        sm.actionReset();
        sm.toStoppedState();
    }

    /*@Override //No Lap needed
    public void onLapReset() {
        sm.actionLap();
        sm.toLapRunningState();
    }*/

    @Override
    public void onTick() {
        sm.actionInc();
        sm.toRunningState();
    }

    @Override
    public void updateView() { sm.updateUIRuntime();}

    @Override
    public int getId() {
        return R.string.RUNNING;
    }
}
