package at.tugraz.software22.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;

public interface UserRepository {
    void registerUser(Executor exec, User user);
    void loginUser(Executor exec, User user);
    void logout();
    void setUserType(UserType userType);
    MutableLiveData<UserState> getUserState();
    User getLoggedInUser();
    void updateUser(User user) throws UserNotLoggedInException;
}
