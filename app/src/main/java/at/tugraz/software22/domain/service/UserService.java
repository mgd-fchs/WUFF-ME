package at.tugraz.software22.domain.service;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;

import java.util.concurrent.Executor;

import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {
    final FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private final FirebaseAuth mAuth;
    private static final String TAG = "test";
    private final MutableLiveData<UserState> userState = new MutableLiveData<>();
    private final MutableLiveData<List<String>> picturePaths = new MutableLiveData<>();

    protected User loggedInUser;


    public UserService(FirebaseDatabase database, FirebaseAuth mAuth, FirebaseStorage firebaseStorage) {
        this.database = database;
        this.mAuth = mAuth;
        this.firebaseStorage = firebaseStorage;
    }

    @Override
    public MutableLiveData<UserState> getUserState() {
        return userState;
    }

    @Override
    public void registerUser(Executor exec, User users) {


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

                        userState.postValue(UserState.LOGGED_IN_FROM_REGISTRATION);

                        loggedInUser = users;

                    } else {
                        System.out.println("registration failed");

                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        userState.postValue(UserState.NOT_LOGGED_IN);
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
    public void loginUser(Executor exec, User user) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(exec, task -> {
                    if (task.isSuccessful()) {

                        Log.d(TAG, "signInWithEmail:success");
                        userState.postValue(UserState.LOGGED_IN_FROM_LOGIN);

                        loggedInUser = user;

                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        userState.postValue(UserState.NOT_LOGGED_IN);

                    }
                });
    }

    public void logout() {
        mAuth.signOut();
        userState.postValue(UserState.NOT_LOGGED_IN);
    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void setLoggedInUser(User user){
        loggedInUser = user;
    }

    @Override
    public void updateUser(User user) throws UserNotLoggedInException {
        Map<String, Object> users = new HashMap<>();
        users.put(getCurrentUserId(), user);
        database.getReference().child(Constants.USER_TABLE).updateChildren(users);
        loggedInUser = user;
    }

    @Override
    public void addPicture(Uri pictureUri) throws UserNotLoggedInException {
        String path = "images/" + getCurrentUserId() + "/" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        StorageReference riversRef = firebaseStorage.getReference().child(path);
        var uploadTask = riversRef.putFile(pictureUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                // todo toast warning
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                loggedInUser.addPicturePath(path);

                try {
                    updateUser(loggedInUser);
                } catch (UserNotLoggedInException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public MutableLiveData<List<String>> getPictures() {
        return picturePaths;
    }

    private String getCurrentUserId() throws UserNotLoggedInException {
        var currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            throw new UserNotLoggedInException();
        }
        return currentUser.getUid();
    }
}
