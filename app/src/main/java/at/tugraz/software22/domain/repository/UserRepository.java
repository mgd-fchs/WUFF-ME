package at.tugraz.software22.domain.repository;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;

public interface UserRepository {
    void registerUser(User user);
    void setUserType(String username, UserType userType);
}
