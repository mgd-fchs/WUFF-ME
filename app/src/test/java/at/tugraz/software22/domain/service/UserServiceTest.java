package at.tugraz.software22.domain.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.MockHandler;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.HashMap;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private FirebaseDatabase database;
    @Mock
    private FirebaseAuth auth;

    @Before
    public void setUp(){
        userService = new UserService(database, auth);
    }

    @Test
    public void givenDatabaseWithOneUser_whenUserUpdated_thenLoggedInUserIsUpdated(){
        String uid = "h6MVwVQvlZOy6FeJh9us88aTNvu1";
        User updatedUser = new User("Testuser", LocalDate.now(), "Developer");
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        Mockito.when(firebaseUser.getUid()).thenReturn(uid);
        Mockito.when(auth.getCurrentUser()).thenReturn(firebaseUser);

        DatabaseReference reference = Mockito.mock(DatabaseReference.class);
        DatabaseReference childReference = Mockito.mock(DatabaseReference.class);
        Mockito.when(reference.child(Mockito.any())).thenReturn(childReference);
        Mockito.when(database.getReference()).thenReturn(reference);

        userService.updateUser(updatedUser);
        User loggedInUser = userService.getLoggedInUser();

        Assert.assertEquals(updatedUser.getUsername(), loggedInUser.getUsername());
        Assert.assertEquals(updatedUser.getBirthday(), loggedInUser.getBirthday());
        Assert.assertEquals(updatedUser.getJob(), loggedInUser.getJob());
        Mockito.verify(childReference).updateChildren(Mockito.argThat(arg -> arg.containsKey(uid)));
    }

}
