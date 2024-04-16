package edu.luc.etl.cs313.android.simplecountdown.model.state;

import edu.luc.etl.cs313.android.simplecountdown.R;
/**
 * Timing of countdown will start
 *
 */
public class TimingState implements CountdownState{

    public TimingState(final CountdownSMStateView sm) {
        this.sm = sm;
    }

    private final CountdownSMStateView sm;

    @Override
    public void onSetStop() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onTick() {
        //if runtime == 0 then
        if (sm.actionGetRuntime() == 0){
            sm.actionStop();
            sm.actionReset();
            sm.toAlarmingState();
            sm.actionStartAlarm();
        }
        else{
            sm.actionDec();
            sm.toTimingState();
        }
    }

    @Override
    public void updateView() { sm.updateUIRuntime();   }

    @Override
    public int getId() {
        return R.string.TIMING;
    }
}
