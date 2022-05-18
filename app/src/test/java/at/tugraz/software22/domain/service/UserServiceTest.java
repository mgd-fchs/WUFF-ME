package at.tugraz.software22.domain.service;

import com.google.firebase.database.DatabaseReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.HashMap;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp(){
        userService = new UserService();
    }

    @Test
    public void givenDatabaseWithOneUser_whenUserUpdated_thenLoggedInUserIsUpdated(){
        User updatedUser = new User("Testuser", LocalDate.now(), "Developer");

        userService.updateUser(updatedUser);
        User loggedInUser = userService.getLoggedInUser();

        Assert.assertEquals(updatedUser.getUsername(), loggedInUser.getUsername());
        Assert.assertEquals(updatedUser.getBirthday(), loggedInUser.getBirthday());
        Assert.assertEquals(updatedUser.getJob(), loggedInUser.getJob());
    }

}
