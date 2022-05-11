package at.tugraz.software22;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import at.tugraz.software22.data.User;
import at.tugraz.software22.data.UserRepository;
import at.tugraz.software22.ui.EditProfileActivity;

@RunWith(AndroidJUnit4.class)
public class EditProfileActivityTest {

    private static UserRepository userRepositoryMock;

    @BeforeClass
    public static void beforeClass(){
        userRepositoryMock = Mockito.mock(UserRepository.class);
    }

    @Test
    public void givenLoggedInUser_whenActivityStarted_thenVerifyThatUserNameIsDisplayed(){
        User user = new User(1, "Testuser");
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);
        Espresso.onView(ViewMatchers.withText(user.getName())).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
