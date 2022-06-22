package at.tugraz.software22.domain.repository;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import java.io.File;

public interface UserRepository {
    void registerUser(Executor exec, User user);
    void loginUser(Executor exec, User user);
    void logout();
    void setUserType(UserType userType);
    MutableLiveData<UserState> getUserState();
    User getLoggedInUser();
    void setLoggedInUser(User user);
    void updateUser(User user) throws UserNotLoggedInException;
    void addPicture(Uri pictureUri) throws UserNotLoggedInException;
    MutableLiveData<List<String>> getPictures();


}
