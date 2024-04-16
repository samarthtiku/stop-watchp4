package edu.luc.etl.cs313.android.simplecountdown.model.state;

import edu.luc.etl.cs313.android.simplecountdown.R;

class StoppedState implements CountdownState {

    public StoppedState(final CountdownSMStateView sm) {
        this.sm = sm;
    }

    private final CountdownSMStateView sm;

    @Override
    public void onSetStop() {
        //sm.actionStart();
        //sm.toRunningState();
        sm.toSettingState();
        sm.actionStart();
    }

    @Override
    public void onTick() {
        throw new UnsupportedOperationException("onTick");
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.STOPPED;
    }
}
