package at.tugraz.software22;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.domain.service.UserService;
import at.tugraz.software22.ui.LoginActivity;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

@RunWith(AndroidJUnit4.class)
public class UsersServiceTest {

//    @Rule
//    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private UserViewModel userViewModel;
    private Executor exec = Runnable::run;
    private UserService userService = new UserService(database, auth, storage);


    /**
     * Set up all test doubles (i.e., the applicationMock, the userServiceMock and
     * the currentThreadExecutor) and initialize a new UserViewModel (i.e., the class under test)
     * with those test doubles.
     * This method also starts the observation of the UserViewModel's live data. The executor
     * (i.e., the currentThreadExecutor) runs all tasks on the current thread, so there is no need
     * to wait for an asynchronous operation. Verify that a live data has changed by calling the
     * Mockito.verify(...LiveDataObserver).onChanged(expectedValue) method.
     */
//    @Before
//    public void setUp() {
////        Mockito.when(applicationMock.getUserService()).thenReturn(userServiceMock);
////        Mockito.when(applicationMock.getBackgroundExecutor()).thenReturn(currentThreadExecutor);
//
//        userViewModel = new UserViewModel(get);
//    }
//
//    @Test
//    public void givenDatabaseSetup_whenRegisterUser_thenVerifyThatCreateRegisterUserWithEmailAndPasswordMethodOfDatabaseIsCalled() {
//        User user = new User("user1", "123", "test@test.at");
//        userViewModel.registerUser(user);
//        Assert.assertEquals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), "test@test.at");
//    }

    @Test
    public void givenLoggedInUser_whenProfilePictureUploaded_thenVerifyProfilePictureHasChanged() throws IOException {
        userService.loggedInUser = new Users("testuser","1234", "user@user.com");

        File picture = File.createTempFile("testProfilePicture", ".png");
        String oldProfilePicture = userService.getLoggedInUser().getProfilePicture();

        userService.uploadProfilePicture(picture);
        String newProfilePicture = userService.getLoggedInUser().getProfilePicture();

        Assert.assertNotEquals(oldProfilePicture, newProfilePicture);
    }

    @Test
    public void givenNewUser_whenSwitchToRegistration_thenVerifyThatProfilePictureUploadAppears(){
        ActivityScenario.launch(LoginActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.image_button_add_profile_picture))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}
