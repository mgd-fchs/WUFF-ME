package at.tugraz.software22.domain.service;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticateServiceTest {


    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private FirebaseAuth firebaseAuth;

    @Mock
    private UserRepository userService;

    private AuthenticateService authenticateService;

    @Before
    public void setUp() {
        authenticateService = new AuthenticateService(firebaseAuth, userService);
    }


    @Test
    public void givenMailAndPassword_whenLoginUser_thenSignInWithEmailAndPasswordIsCalled() {
        String email = "user@yahoo.at";
        String password = "123456";
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        MutableLiveData<UserState> userStateMutableLiveData = new MutableLiveData<>();

        Task<AuthResult> authResultTask = Mockito.mock(Task.class);

        Mockito.when(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(authResultTask);

        authenticateService.loginUser(email, password, userMutableLiveData, userStateMutableLiveData);
        Mockito.verify(firebaseAuth).signInWithEmailAndPassword(email,password);
    }

    @Test
    public void givenMailAndPassword_whenRegisterUser_thenCreateUserWithEmailAndPasswordIsCalled() {
        String email = "user@yahoo.at";
        String password = "123456";
        String username = "hannes";
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        MutableLiveData<UserState> userStateMutableLiveData = new MutableLiveData<>();

        Task<AuthResult> authResultTask = Mockito.mock(Task.class);

        Mockito.when(firebaseAuth.createUserWithEmailAndPassword(email, password)).thenReturn(authResultTask);

        authenticateService.registerUser(email, password, username, userMutableLiveData, userStateMutableLiveData);
        Mockito.verify(firebaseAuth).createUserWithEmailAndPassword(email,password);
    }

}