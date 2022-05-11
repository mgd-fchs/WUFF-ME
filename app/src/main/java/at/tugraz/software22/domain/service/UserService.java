package at.tugraz.software22.domain.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {
    DatabaseReference databaseReference;

    public UserService() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.DATABASE_URL);
    }

    @Override
    public void registerUser(User user) {

    }

    @Override
    public User getLoggedInUser() {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }
}
