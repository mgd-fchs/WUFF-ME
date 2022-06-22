package at.tugraz.software22.ui.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.concurrent.Executor;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.service.UserService;

/**
 * This test should be implemented in Assignment 2
 * See https://tc.tugraz.at/main/mod/assign/view.php?id=253546
 */
@RunWith(MockitoJUnitRunner.class)
public class EditProfileViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private WuffApplication applicationMock;

    @Mock
    private UserService userServiceMock;

    /**
     * Class under test (already setup with test doubles in the setUp method).
     */
    private EditProfileViewModel editProfileViewModel;

    /**
     * Set up all test doubles (i.e., the applicationMock, the userServiceMock and
     * the currentThreadExecutor) and initialize a new UserViewModel (i.e., the class under test)
     * with those test doubles.
     * This method also starts the observation of the UserViewModel's live data. The executor
     * (i.e., the currentThreadExecutor) runs all tasks on the current thread, so there is no need
     * to wait for an asynchronous operation. Verify that a live data has changed by calling the
     * Mockito.verify(...LiveDataObserver).onChanged(expectedValue) method.
     */
    @Before
    public void setUp() {
        Mockito.when(applicationMock.getUserService()).thenReturn(userServiceMock);
        Mockito.when(userServiceMock.getLoggedInUser()).thenReturn(createDummyUser());
        editProfileViewModel = new EditProfileViewModel(applicationMock);
    }

    private User createDummyUser(){
        User user = new User("Test User");
        user.setType(UserType.OWNER);
        user.setBirthday(LocalDate.of(2022,1,1));
        user.setJob("Developer");
        return user;
    }

    @Test
    public void givenRegisteredUser_whenAskedForInformation_thenRespondWithCorrectInformation() {
        Assert.assertEquals(editProfileViewModel.getUserBirthday(), LocalDate.of(2022,1,1));
        Assert.assertEquals(editProfileViewModel.getUserTyp(), UserType.OWNER.toString());
        Assert.assertEquals(editProfileViewModel.getUserJob(), "Developer");
        Assert.assertEquals(editProfileViewModel.getUsername(), "Test User");
    }

    @Test
    public void givenRegisteredUser_whenUserNameChange_thenUpdateUserIsCalled() {
        User user = editProfileViewModel.getCurrentUser();
        user.setUsername("New Test User");
        editProfileViewModel.updateUserName("New Test User");
        Mockito.verify(userServiceMock, Mockito.times(1)).updateUser(user);
    }

    @Test
    public void givenRegisteredUser_whenBirthdayChange_thenUpdateUserIsCalled() {
        User user = editProfileViewModel.getCurrentUser();
        LocalDate today = LocalDate.now();
        user.setBirthday(today);
        editProfileViewModel.updateUserBirthday(today);
        Mockito.verify(userServiceMock, Mockito.times(1)).updateUser(user);
    }

    @Test
    public void givenRegisteredUser_whenJobChange_thenUpdateUserIsCalled() {
        User user = editProfileViewModel.getCurrentUser();
        user.setJob("New Job");
        editProfileViewModel.updateUserJob("New Job");
        Mockito.verify(userServiceMock, Mockito.times(1)).updateUser(user);
    }
}
