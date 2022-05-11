package at.tugraz.software22;

import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.EditProfileActivity;

@RunWith(AndroidJUnit4.class)
public class EditProfileActivityTest {

    private static UserRepository userRepositoryMock;

    private Resources resources;

    @BeforeClass
    public static void beforeClass(){
        userRepositoryMock = Mockito.mock(UserRepository.class);
        WuffMeApplication.setUserRepository(userRepositoryMock);
    }

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenLoggedInUser_whenActivityStarted_thenVerifyThatUserNameIsDisplayed(){
        User user = new User("Testuser");
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);
        Espresso.onView(ViewMatchers.withText(user.getUsername())).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenLoggedInUser_whenNameEditedAndSubmitted_thenVerifyThatUpdateUserIsCalled() {
        String newUserName = "New Name";
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(new User("Testuser"));
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .perform(ViewActions.typeText(newUserName));

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).updateUser(Mockito.argThat(user -> user.getUsername().equals(newUserName)) );
    }

}
