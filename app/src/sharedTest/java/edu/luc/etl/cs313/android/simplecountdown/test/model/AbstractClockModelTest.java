package edu.luc.etl.cs313.android.simplecountdown.test.model;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import edu.luc.etl.cs313.android.simplecountdown.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplecountdown.model.clock.TickListener;


/**
 * Test case superclass for the autonomous clock model abstraction.
 * Unit-tests the pseudo-real-time behavior of the clock.
 * Uses a simple stub object to satisfy the clock's dependency.
 *
 * @author laufer
 */
public abstract class AbstractClockModelTest {

    private ClockModel model;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final ClockModel model) {
        this.model = model;
    }

    protected ClockModel getModel() {
        return model;
    }

    /**
     * Verifies that a stopped clock does not emit any tick events.
     *
     * @throws InterruptedException
     */
    @Test
    public void testStopped() throws InterruptedException {
        // use a thread-safe object because the timer inside the
        // clock has its own thread
        final var i = new AtomicInteger(0);
        model.setTickListener(new TickListener() {
            @Override
            public void onTick() {
                i.incrementAndGet();
            }

            @Override
            public void onSetStop() {

            }
        });
        Thread.sleep(5500);
        assertEquals(0, i.get());
    }

    /**
     * Verifies that a running clock emits about one tick event per second.
     *
     * @throws InterruptedException
     */
    @Test
    public void testRunning() throws InterruptedException {
        final var i = new AtomicInteger(0);
        model.setTickListener(new TickListener() {
            @Override
            public void onTick() {
                i.incrementAndGet();
            }

            @Override
            public void onSetStop() {

            }
        });
        model.start();
        Thread.sleep(5500);
        model.stop();
        assertEquals(5, i.get());
    }
}
