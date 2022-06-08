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

import java.util.concurrent.Executor;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class MatcherServiceTest {
    @Mock
    private WuffApplication applicationMock;

    @Mock
    private FirebaseStorage firebaseStorage;



    @Mock
    private UserRepository userService;

    private MatcherService matcherService;



    @Before
    public void setUp() {
        matcherService = new MatcherService(userService);

    }

    @Test
    public void givenUserAndUsertypeSearcher_whenGetNextInterestingProfile_thenUserGetsANewInterestingOwnerProfile() {
        User testUser = new User("Testuser", "123456", "Developer");
        testUser.setType(UserType.SEARCHER);
        Mockito.when(userService.getLoggedInUser()).thenReturn(testUser);
        User userResult = matcherService.getNextInterestingProfile();
        Assert.assertTrue(checkUserType(UserType.OWNER, userResult.getType()));
    }

    Boolean checkUserType(UserType expType, UserType givenType) {
        return expType == givenType || UserType.BOTH == givenType;
    }
}
