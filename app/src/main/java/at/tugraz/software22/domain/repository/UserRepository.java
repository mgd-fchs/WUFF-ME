package at.tugraz.software22.domain.repository;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.List;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.enums.UserType;

public interface UserRepository {
    void setUserType(UserType userType);
    User getLoggedInUser();
    void updateUser(User user);
    void addPicture(File picture);
    void setLoggedInUser(User user);
    void updateUser(User user) throws UserNotLoggedInException;
    void addPicture(Uri pictureUri) throws UserNotLoggedInException;
    MutableLiveData<List<String>> getPictures();
    void getUser(String userUid, MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData);
    void createUser(String userUid, String username, MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData);
}
