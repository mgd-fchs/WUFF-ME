package at.tugraz.software22.domain.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {
    DatabaseReference databaseReference;
    final FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;

    public UserService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
    }

    @Override
    public void registerUser(User user) {

    }

    @Override
    public void setUserType(String username, UserType userType) {
        ref.child(Constants.USER_TABLE).child("-N1mLLkwu95ZlEFqYVi6").child("type").setValue(userType);
    }
}
