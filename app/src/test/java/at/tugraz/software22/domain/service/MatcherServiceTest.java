package at.tugraz.software22.domain.service;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class MatcherServiceTest {
    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private FirebaseDatabase firebaseDatabase;

    private MatcherService matcherService;



    @Before
    public void setUp() {
        matcherService = new MatcherService(firebaseDatabase);

    }

    @Test
    public void givenUserAndUsertypeSearcher_whenGetNextInterestingProfile_thenUserGetsANewInterestingOwnerProfile() {
        User testUser = new User("Testuser");
        testUser.setType(UserType.SEARCHER);

        DatabaseReference reference = Mockito.mock(DatabaseReference.class);
        DatabaseReference childrenReference = Mockito.mock(DatabaseReference.class);
        Task<DataSnapshot> dataSnapshotTask = Mockito.mock(Task.class);
        ArgumentCaptor<OnSuccessListener<DataSnapshot>> successListener = ArgumentCaptor.forClass(OnSuccessListener.class);
        Mockito.when(childrenReference.get()).thenReturn(dataSnapshotTask);
        Mockito.when(reference.child(Constants.USER_TABLE)).thenReturn(childrenReference);
        Mockito.when(firebaseDatabase.getReference()).thenReturn(reference);

        DataSnapshot userData = Mockito.mock(DataSnapshot.class);
        DataSnapshot userSnapshot = Mockito.mock(DataSnapshot.class);
        User user = new User();
        user.setType(UserType.OWNER);
        Mockito.when(userSnapshot.getValue(User.class)).thenReturn(user);
        Iterable<DataSnapshot> children = List.of(userSnapshot);
        Mockito.when(userData.getChildren()).thenReturn(children);


        MutableLiveData<User> userResult = new MutableLiveData<>();
        matcherService.getNextInterestingProfile(userResult, testUser.getType());
        userResult.observeForever(u -> Assert.assertTrue(checkUserType(UserType.OWNER, u.getType())));
        Mockito.verify(dataSnapshotTask).addOnSuccessListener(successListener.capture());
        successListener.getValue().onSuccess(userData);
    }

    Boolean checkUserType(UserType expType, UserType givenType) {
        return expType == givenType || UserType.BOTH == givenType;
    }
}
