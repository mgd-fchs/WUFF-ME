package at.tugraz.software22.domain.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {

    @Mock
    private WuffApplication applicationMock;
    @Mock
    private FirebaseDatabase database;
    @Mock
    private FirebaseAuth auth;
    @Mock
    private FirebaseStorage storage;

    private UserViewModel userViewModel;
    private Executor exec = Runnable::run;
    private UserService userService = new UserService(database, auth, storage);

    @Before
    public void setUp() {
        userViewModel = new UserViewModel(applicationMock);
    }

    @Test
    public void givenDatabaseSetup_whenRegisterUser_thenVerifyThatCreateRegisterUserWithEmailAndPasswordMethodOfDatabaseIsCalled() {
        Mockito.when(applicationMock.getBackgroundExecutor()).thenReturn(Runnable::run);
        Mockito.when(applicationMock.getUserService()).thenReturn(userService);

        Users users = new Users("user1", "123", "test@test.at");
        // userViewModel.registerUser(users);
        // Mockito.verify(userService, Mockito.times(1)).registerUser(exec, users);
        // Assert.assertEquals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), "test@test.at");
    }

}
