package at.tugraz.software22.domain.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {
    final FirebaseDatabase database;
    private final FirebaseAuth mAuth;

    User loggedInUser;

    public UserService(FirebaseDatabase database, FirebaseAuth mAuth) {
        this.database = database;
        this.mAuth = mAuth;
    }

    @Override
    public void registerUser(User user) {

    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void updateUser(User user) throws UserNotLoggedInException {
        Map<String, Object> users = new HashMap<>();
        users.put(getCurrentUserId(), user);
        database.getReference().child(Constants.USER_TABLE).updateChildren(users);
        loggedInUser = user;
    }

    private String getCurrentUserId() throws UserNotLoggedInException {
        var currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            throw new UserNotLoggedInException();
        }
        return currentUser.getUid();
    }
}
