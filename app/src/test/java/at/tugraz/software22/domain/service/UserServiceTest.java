package at.tugraz.software22.domain.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.Assert;
import org.junit.Before;
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
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private WuffApplication applicationMock;

    @Mock
    private FirebaseStorage firebaseStorage;

    @Mock
    private FirebaseDatabase database;
    @Mock
    private FirebaseAuth mAuth;
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(database, mAuth, firebaseStorage);
    }

    @Test
    public void givenUserAndUsertype_whenSettingUsertype_thenUserHasNewType() throws UserNotLoggedInException {
        UserType type = UserType.OWNER;

        String uid = "h6MVwVQvlZOy6FeJh9us88aTNvu1";
        User testUser = new User("Testuser", "123456", "Developer");
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        Mockito.when(firebaseUser.getUid()).thenReturn(uid);
        Mockito.when(mAuth.getCurrentUser()).thenReturn(firebaseUser);

        DatabaseReference reference = Mockito.mock(DatabaseReference.class);
        DatabaseReference childReference = Mockito.mock(DatabaseReference.class);
        Mockito.when(reference.child(Mockito.any())).thenReturn(childReference);
        Mockito.when(database.getReference()).thenReturn(reference);

        userService.loggedInUser = testUser;
        userService.setUserType(type);

        Mockito.verify(childReference).updateChildren(Mockito.argThat(arg -> {
            if(arg.containsKey(uid))
            {
                return ((User)arg.get(uid)).getType() == type;
            }
            return false;
        }));
    }

    @Test
    public void givenDatabaseWithOneUser_whenUserUpdated_thenLoggedInUserIsUpdated() throws UserNotLoggedInException {
        String uid = "h6MVwVQvlZOy6FeJh9us88aTNvu1";
        User updatedUser = new User();
        updatedUser.setUsername("Testuser");
        updatedUser.setJob("Developer");
        updatedUser.setBirthday(LocalDate.now());
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        Mockito.when(firebaseUser.getUid()).thenReturn(uid);
        Mockito.when(mAuth.getCurrentUser()).thenReturn(firebaseUser);

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
