package at.tugraz.software22.domain.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {

    @Mock
    private WuffApplication applicationMock;

    @Mock
    private FirebaseDatabase database;
    @Mock
    private FirebaseAuth mAuth;
    private UserViewModel userViewModel;
    private Executor exec = Runnable::run;
    private UserService userService;

    @Before
    public void setUp() {
        userViewModel = new UserViewModel(applicationMock);
        userService = new UserService(database, mAuth);
    }

    @Test
    public void givenUserAndUsertype_whenSettingUsertype_thenUserHasNewType() {
        UserType type = UserType.OWNER;

        String uid = "h6MVwVQvlZOy6FeJh9us88aTNvu1";
        Users testUser = new Users("Testuser", "123456", "Developer");
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
                return ((Users)arg.get(uid)).getType() == type;
            }
            return false;
        }));
    }
}
