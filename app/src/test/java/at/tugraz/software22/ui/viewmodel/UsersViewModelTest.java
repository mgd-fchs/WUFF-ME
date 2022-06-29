package at.tugraz.software22.ui.viewmodel;

import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import android.net.Uri;

import java.io.File;
import java.util.concurrent.Executor;

import at.tugraz.software22.R;
import at.tugraz.software22.WuffApplication;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.PictureRepository;
import at.tugraz.software22.domain.service.AuthenticateService;
import at.tugraz.software22.domain.service.MatcherService;
import at.tugraz.software22.domain.service.PictureService;
import at.tugraz.software22.domain.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UsersViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Executor currentThreadExecutor = Runnable::run;

    @Mock
    private WuffApplication applicationMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private AuthenticateService authenticateService;

    @Mock
    private PictureService pictureService;

    @Mock
    private MatcherService matcherService;

    private UserViewModel userViewModel;

    @Before
    public void setUp() {
        Mockito.when(applicationMock.getUserService()).thenReturn(userServiceMock);
        Mockito.when(applicationMock.getPictureService()).thenReturn(pictureService);
        Mockito.when(applicationMock.getBackgroundExecutor()).thenReturn(currentThreadExecutor);
        Mockito.when(applicationMock.getAuthenticateService()).thenReturn(authenticateService);
        Mockito.when(applicationMock.getMatcherService()).thenReturn(matcherService);

        userViewModel = new UserViewModel(applicationMock);
    }

    @Test
    public void givenLoggedInUser_whenAddPictureToLoggedInUserCalledWithInvalidUri_thenUserServiceIsNotCalled(){
        Uri uri = Uri.EMPTY;
        userViewModel.addPictureToLoggedInUser(uri);
        Mockito.verify(userServiceMock, Mockito.times(0))
                .addPicture(Mockito.any(Uri.class));
    }

    @Test
    public void givenLoggedInUser_whenGetUserLiveDataCalled_thenReturnsUserLiveDataFromUserService(){
        User user = new User();
        user.setId("12345abcdef");
        Mockito.when(userServiceMock.getLoggedInUser()).thenReturn(user);
        User returnedUser = userViewModel.getUserLiveData().getValue();

        Mockito.verify(userServiceMock, Mockito.times(1))
                .getLoggedInUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user.getId(), returnedUser.getId());
    }

    @Test
    public void givenUserWantsToRegister_whenRegisterCalled_thenAuthenticationServiceIsCalled() {
        userViewModel.registerUser("test@test.test","test1234", "testuser");
        Mockito.verify(authenticateService, Mockito.times(1))
                .registerUser(Mockito.eq("test@test.test"), Mockito.eq("test1234"), Mockito.eq("testuser"), Mockito.any(MutableLiveData.class), Mockito.any(MutableLiveData.class));
    }

    @Test
    public void givenUserHasAccount_whenLogin_thenAuthenticationServiceIsCalled() {
        userViewModel.loginUser("test@test.test","test1234");
        Mockito.verify(authenticateService, Mockito.times(1))
                .loginUser(Mockito.eq("test@test.test"), Mockito.eq("test1234"), Mockito.any(MutableLiveData.class), Mockito.any(MutableLiveData.class));
    }

    @Test
    public void givenUserIsLoggedIn_whenLogsOut_thenAuthenticationServiceIsCalled() {
        userViewModel.logout();
        Mockito.verify(authenticateService, Mockito.times(1)).logout(userViewModel.getUserStateMutableLiveData());
    }

    @Test
    public void givenUserIsLoggedIn_whenUserWantsToFindNextInterestingUser_thenMatcherServiceIsCalled() {
        User user = new User();
        userViewModel.loadNextInterestingUser(user);
        Mockito.verify(matcherService, Mockito.times(1))
                .getNextInterestingProfile(Mockito.any(), Mockito.eq(user));
    }

    @Test
    public void givenUserIsLoggedIn_whenUserWantsToSeeHisPicture_thenUserServiceIsCalled() {
        String path = "Any Path";
        userViewModel.loadPicture(path);
        Mockito.verify(pictureService, Mockito.times(1))
                .downloadPicture(Mockito.eq(path), Mockito.any());
    }

    @Test
    public void givenUserIsLoggedIn_whenUserInformationIsUpdated_thenUserServiceIsCalled() {
        User newUser = new User();
        userViewModel.updateUser(newUser);
        Mockito.verify(userServiceMock, Mockito.times(1))
                .updateUser(newUser);
    }
}
