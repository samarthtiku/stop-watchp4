package edu.luc.etl.cs313.android.simplecountdown.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface CountdownSMStateView {

    // transitions
    void toRunningState();
    void toStoppedState();
    void toSettingState();
    void toTimingState();
    void toAlarmingState();
    //void toLapRunningState();
    //void toLapStoppedState();

    // actions
    void actionInit();
    void actionReset();
    void actionStart();
    void actionStop();
    //void actionLap();
    void actionInc();
    void actionSetRunTime(int value);
    void actionDec();
    void actionUpdateView();
    int actionGetRuntime();
    void timeSince();

    // state-dependent UI updates
    void updateUIRuntime();

    //play media
    void actionPlayBeep();
    void actionStartAlarm();
    void actionStopAlarm();


    //checking if it's initial state or not
    boolean isStopped();



    //void updateUILaptime();
}
