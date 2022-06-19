package at.tugraz.software22.domain.service;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.repository.UserRepository;

public class AuthenticateService {
    private final FirebaseAuth mAuth;
    private final UserRepository userService;
    private static final String TAG = "test";

    public AuthenticateService(FirebaseAuth mAuth, UserRepository userService) {
        this.mAuth = mAuth;
        this.userService = userService;
    }


    public void loginUser(String email, String password, MutableLiveData<User> userMutableLiveData,
                          MutableLiveData<UserState> userState) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        userService.getUser(mAuth.getCurrentUser().getUid(), userMutableLiveData, userState);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        userState.postValue(UserState.NOT_LOGGED_IN);

                    }
                });
    }

    public void logout(MutableLiveData<UserState> userState) {
        mAuth.signOut();
        userState.postValue(UserState.NOT_LOGGED_IN);
    }


    public void registerUser(String email, String password, String username,
                             MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData) {
        MutableLiveData<UserState> userState = new MutableLiveData<>();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        userState.postValue(UserState.LOGGED_IN_FROM_REGISTRATION);
                        userService.createUser(mAuth.getCurrentUser().getUid(), username,
                                userMutableLiveData, userStateMutableLiveData);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        userState.postValue(UserState.NOT_LOGGED_IN);
                    }
                });

    }
}
