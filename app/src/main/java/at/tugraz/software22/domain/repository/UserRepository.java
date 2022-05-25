package at.tugraz.software22.domain.repository;

import java.io.File;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.entity.Users;

public interface UserRepository {
    void registerUser(Executor exec, Users user);
    Users getLoggedInUser();
    void uploadProfilePicture(File picture);
    File getProfilePicture();
}
