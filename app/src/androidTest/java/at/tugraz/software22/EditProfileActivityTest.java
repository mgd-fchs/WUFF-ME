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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.EditProfileActivity;

@RunWith(AndroidJUnit4.class)
public class EditProfileActivityTest {

    private static UserRepository userRepositoryMock;

    private Resources resources;

    @Before
    public void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        WuffApplication.setUserRepository(userRepositoryMock);
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenLoggedInUser_whenActivityStarted_thenVerifyThatUserNameIsDisplayed(){
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.textViewUserName))
                .check(ViewAssertions.matches(ViewMatchers.withText(user.getUsername())));
    }

    @Test
    public void givenLoggedInUser_whenNameEditedAndSubmitted_thenVerifyThatUpdateUserIsCalledWithCorrectName() {
        String newUserName = "New Name";
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("SCRUM Master");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .perform(ViewActions.replaceText(newUserName));

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).updateUser(Mockito.argThat(u -> u.getUsername().equals(newUserName)) );
    }

    @Test
    public void givenLoggedInUser_whenAgeEditedAndSubmitted_thenVerifyThatUpdateUserIsCalledWithCorrectAge() {
        LocalDate newUserBirthday = LocalDate.of(2000,11,3);
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditAge))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextAge))
                .perform(ViewActions.replaceText(newUserBirthday.format(DateTimeFormatter.ISO_DATE)));


        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditAge))
                .perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).updateUser(Mockito.argThat(u -> u.getBirthday().equals(newUserBirthday)) );
    }

    @Test
    public void givenLoggedInUser_whenInvalidAgeSubmitted_thenVerifyThatErrorMessageIsDisplayed() {
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
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
    public void givenLoggedInUser_whenJobEditedAndSubmitted_thenVerifyThatUpdateUserIsCalledWithCorrectJob() {
        String newJob = "Scrum Master";
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditJob))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextJob))
                .perform(ViewActions.replaceText(newJob));

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditJob))
                .perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).updateUser(Mockito.argThat(u -> u.getJob().equals(newJob)));
    }

    @Test
    public void givenLoggedInUser_whenEmptyNameSubmitted_thenVerifyThatErrorMessageIsDisplayed() {
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
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
    public void givenLoggedInUser_whenEditNameButtonClicked_thenEditTextUserNameGetsFocus() {
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditUserName))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextUserName))
                .check(ViewAssertions.matches(ViewMatchers.hasFocus()));
    }

    @Test
    public void givenLoggedInUser_whenEditAgeButtonClicked_thenEditTextAgeGetsFocus() {
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditAge))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextAge))
                .check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextAge))
                .check(ViewAssertions.matches(ViewMatchers.hasFocus()));
    }

    @Test
    public void givenLoggedInUser_whenEditJobButtonClicked_thenEditTextJobGetsFocus() {
        User user = new User();
        user.setUsername("Testuser");
        user.setJob("Developer");
        user.setBirthday(LocalDate.now());
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.imageButtonEditJob))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextJob))
                .check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextJob))
                .check(ViewAssertions.matches(ViewMatchers.hasFocus()));
    }

    @Test
    public void givenLoggedInUser_whenProfilePictureSet_thenVerifyPictureIsVisibleInProfile() throws InterruptedException {
        User user = Mockito.mock(User.class);
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);

        ArrayList<String> picPaths = new ArrayList<String>();
        picPaths.add("images/3Bf2xH09ahd9nLia4keNxIOo9vi1/1654684549");

        Mockito.when(user.getPicturePaths()).thenReturn(picPaths);
        Mockito.when(user.getBirthday()).thenReturn(LocalDate.now());
        Mockito.when(user.getUsername()).thenReturn("Testboi");
        Mockito.when(user.getJob()).thenReturn("Dogwalker");

        ActivityScenario.launch(EditProfileActivity.class);

        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.imageViewProfilePicture)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }
}
