package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import at.tugraz.software22.R;
import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.ui.LoginActivity;
import at.tugraz.software22.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {



    @BeforeClass
    public static void beforeClass() {
        Executor currentThreadExecutor = Runnable::run;
        WuffApplication.setBackgroundExecutor(currentThreadExecutor);
    }


    @Rule public Timeout timeout = new Timeout(120000, TimeUnit.MILLISECONDS);
    @Rule
    public ActivityScenarioRule<LoginActivity> rule = new ActivityScenarioRule<>(LoginActivity.class);


    @Test
    public void givenSuccessfulRegistration_whenRegisterButtonPressed_thenDisplayMainActivity() throws InterruptedException {
        Intents.init();
        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText("user"));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at"+Math.random()*1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        // We call sleep method, because of asynchronous firebase call.
        Thread.sleep(2000);
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();

        Espresso.onView(ViewMatchers.withId(R.id.logout)).perform(ViewActions.click());
    }

    @Test
    public void givenNoUsername_whenRegisterButtonPressed_thenVerifyWarning() {
        ActivityScenario.launch(LoginActivity.class);
        String expectedWarning = "Please enter a username!";
        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at"+Math.random()*1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.username)).check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedWarning)));
    }

    @Test
    public void givenExistingUser_whenLoginButtonPressed_thenVerifyThatCurrentUserIsSet() throws InterruptedException {
        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at"));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());
        // We call sleep method, because of asynchronous firebase call.
        Thread.sleep(2000);
        Assert.assertEquals("email@yahoo.at", FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

}
