package edu.luc.etl.cs313.android.simplecountdown.android;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;
import edu.luc.etl.cs313.android.simplecountdown.test.android.AbstractCountDownActivityTest;


/**
 * Concrete Android test subclass. Has to inherit from framework class
 * and uses delegation to concrete subclass of abstract test superclass.
 * IMPORTANT: project must export JUnit 4 to make it available on the
 * device.
 *
 * @author laufer
 * @see "https://developer.android.com/training/testing/ui-testing/"
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CountDownActivityTest extends AbstractCountDownActivityTest {

    @Rule
    public final ActivityTestRule<CountdownAdapter> activityRule =
            new ActivityTestRule<>(CountdownAdapter.class);

    @Override
    protected CountdownAdapter getActivity() {
        return activityRule.getActivity();
    }
}
