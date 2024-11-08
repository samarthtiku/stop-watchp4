package edu.luc.etl.cs313.android.simplecountdown.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.luc.etl.cs313.android.simplecountdown.R;
import edu.luc.etl.cs313.android.simplecountdown.common.CountdownModelListener;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.TickListener;
import edu.luc.etl.cs313.android.simplecountdown.model.state.CountdownStateMachine;

import edu.luc.etl.cs313.android.simplecountdown.model.time.TimeModel;

/**
 * Testcase superclass for the stopwatch state machine model. Unit-tests the state
 * machine in fast-forward mode by directly triggering successive tick events
 * without the presence of a pseudo-real-time clock. Uses a single unified mock
 * object for all dependencies of the state machine model.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractStopwatchStateMachineTest {

    private CountdownStateMachine model;

    private UnifiedMockDependency dependency;

    @Before
    public void setUp() throws Exception {
        dependency = new UnifiedMockDependency();
    }

    @After
    public void tearDown() {
        dependency = null;
    }

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final CountdownStateMachine model) {
        this.model = model;
        if (model == null)
            return;
        this.model.setModelListener(dependency);
        this.model.actionInit();
    }

    protected UnifiedMockDependency getDependency() {
        return dependency;
    }

    /**
     * Verifies that we're initially in the stopped state (and told the listener
     * about it).
     */
    @Test
    public void testPreconditions() {
        assertEquals(R.string.STOPPED, dependency.getState());
    }

    /**
     * Verifies the following scenario: time is 0, press start, wait 5+ seconds,
     * expect time 5.
     */
    @Test
    public void testScenarioRun() {
        assertTimeEquals(0);
        assertFalse(dependency.isStarted());
        // directly invoke the button press event handler methods
        model.actionStart();
        assertTrue(dependency.isStarted());
        onTickRepeat(5);
        assertTimeEquals(5);
    }

    /**
     * Verifies the following scenario: time is 0, press start, wait 5+ seconds,
     * expect time 5, press lap, wait 4 seconds, expect time 5, press start,
     * expect time 5, press lap, expect time 9, press lap, expect time 0.
     *
     * @throws Throwable
     */
    @Test
    public void testScenarioRunLapReset() {
      /*  assertTimeEquals(0);
        assertFalse(dependency.isStarted());
        // directly invoke the button press event handler methods
        model.onStartStop();
        assertEquals(R.string.RUNNING, dependency.getState());
        assertTrue(dependency.isStarted());
        onTickRepeat(5);
        assertTimeEquals(5);
        model.onLapReset();
        assertEquals(R.string.LAP_RUNNING, dependency.getState());
        assertTrue(dependency.isStarted());
        onTickRepeat(4);
        assertTimeEquals(5);
        model.onStartStop();
        assertEquals(R.string.LAP_STOPPED, dependency.getState());
        assertFalse(dependency.isStarted());
        assertTimeEquals(5);
        model.onLapReset();
        assertEquals(R.string.STOPPED, dependency.getState());
        assertFalse(dependency.isStarted());
        assertTimeEquals(9);
        model.onLapReset();
        assertEquals(R.string.STOPPED, dependency.getState());
        assertFalse(dependency.isStarted());
        assertTimeEquals(0);*/
    }

    /**
     * Sends the given number of tick events to the model.
     *
     *  @param n the number of tick events
     */
    protected void onTickRepeat(final int n) {
        for (var i = 0; i < n; i++)
            model.onTick();
    }

    /**
     * Checks whether the model has invoked the expected time-keeping
     * methods on the mock object.
     */
    protected void assertTimeEquals(final int t) {
        assertEquals(t, dependency.getTime());
    }
}

/**
 * Manually implemented mock object that unifies the three dependencies of the
 * stopwatch state machine model. The three dependencies correspond to the three
 * interfaces this mock object implements.
 *
 * @author laufer
 */
class UnifiedMockDependency implements TimeModel, ClockModel, CountdownModelListener {

    private int timeValue = -1, stateId = -1;

    private int runningTime = 0, lapTime = -1;

    private boolean started = false;

    public int getTime() {
        return timeValue;
    }

    public int getState() {
        return stateId;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void onTimeUpdate(final int timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public void onStateUpdate(final int stateId) {
        this.stateId = stateId;
    }

    @Override
    public void onPlayBeep() {

    }

    @Override
    public void onStartAlarm() {

    }

    @Override
    public void onStopAlarm() {

    }

    @Override
    public void setTickListener(TickListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void incRuntime() {
        runningTime++;
    }

    @Override
    public void setRunTime(int time) {
        setRunTime(time);
    }

    @Override
    public void decRuntime() {
        runningTime--;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }
}
