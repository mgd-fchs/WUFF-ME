package at.tugraz.software22.domain.repository;

import at.tugraz.software22.domain.entity.User;

public interface UserRepository {
    boolean registerUser(User user);
}
