package at.tugraz.software22.domain.repository;

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
    MutableLiveData<List<String>> getPictures();
    void getUser(String userUid, MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData);
    void createUser(String userUid, String username, MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData);
}
