package at.tugraz.software22.ui.activity;

import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import at.tugraz.software22.R;
import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.UsertypeSelectionActivity;

/**
 * Instrumented test, which will execute on an Android device and tests the CreateActivity.
 * For details see http://d.android.com/tools/testing
 */
@RunWith(AndroidJUnit4.class)
public class UsertypeSelectionTest {

    @Rule
    public ActivityScenarioRule<UsertypeSelectionActivity> activityScenarioRule =
            new ActivityScenarioRule<>(UsertypeSelectionActivity.class);

    private UserRepository userRepositoryMock;
    private Resources resources;

    @Before
    public void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        WuffApplication.setUserRepository(userRepositoryMock);
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenUsertypeSelectionActivity_whenActivityLaunched_thenAllElementsPresent()
    {
        Espresso.onView(ViewMatchers.withText(R.string.checkBoxOwner)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.checkBoxSearcher)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.buttonSelectUsertype)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.userTypeInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenUsertypeSelectionActivity_whenActivityLaunched_thenInfoTextContainsTextFromOwnerBox() {

        Espresso.onView(ViewMatchers.withText(R.string.userTypeInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        String infoText = resources.getString(R.string.userTypeInfo);
        String owner = resources.getString(R.string.checkBoxOwner);

        Assert.assertTrue(infoText.contains(owner));
    }

    @Test
    public void givenUsertypeSelectionActivity_whenActivityLaunched_thenInfoTextContainsTextFromSearcherBox() {

        Espresso.onView(ViewMatchers.withText(R.string.userTypeInfo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        String infoText = resources.getString(R.string.userTypeInfo);
        String searcher = resources.getString(R.string.checkBoxSearcher);

        Assert.assertTrue(infoText.contains(searcher));
    }

    @Test
    public void givenUserAndUserType_whenSettingUserType_thenUserHasNewType() {

        ActivityScenario.launch(UsertypeSelectionActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.checkBoxOwner)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSelectUsertype)).perform(ViewActions.click());

        Mockito.verify(userRepositoryMock).setUserType(UserType.OWNER);
    }
}
