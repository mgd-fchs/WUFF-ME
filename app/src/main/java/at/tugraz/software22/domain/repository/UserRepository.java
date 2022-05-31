package at.tugraz.software22.domain.repository;

import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;

public interface UserRepository {
    void registerUser(Executor exec, Users user);
    void loginUser(Executor exec, Users user);
    void logout();
    void setUserType(UserType userType);
    Users getLoggedInUser();
    void updateUser(Users user) throws UserNotLoggedInException;
}
