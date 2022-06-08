package at.tugraz.software22;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
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

    private Resources resources;
    private Context context;

    @Test
    public void givenAppStartedWithGermanLanguage_whenLoginActivityStarted_thenVerifyThatAllElementsAreInGerman(){

        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "de");
        resources = context.getResources();

        String expectedHeader = "Software22 - Gruppenarbeit";
        String expectedEmailHint = "Email";
        String expectedPasswordHint = "Passwort";
        String expectedSignInButton = "Einloggen";
        String expectedRegisterToggle = "Registrieren";

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedEmailHint, resources.getString(R.string.prompt_email));
        Assert.assertEquals(expectedPasswordHint, resources.getString(R.string.prompt_password));
        Assert.assertEquals(expectedSignInButton, resources.getString(R.string.action_sign_in));
        Assert.assertEquals(expectedRegisterToggle, resources.getString(R.string.action_register));
    }

    @Test
    public void givenAppStartedWithEnglishLanguage_whenLoginActivityStarted_thenVerifyThatAllElementsAreInEnglish(){

        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "en");
        resources = context.getResources();

        String expectedHeader = "Software22 - Team project";
        String expectedEmailHint = "Email";
        String expectedPasswordHint = "Password";
        String expectedSignInButton = "Sign in";
        String expectedRegisterToggle = "Register";

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedEmailHint, resources.getString(R.string.prompt_email));
        Assert.assertEquals(expectedPasswordHint, resources.getString(R.string.prompt_password));
        Assert.assertEquals(expectedSignInButton, resources.getString(R.string.action_sign_in));
        Assert.assertEquals(expectedRegisterToggle, resources.getString(R.string.action_register));
    }

    @Test
    public void givenAppStartedWithGermanLanguage_whenLoginActivityStartedWithRegisterToggle_thenVerifyThatAllElementsAreInGerman(){

        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "de");
        resources = context.getResources();

        String expectedHeader = "Software22 - Team project";
        String expectedUsernameHint = "Username";
        String expectedEmailHint = "Email";
        String expectedPasswordHint = "Password";
        String expectedSignInButton = "Sign in";
        String expectedRegisterToggle = "Register";

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedUsernameHint, resources.getString(R.string.prompt_username));
        Assert.assertEquals(expectedEmailHint, resources.getString(R.string.prompt_email));
        Assert.assertEquals(expectedPasswordHint, resources.getString(R.string.prompt_password));
        Assert.assertEquals(expectedSignInButton, resources.getString(R.string.action_sign_in));
        Assert.assertEquals(expectedRegisterToggle, resources.getString(R.string.action_register));
    }

    @Test
    public void givenAppStartedWithEnglishLanguage_whenLoginActivityStartedWithRegisterToggle_thenVerifyThatAllElementsAreInEnglish(){

        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "en");
        resources = context.getResources();

        String expectedHeader = "Software22 - Team project";
        String expectedUsernameHint = "Username";
        String expectedEmailHint = "Email";
        String expectedPasswordHint = "Password";
        String expectedSignInButton = "Sign in";
        String expectedRegisterToggle = "Register";

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedUsernameHint, resources.getString(R.string.prompt_username));
        Assert.assertEquals(expectedEmailHint, resources.getString(R.string.prompt_email));
        Assert.assertEquals(expectedPasswordHint, resources.getString(R.string.prompt_password));
        Assert.assertEquals(expectedSignInButton, resources.getString(R.string.action_sign_in));
        Assert.assertEquals(expectedRegisterToggle, resources.getString(R.string.action_register));
    }
}
