package at.tugraz.software22;


import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.core.StringContains.containsString;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.domain.service.MatcherService;
import at.tugraz.software22.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private UserRepository userRepositoryMock;
    private MatcherService matcherService;

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
        Espresso.onView(ViewMatchers.withId(R.id.users_swipe_deck)).perform(ViewActions.swipeLeft());

        Espresso.onView(ViewMatchers.withText(user.getUsername()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenInterestingUser_whenActivityStarted_thenVerifyProfilePictureIsSet(){
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

        Espresso.onView(ViewMatchers.withId(R.id.imageViewInterestingUser))
                .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void givenLoggedInUser_whenSwipeLeft_thenVerifySnackbarIsDisplayed(){
        ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.users_swipe_deck)).perform(ViewActions.swipeLeft());
        Espresso.onView(Matchers.allOf(ViewMatchers.withText(containsString("User Swiped Left")))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
