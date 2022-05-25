package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import at.tugraz.software22.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import at.tugraz.software22.ui.LoginActivity;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

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

}
