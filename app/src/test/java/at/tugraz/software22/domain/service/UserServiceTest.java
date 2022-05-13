package at.tugraz.software22.domain.service;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private WuffApplication wuffApplication;

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(wuffApplication);
    }

    @Test
    public void givenDatabaseSetup_whenRegisterUser_thenVerifyThatCreateRegisterUserWithEmailAndPasswordMethodOfDatabaseIsCalled() {
        User user = new User("user1", "123", "test@test.at");
        FirebaseAuth mAuth = Mockito.mock(FirebaseAuth.class);
        userRepository.registerUser(user);
        Mockito.verify(mAuth, Mockito.times(1)).createUserWithEmailAndPassword(user.getEmail(), user.getPassword());
    }

}
