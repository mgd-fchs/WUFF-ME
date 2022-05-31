package at.tugraz.software22.domain.service;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {
    final FirebaseDatabase database;
    private final FirebaseAuth mAuth;
    private static final String TAG = "test";
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();

    protected Users loggedInUser;


    public UserService(FirebaseDatabase database, FirebaseAuth mAuth) {
        this.database = database;
        this.mAuth = mAuth;
    }

    public MutableLiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    @Override
    public void registerUser(Executor exec, Users users) {


        mAuth.createUserWithEmailAndPassword(users.getEmail(), users.getPassword())
                .addOnCompleteListener(exec, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");

                        DatabaseReference usersRef = database.getReference().child(Constants.USER_TABLE);
                        Map<String, Object> userMap = new HashMap<>();
                        FirebaseUser fireBaseUser = mAuth.getCurrentUser();

                        assert fireBaseUser != null;
                        userMap.put(fireBaseUser.getUid(), users);
                        usersRef.updateChildren(userMap);

                        registrationSuccess.postValue(true);

                        loggedInUser = users;

                    } else {
                        System.out.println("registration failed");

                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        registrationSuccess.postValue(false);
                    }
                });

    }

    @Override
    public void setUserType(UserType userType) {
        Map<String, Object> users = new HashMap<>();
        String uid = mAuth.getCurrentUser().getUid();
        loggedInUser.setType(userType);
        users.put(uid, loggedInUser);
        database.getReference().child(Constants.USER_TABLE).updateChildren(users);
    }

    @Override
    public void loginUser(Executor exec, Users user) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(exec, task -> {
                    if (task.isSuccessful()) {

                        Log.d(TAG, "signInWithEmail:success");
                        registrationSuccess.postValue(true);

                        loggedInUser = user;

                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        registrationSuccess.postValue(false);

                    }
                });
    }

    public void logout() {
        mAuth.signOut();
        registrationSuccess.postValue(false);
    }
}
