package at.tugraz.software22.domain.service;

import com.google.firebase.database.FirebaseDatabase;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {

    public UserService(WuffApplication application) {

    }

    public boolean registerUser(User user) {
        return false;
    }
}
