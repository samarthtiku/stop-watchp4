package edu.luc.etl.cs313.android.simplecountdown.test.android;

import edu.luc.etl.cs313.android.simplecountdown.android.CountdownAdapter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Concrete Robolectric test subclass. For the Gradle unitTest task to work,
 * the Robolectric dependency needs to be isolated here instead of being present in src/main.
 *
 * @author laufer
 * @see http://pivotal.github.com/robolectric
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23)
public class CountDownActivityRobolectric extends AbstractCountDownActivityTest {

    private static String TAG = "stopwatch-android-activity-robolectric";

    private CountdownAdapter activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(CountdownAdapter.class).create().start().visible().get();
    }

    @Override
    protected CountdownAdapter getActivity() {
        return activity;
    }

    @Override
    protected void runUiThreadTasks() {
        // Robolectric requires us to run the scheduled tasks explicitly!
        org.robolectric.shadows.ShadowLooper.runUiThreadTasks();
    }
}
