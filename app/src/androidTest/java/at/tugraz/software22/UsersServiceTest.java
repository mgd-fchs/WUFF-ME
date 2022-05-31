package at.tugraz.software22;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.concurrent.Executor;

import at.tugraz.software22.domain.service.UserService;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

@RunWith(AndroidJUnit4.class)
public class UsersServiceTest {

//    @Rule
//    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Executor currentThreadExecutor = Runnable::run;

    @Mock
    private WuffApplication applicationMock;

    @Mock
    private UserService userServiceMock;

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
}
