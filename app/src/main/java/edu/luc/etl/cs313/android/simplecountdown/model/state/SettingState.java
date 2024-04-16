package edu.luc.etl.cs313.android.simplecountdown.model.state;

import static edu.luc.etl.cs313.android.simplecountdown.common.Constants.MAX_RUN_TIME;

import edu.luc.etl.cs313.android.simplecountdown.R;
/**
 * this will set the timer.
 *
 *
 */

public class SettingState implements CountdownState {

    public SettingState(final CountdownSMStateView sm) {
        this.sm = sm;
    }

    private final CountdownSMStateView sm;
    private int secPassed = -1;
    private boolean isStarted = false;
    @Override
    public void onSetStop() {
        secPassed = -1;
        sm.actionInc();
        sm.toSettingState();

        if (sm.actionGetRuntime() == MAX_RUN_TIME) {
           moveToNextState();
        }
    }

    @Override
    public void onTick() {
        isStarted = true;
        secPassed ++;
        if (secPassed >= 3 && sm.actionGetRuntime() > 0) {
           moveToNextState();
        }
    }

    //it will come to next state
    private void moveToNextState() {
        if (isStarted) {
            sm.actionStop();
            isStarted = false;
        }
        secPassed = -1;
        sm.toTimingState();
        sm.actionPlayBeep();
        sm.actionStart();
    }

    @Override
    public void updateView() {sm.updateUIRuntime();}


    @Override
    public int getId() {
        return R.string.SETTING;
    }
}

