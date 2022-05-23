package at.tugraz.software22.domain.repository;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;

public interface UserRepository {
    void registerUser(User user);
    User getLoggedInUser();
    void updateUser(User user) throws UserNotLoggedInException;
}
