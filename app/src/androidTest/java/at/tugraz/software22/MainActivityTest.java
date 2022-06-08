package at.tugraz.software22;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.tugraz.software22.ui.LoginActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Resources resources;
    private Context context;

    @Test
    public void givenMainActivity_whenActivityStarted_thenNavigationPresent() throws InterruptedException {
        ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText("user"));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at" + Math.random() * 1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.checkBoxOwner)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSelectUsertype)).perform(ViewActions.click());
        Thread.sleep(2000);

        Espresso.onView(ViewMatchers.withText(R.string.button_swiping)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.button_matches)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.button_messages)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}
