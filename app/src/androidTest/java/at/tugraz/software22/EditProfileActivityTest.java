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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.EditProfileActivity;

@RunWith(AndroidJUnit4.class)
public class EditProfileActivityTest {

    private static UserRepository userRepositoryMock;

    private Resources resources;

    @Before
    public void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        WuffMeApplication.setUserRepository(userRepositoryMock);
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenLoggedInUser_whenActivityStarted_thenVerifyThatUserNameIsDisplayed(){
        User user = new User("Testuser",  LocalDate.now(), "Developer");
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.textViewUserName))
                .check(ViewAssertions.matches(ViewMatchers.withText(user.getUsername())));
    }

    @Test
    public void givenLoggedInUser_whenNameEditedAndSubmitted_thenVerifyThatUpdateUserIsCalledWithCorrectName() throws UserNotLoggedInException {
        String newUserName = "New Name";
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(new User("Testuser", LocalDate.now(), "SCRUM Master"));
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .perform(ViewActions.replaceText(newUserName));

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).updateUser(Mockito.argThat(user -> user.getUsername().equals(newUserName)) );
    }

    @Test
    public void givenLoggedInUser_whenAgeEditedAndSubmitted_thenVerifyThatUpdateUserIsCalledWithCorrectAge() throws UserNotLoggedInException {
        LocalDate newUserBirthday = LocalDate.of(2000,11,3);
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(new User("Testuser", LocalDate.now(), "Developer"));
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditAge))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextAge))
                .perform(ViewActions.replaceText(newUserBirthday.format(DateTimeFormatter.ISO_DATE)));


        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditAge))
                .perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).updateUser(Mockito.argThat(user -> user.getBirthday().equals(newUserBirthday)) );
    }

    @Test
    public void givenLoggedInUser_whenInvalidAgeSubmitted_thenVerifyThatErrorMessageIsDisplayed() {
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(new User("Testuser", LocalDate.now(), "Developer"));
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditAge))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextAge))
                .perform(ViewActions.replaceText("1900"));

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditAge))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextAge))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(resources.getString(R.string.edit_profile_age_error))));
    }

    @Test
    public void givenLoggedInUser_whenJobEditedAndSubmitted_thenVerifyThatUpdateUserIsCalledWithCorrectJob() throws UserNotLoggedInException {
        String newJob = "Scrum Master";
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(new User("Testuser", LocalDate.now(), "Developer"));
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditJob))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextJob))
                .perform(ViewActions.replaceText(newJob));

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditJob))
                .perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).updateUser(Mockito.argThat(user -> user.getJob().equals(newJob)) );
    }

    @Test
    public void givenLoggedInUser_whenEmptyNameSubmitted_thenVerifyThatErrorMessageIsDisplayed() throws UserNotLoggedInException {
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(new User("Testuser", LocalDate.now(), "Developer"));
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(resources.getString(R.string.edit_profile_name_empty_error))));
        Mockito.verify(userRepositoryMock, Mockito.never()).updateUser(Mockito.any());
    }

    @Test
    public void givenLoggedInUser_whenEditNameButtonClicked_thenEnableEditTextUserNameAndChangeButtonImage() {
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(new User("Testuser", LocalDate.now(), "Developer"));
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .check(ViewAssertions.matches(ViewMatchers.hasFocus()));
        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .check(ViewAssertions.matches(ViewMatchers.hasBackground(R.drawable.ic_baseline_check_24)));
    }

}
