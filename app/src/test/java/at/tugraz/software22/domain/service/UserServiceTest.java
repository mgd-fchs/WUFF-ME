package at.tugraz.software22.domain.service;

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

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private FirebaseStorage firebaseStorage;

    @Mock
    private FirebaseDatabase database;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(database, firebaseStorage);
    }

    @Test
    public void givenUserAndUsertype_whenSettingUsertype_thenUserHasNewType() {
        UserType type = UserType.OWNER;

        String uid = "h6MVwVQvlZOy6FeJh9us88aTNvu1";
        userService.loggedInUser = new User("Testuser");
        userService.loggedInUserUid = uid;

        DatabaseReference reference = Mockito.mock(DatabaseReference.class);
        DatabaseReference childReference = Mockito.mock(DatabaseReference.class);
        Mockito.when(reference.child(Mockito.any())).thenReturn(childReference);
        Mockito.when(database.getReference()).thenReturn(reference);

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
    public void givenDatabaseWithOneUser_whenUserUpdated_thenLoggedInUserIsUpdated() {
        String uid = "h6MVwVQvlZOy6FeJh9us88aTNvu1";
        User updatedUser = new User();
        updatedUser.setUsername("Testuser");
        updatedUser.setJob("Developer");
        updatedUser.setBirthday(LocalDate.now());
        userService.loggedInUserUid = uid;

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
