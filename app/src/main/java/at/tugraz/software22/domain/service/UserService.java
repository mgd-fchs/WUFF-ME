package at.tugraz.software22.domain.service;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {
    final FirebaseDatabase database;
    private final FirebaseStorage firebaseStorage;

    private final MutableLiveData<List<String>> picturePaths = new MutableLiveData<>();

    protected User loggedInUser;
    protected String loggedInUserUid;




    public UserService(FirebaseDatabase database, FirebaseStorage firebaseStorage) {
        this.database = database;
        this.firebaseStorage = firebaseStorage;
    }


    @Override
    public void setUserType(UserType userType) {
        Map<String, Object> users = new HashMap<>();
        loggedInUser.setType(userType);
        users.put(loggedInUserUid, loggedInUser);
        database.getReference().child(Constants.USER_TABLE).updateChildren(users);
    }


    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void updateUser(User user) {
        Map<String, Object> users = new HashMap<>();
        users.put(loggedInUserUid, user);
        database.getReference().child(Constants.USER_TABLE).updateChildren(users);
        loggedInUser = user;
    }

    @Override
    public void addPicture(File picture) {
        Uri file = Uri.fromFile(picture);
        String path = "images/" + loggedInUserUid + "/" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        StorageReference riversRef = firebaseStorage.getReference().child(path);
        var uploadTask = riversRef.putFile(file);


        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            // todo toast warning
        }).addOnSuccessListener(taskSnapshot -> {
            loggedInUser.addPicturePath(path);
            updateUser(loggedInUser);
        });
    }

    @Override
    public MutableLiveData<List<String>> getPictures() {
        return picturePaths;
    }

    @Override
    public void getUser(String userUid, MutableLiveData<User> userMutableLiveData, MutableLiveData<UserState> userStateMutableLiveData) {
        DatabaseReference userRef = database.getReference().child(Constants.USER_TABLE).child(userUid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    userMutableLiveData.postValue(user);
                    loggedInUserUid = userUid;
                    loggedInUser = user;
                    userStateMutableLiveData.postValue(UserState.LOGGED_IN_FROM_LOGIN);
                } else {
                    userStateMutableLiveData.postValue(UserState.NOT_LOGGED_IN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userStateMutableLiveData.postValue(UserState.NOT_LOGGED_IN);
            }
        });

    }

    @Override
    public void createUser(String userUid, String username, MutableLiveData<User> userMutableLiveData,
                           MutableLiveData<UserState> userStateMutableLiveData) {
        DatabaseReference usersRef = database.getReference().child(Constants.USER_TABLE);
        Map<String, Object> userMap = new HashMap<>();
        User user = new User();
        user.setUsername(username);
        userMap.put(userUid, user);
        usersRef.updateChildren(userMap).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                userMutableLiveData.postValue(user);
                this.loggedInUserUid = userUid;
                loggedInUser = user;
                userStateMutableLiveData.postValue(UserState.LOGGED_IN_FROM_REGISTRATION);
            } else {
                userStateMutableLiveData.postValue(UserState.NOT_LOGGED_IN);
            }
        });

    }


}
