package at.tugraz.software22.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import java.io.File;

public interface UserRepository {
    void setUserType(UserType userType);
    User getLoggedInUser();
    void updateUser(User user);
    void addPicture(File picture);
    MutableLiveData<List<String>> getPictures();
    void getUser(String userUid, MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData);
    void createUser(String userUid, String username, MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData);
}
