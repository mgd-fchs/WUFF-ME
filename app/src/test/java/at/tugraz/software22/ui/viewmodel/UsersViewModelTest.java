package at.tugraz.software22.ui.viewmodel;

import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

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
import at.tugraz.software22.domain.service.AuthenticateService;
import at.tugraz.software22.domain.service.MatcherService;
import at.tugraz.software22.domain.service.UserService;

/**
 * This test should be implemented in Assignment 2
 * See https://tc.tugraz.at/main/mod/assign/view.php?id=253546
 */
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
    private MatcherService matcherService;

    /**
     * Class under test (already setup with test doubles in the setUp method).
     */
    private UserViewModel userViewModel;

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
        Mockito.when(applicationMock.getBackgroundExecutor()).thenReturn(currentThreadExecutor);
        Mockito.when(applicationMock.getAuthenticateService()).thenReturn(authenticateService);
        Mockito.when(applicationMock.getMatcherService()).thenReturn(matcherService);

        userViewModel = new UserViewModel(applicationMock);
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
}
