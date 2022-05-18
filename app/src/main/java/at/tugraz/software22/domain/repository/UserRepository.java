package at.tugraz.software22.domain.repository;

import java.util.concurrent.Executor;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.entity.Users;

public interface UserRepository {
    void registerUser(Executor exec, Users user);
    void setUserType(UserType userType);
}
