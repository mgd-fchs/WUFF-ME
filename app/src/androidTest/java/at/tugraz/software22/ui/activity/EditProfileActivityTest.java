package at.tugraz.software22;

import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import at.tugraz.software22.R;
import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.PictureRepository;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.EditProfileActivity;
import at.tugraz.software22.ui.LoginActivity;

@RunWith(AndroidJUnit4.class)
public class EditProfileActivityTest {

    private UserRepository userRepositoryMock;
    private PictureRepository pictureRepositoryMock;

    private Resources resources;

    @Before
    public void setUp() {
        pictureRepositoryMock = Mockito.mock(PictureRepository.class);
        userRepositoryMock = Mockito.mock(UserRepository.class);
        WuffApplication.setPictureRepository(pictureRepositoryMock);
        WuffApplication.setUserRepository(userRepositoryMock);
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Intents.init();
    }

    @After
    public void After(){
        Intents.release();
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
        ActivityScenario.launch(EditProfileActivity.class).recreate();

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
        ActivityScenario.launch(EditProfileActivity.class).recreate();

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
        mockUser(user);

        ActivityScenario.launch(EditProfileActivity.class);

        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.imageViewProfilePicture)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    private void mockUser(User user){
        ArrayList<String> picPaths = new ArrayList<String>();
        picPaths.add("images/E0VlMPMgOAWjE5SHn9OJ3W1HWk82/1655894930");
        Mockito.when(user.getPicturePaths()).thenReturn(picPaths);
        Mockito.when(user.getBirthday()).thenReturn(LocalDate.now());
        Mockito.when(user.getUsername()).thenReturn("Testboi");
        Mockito.when(user.getJob()).thenReturn("Dogwalker");
        Mockito.when(user.getType()).thenReturn(UserType.SEARCHER);
    }

    @Test
    public void givenLoggedInUser_whenProfilePictureSet_thenVerifyThatImageButtonsArePresent(){
        User user = Mockito.mock(User.class);
        mockUser(user);
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        ActivityScenario.launch(EditProfileActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.image_button_edit_add_profile_picture_from_gallery)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(ViewMatchers.withId(R.id.image_button_edit_add_profile_picture)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
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
    public void givenEditProfile_whenClickOnNewProfilePictureFromGallery_thenVerifyGalleryIntentLaunched() {
        User user = Mockito.mock(User.class);
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        mockUser(user);

        ArrayList<String> picPaths = new ArrayList<String>();
        picPaths.add("images/3Bf2xH09ahd9nLia4keNxIOo9vi1/1654684549");

        ActivityScenario.launch(EditProfileActivity.class);
        ArgumentCaptor<MutableLiveData<byte[]>> liveData = ArgumentCaptor.forClass(MutableLiveData.class);
        Mockito.verify(pictureRepositoryMock).downloadPicture(Mockito.eq(picPaths.get(0)), liveData.capture());

        liveData.getValue().postValue(new byte[1]);

        Espresso.onView(ViewMatchers.withId(R.id.imageViewProfilePicture))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        Instrumentation.ActivityResult imgCaptureResult = createImageUploadActivityResultStub();
        Intents.intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(imgCaptureResult);

        Espresso.onView(ViewMatchers.withId(R.id.image_button_edit_add_profile_picture_from_gallery))
                .perform(ViewActions.click());

        Intents.intended(hasAction(Intent.ACTION_GET_CONTENT),times(1));
    }

    @Test
    public void givenEditProfile_whenClickOnNewProfilePictureFromCamera_thenVerifyCameraIntentLaunched() {
        User user = Mockito.mock(User.class);
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(user);
        mockUser(user);

        ActivityScenario.launch(EditProfileActivity.class);

        Instrumentation.ActivityResult imgCaptureResult = createImageCaptureActivityResultStub();
        Intents.intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgCaptureResult);

        Espresso.onView(ViewMatchers.withId(R.id.image_button_edit_add_profile_picture))
                .perform(ViewActions.click());

        Intents.intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE),times(1));
    }
}
