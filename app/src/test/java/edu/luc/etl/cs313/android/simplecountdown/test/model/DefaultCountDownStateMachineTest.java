package edu.luc.etl.cs313.android.simplecountdown.test.model;

import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simplecountdown.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplecountdown.model.state.DefaultCountdownStateMachine;
import edu.luc.etl.cs313.android.simplecountdown.model.time.DefaultTimeModel;

/**
 * Concrete testcase subclass for the default stopwatch state machine
 * implementation.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public class DefaultCountDownStateMachineTest extends AbstractStopwatchStateMachineTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        setModel(new DefaultCountdownStateMachine(new DefaultTimeModel(), new DefaultClockModel()));
    }

    @After
    public void tearDown() {
        setModel(null);
        super.tearDown();
    }
}
