package at.tugraz.software22.domain.repository;

import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.domain.enums.UserType;

public interface UserRepository {
    void registerUser(Executor exec, Users user);
    void loginUser(Executor exec, Users user);
    void logout();
    void setUserType(UserType userType);
}
