package at.tugraz.software22;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.tugraz.software22.ui.LoginActivity;

@RunWith(AndroidJUnit4.class)
public class ChangeLanguageTest {

    private Resources resources;
    private Context context;

    @Test
    public void givenAppStartedWithGermanLanguage_whenLoginActivityStarted_thenVerifyThatAllElementsAreInGerman() {

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
    public void givenAppStartedWithEnglishLanguage_whenLoginActivityStarted_thenVerifyThatAllElementsAreInEnglish() {

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
    public void givenAppStartedWithGermanLanguage_whenLoginActivityStartedWithRegisterToggle_thenVerifyThatAllElementsAreInGerman() {

        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "de");
        resources = context.getResources();

        String expectedHeader = "Software22 - Gruppenarbeit";
        String expectedEmailHint = "Email";
        String expectedPasswordHint = "Passwort";
        String expectedSignInButton = "Einloggen";
        String expectedRegisterToggle = "Registrieren";
        String expectedUsernameHint = "Nutzername";

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedUsernameHint, resources.getString(R.string.prompt_username));
        Assert.assertEquals(expectedEmailHint, resources.getString(R.string.prompt_email));
        Assert.assertEquals(expectedPasswordHint, resources.getString(R.string.prompt_password));
        Assert.assertEquals(expectedSignInButton, resources.getString(R.string.action_sign_in));
        Assert.assertEquals(expectedRegisterToggle, resources.getString(R.string.action_register));
    }

    @Test
    public void givenAppStartedWithEnglishLanguage_whenLoginActivityStartedWithRegisterToggle_thenVerifyThatAllElementsAreInEnglish() {

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

    @Test
    public void givenAppStartedWithGermanLanguage_whenUserTypeSelectionActivityStarted_thenVerifyThatAllElementsAreInGerman() {

        ActivityScenario.launch(LoginActivity.class);
        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "de");
        resources = context.getResources();

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText("user"));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at" + Math.random() * 1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        String expectedHeader = "Software22 - Gruppenarbeit";
        String expectedSelectUserType = "Auswählen";
        String expectedOwnerType = "Besitzer";
        String expectedSearcherType = "Suchender";
        String expectedSelection = "Auswählen";
        String expectedHint = "ⓘ Wählen Sie \"" + expectedOwnerType + "\", \"" + expectedSearcherType + "\" oder beides, indem Sie das entsprechende Kontrollkästchen anklicken und auf den Button \"" + expectedSelection + "\" klicken.";

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedOwnerType, resources.getString(R.string.checkBoxOwner));
        Assert.assertEquals(expectedSearcherType, resources.getString(R.string.checkBoxSearcher));
        Assert.assertEquals(expectedSelectUserType, resources.getString(R.string.buttonSelectUsertype));
        Assert.assertEquals(expectedHint, resources.getString(R.string.userTypeInfo));
    }

    @Test
    public void givenAppStartedWithEnglishLanguage_whenUserTypeSelectionActivityStarted_thenVerifyThatAllElementsAreInEnglish() {

        ActivityScenario.launch(LoginActivity.class);
        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "en");
        resources = context.getResources();

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText("user"));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at" + Math.random() * 1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        String expectedHeader = "Software22 - Team project";
        String expectedOwnerBox = "Owner";
        String expectedSearcherBox = "Searcher";
        String expectedSelectUserType = "Select";

        String HintText = "ⓘ Select to be \"" + expectedOwnerBox + "\", \"" + expectedSearcherBox + "\" or both by clicking the corresponding checkbox and pressing the \"" + expectedSelectUserType + "\" button";

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedOwnerBox, resources.getString(R.string.checkBoxOwner));
        Assert.assertEquals(expectedSearcherBox, resources.getString(R.string.checkBoxSearcher));
        Assert.assertEquals(expectedSelectUserType, resources.getString(R.string.buttonSelectUsertype));
        Assert.assertEquals(HintText, resources.getString(R.string.userTypeInfo));
    }

    @Test
    public void givenAppStartedWithGermanLanguage_whenEditProfileActivityStarted_thenVerifyThatAllElementsAreInGerman() throws InterruptedException {

        ActivityScenario.launch(LoginActivity.class);
        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "de");
        resources = context.getResources();

        String expectedUsername = "user";
        String expectedHeader = "Software22 - Gruppenarbeit";
        String expectedEditProfileHeader = "Einstellungen";
        String expectedBirthdayTextField = "Geburtstag";
        String expectedJobTextField = "Beruf";

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText(expectedUsername));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at" + Math.random() * 1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.checkBoxOwner)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSelectUsertype)).perform(ViewActions.click());

        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.buttonEditProfile)).perform(ViewActions.click());

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedEditProfileHeader, resources.getString(R.string.edit_profile_header));
        Assert.assertEquals(expectedBirthdayTextField, resources.getString(R.string.edit_profile_birthday));
        Assert.assertEquals(expectedJobTextField, resources.getString(R.string.edit_profile_job));
    }

    @Test
    public void givenAppStartedWithEnglishLanguage_whenEditProfileActivityStarted_thenVerifyThatAllElementsAreInEnglish() throws InterruptedException {

        ActivityScenario.launch(LoginActivity.class);
        context = LocaleHelper.setLocale(InstrumentationRegistry.getInstrumentation().getTargetContext(), "en");
        resources = context.getResources();

        String expectedUsername = "user";
        String expectedHeader = "Software22 - Team project";
        String expectedEditProfileHeader = "Settings";
        String expectedBirthdayTextField = "Birthday";
        String expectedJobTextField = "Job";

        Espresso.onView(ViewMatchers.withId(R.id.toggle_register)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.clearText(), ViewActions.typeText(expectedUsername));
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.clearText(), ViewActions.typeText("email@yahoo.at" + Math.random() * 1000000));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.clearText(), ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.checkBoxOwner)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.buttonSelectUsertype)).perform(ViewActions.click());

        Thread.sleep(2000);
        Espresso.onView(ViewMatchers.withId(R.id.buttonEditProfile)).perform(ViewActions.click());

        Assert.assertEquals(expectedHeader, resources.getString(R.string.app_name));
        Assert.assertEquals(expectedEditProfileHeader, resources.getString(R.string.edit_profile_header));
        Assert.assertEquals(expectedBirthdayTextField, resources.getString(R.string.edit_profile_birthday));
        Assert.assertEquals(expectedJobTextField, resources.getString(R.string.edit_profile_job));
    }
}
