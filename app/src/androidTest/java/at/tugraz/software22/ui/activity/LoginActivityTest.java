package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
import at.tugraz.software22.ui.UsertypeSelectionActivity;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private Resources resources;

    @BeforeClass
    public static void beforeClass() {
        Executor currentThreadExecutor = Runnable::run;
        WuffApplication.setBackgroundExecutor(currentThreadExecutor);
    }


    @Rule
    public Timeout timeout = new Timeout(120000, TimeUnit.MILLISECONDS);
    @Rule
    public ActivityScenarioRule<LoginActivity> rule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void before(){
        Intents.init();
    }

    @After
    public void after(){
        Intents.release();
    }

    @Test
    public void givenSuccessfulRegistration_whenRegisterButtonPressed_thenDisplayMainActivity() throws InterruptedException {
        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText("user"));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at"+Math.random()*1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        // We call sleep method, because of asynchronous firebase call.
        Thread.sleep(2000);
        intended(hasComponent(UsertypeSelectionActivity.class.getName()));

        Espresso.onView(ViewMatchers.withId(R.id.checkBoxOwner)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSelectUsertype)).perform(ViewActions.click());

        Thread.sleep(2000);
        intended(hasComponent(MainActivity.class.getName()));

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
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("www@w.atw"));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        // We call sleep method, because of asynchronous firebase call.
        Thread.sleep(2000);

        Assert.assertNotNull(FirebaseAuth.getInstance().getCurrentUser());
        Assert.assertEquals("www@w.atw", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Espresso.onView(ViewMatchers.withId(R.id.logout)).perform(ViewActions.click());
    }

    @Test
    public void givenNewUser_whenSwitchToRegistration_thenVerifyThatProfilePictureUploadAppears(){
        ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());
    }

    @Test
    public void givenRegistration_whenClickOnNewProfilePicture_thenVerifyCameraViewAppears(){
        ActivityScenario.launch(LoginActivity.class);

        Instrumentation.ActivityResult imgCaptureResult = createImageCaptureActivityResultStub();
        Intents.intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgCaptureResult);

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.profile_picture_preview))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());
    }

    @Test
    public void givenRegistrationAndProfilePictureTaken_whenClickOnLoginSwitch_thenVerifyThatProfilePictureDisappears(){
        ActivityScenario.launch(LoginActivity.class);

        Instrumentation.ActivityResult imgCaptureResult = createImageCaptureActivityResultStub();
        Intents.intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgCaptureResult);

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.profile_picture_preview))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }


     private Instrumentation.ActivityResult createImageCaptureActivityResultStub() {
         Bundle bundle = new Bundle();
         bundle.putParcelable("IMG_DATA", BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher));
         Intent resultData = new Intent();
         resultData.putExtras(bundle);
         return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
     }

    private Instrumentation.ActivityResult createImageUploadActivityResultStub() {
        Intent resultData = new Intent();
        resultData.setData(Uri.parse("android.resource://at.tugraz.software22/" + R.drawable.software22));
        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
    }

    @Test
    public void givenRegistration_whenClickOnNewProfilePictureFromGallery_thenVerifyGetContentViewAppears(){
        ActivityScenario.launch(LoginActivity.class);

        Instrumentation.ActivityResult imgCaptureResult = createImageUploadActivityResultStub();
        Intents.intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(imgCaptureResult);

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture_from_gallery))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.profile_picture_preview))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());
    }

    @Test
    public void givenLoginActivity_whenSwitchedToRegistration_thenVerifyThatPictureUploadButtonsVisible(){
        ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture_from_gallery))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture_from_gallery))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());
    }

}
