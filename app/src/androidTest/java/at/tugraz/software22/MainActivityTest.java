package at.tugraz.software22;


import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.service.MatcherService;
import at.tugraz.software22.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MatcherService matcherService;

    @Before
    public void setUp() {
        matcherService = Mockito.mock(MatcherService.class);
    }

    @Test
    public void givenInterestingUser_whenActivityStarted_thenVerifyThatUserNameIsDisplayed() {
        User user = new User();
        user.setUsername("Testuser");

        Mockito.when(matcherService.getNextInterestingProfile()).thenReturn(new MutableLiveData<>(user));

        ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withText(user.getUsername()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
