package at.tugraz.software22;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.tugraz.software22.R;
import at.tugraz.software22.ui.UsertypeSelectionActivity;

/**
 * Instrumented test, which will execute on an Android device and tests the CreateActivity.
 * For details see http://d.android.com/tools/testing
 */
@RunWith(AndroidJUnit4.class)
public class UsertypeSelectionTest {
    @Rule
    public ActivityScenarioRule<UsertypeSelectionActivity> activityScenarioRule =
            new ActivityScenarioRule<>(UsertypeSelectionActivity.class);

    private Resources resources;

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenUsertypeSelectionActivity_whenActivityLaunched_thenAllElementsPresent()
    {
        Espresso.onView(ViewMatchers.withText(R.string.checkBoxOwner)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.checkBoxSearcher)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.buttonSelectUsertype)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
