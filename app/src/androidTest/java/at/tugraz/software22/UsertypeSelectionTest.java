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

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.tugraz.software22.R;
import at.tugraz.software22.ui.LoginActivity;
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
        Espresso.onView(ViewMatchers.withText(R.string.userTypeInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenUsertypeSelectionActivity_whenActivityLaunched_thenInfoTextContainsTextFromOwnerBox() {

        Espresso.onView(ViewMatchers.withText(R.string.userTypeInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        String infoText = resources.getString(R.string.userTypeInfo);
        String owner = resources.getString(R.string.checkBoxOwner);

        Assert.assertTrue(infoText.contains(owner));
    }

    @Test
    public void givenUsertypeSelectionActivity_whenActivityLaunched_thenInfoTextContainsTextFromSearcherBox() {

        Espresso.onView(ViewMatchers.withText(R.string.userTypeInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        String infoText = resources.getString(R.string.userTypeInfo);
        String searcher = resources.getString(R.string.checkBoxSearcher);

        Assert.assertTrue(infoText.contains(searcher));
    }

    @Test
    public void givenUserAndUserType_whenSettingUserType_thenUserHasNewType() throws InterruptedException {

        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText("user"));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("test@test.at"+Math.random()*1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        // We call sleep method, because of asynchronous firebase call.
        Thread.sleep(2000);

        Espresso.onView(ViewMatchers.withText(R.string.checkBoxOwner)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.checkBoxSearcher)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.buttonSelectUsertype)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.userTypeInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.checkBoxOwner)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSelectUsertype)).perform(ViewActions.click());

        var x = FirebaseAuth.getInstance().getCurrentUser();

        Assert.assertEquals("OWNER", FirebaseAuth.getInstance().getCurrentUser().zze());
    }
}
