package at.tugraz.software22;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.EditProfileActivity;
import at.tugraz.software22.ui.LoginActivity;


@RunWith(AndroidJUnit4.class)
public class ChangeLanguageTest {

    private static UserRepository userRepositoryMock;

    private Resources resources;

    @BeforeClass
    public static void beforeClass() {
        Executor currentThreadExecutor = Runnable::run;
        WuffApplication.setBackgroundExecutor(currentThreadExecutor);
    }

    private void setLocale(String language, String country) {
        Locale locale = new Locale(language, country);
        // here we update locale for date formatters
        Locale.setDefault(locale);
        // here we update locale for app resources
        Resources res = InstrumentationRegistry.getInstrumentation().getContext().getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }


    @Test
    public void givenAppStartedWithGermanLanguage_whenLoginActivityStarted_thenVerifyThatAllElementsAreInGerman(){
        setLocale("de", "DE");
        ActivityScenario.launch(LoginActivity.class);
        String expectedHeader = "Software22 - Gruppenarbeit";
        String expectedEmailHint = "Email";
        String expectedPasswordHint = "Passwort";
        String expectedSignInButton = "Einloggen";
        String expectedRegisterToggle = "Registrieren";

       Espresso.onView(ViewMatchers.withText(expectedHeader))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedEmailHint))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedPasswordHint))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedSignInButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedRegisterToggle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
