package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import at.tugraz.software22.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import at.tugraz.software22.ui.LoginActivity;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private Resources resources;

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Intents.init();
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
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

    }

     private Instrumentation.ActivityResult createImageCaptureActivityResultStub() {
         Bundle bundle = new Bundle();
         bundle.putParcelable("IMG_DATA", BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher));
         Intent resultData = new Intent();
         resultData.putExtras(bundle);

         return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
     }
}
