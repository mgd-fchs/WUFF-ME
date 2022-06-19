package at.tugraz.software22;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.domain.service.MatcherService;
import at.tugraz.software22.domain.service.UserService;
import at.tugraz.software22.ui.MainActivity;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private UserRepository userRepositoryMock;
    private MatcherService matcherService;
    private UserViewModel userViewModel;

    @Before
    public void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        WuffApplication.setUserRepository(userRepositoryMock);
        matcherService = Mockito.mock(MatcherService.class);
        WuffApplication.setMatcherService(matcherService);
    }

    @Test
    public void givenInterestingUser_whenActivityStarted_thenVerifyThatUserNameIsDisplayed() {
        User loggedInUser = new User();
        loggedInUser.setType(UserType.SEARCHER);
        Mockito.when(userRepositoryMock.getLoggedInUser()).thenReturn(loggedInUser);
        User user = new User();
        user.setUsername("Testuser");
        user.addPicturePath(" ");
        ArgumentCaptor<MutableLiveData<User>> liveData = ArgumentCaptor.forClass(MutableLiveData.class);


        ActivityScenario.launch(MainActivity.class);
        Mockito.verify(matcherService).getNextInterestingProfile(liveData.capture(), Mockito.eq(loggedInUser.getType()));
        liveData.getValue().postValue(user);
        Espresso.onView(ViewMatchers.withText(user.getUsername()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
