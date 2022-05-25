package at.tugraz.software22.domain.service;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;

public class UserService implements UserRepository {
    final FirebaseDatabase database;
    private FirebaseAuth mAuth;
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
                .addOnCompleteListener(exec, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");

                            DatabaseReference usersRef = database.getReference().child(Constants.USER_TABLE);
                            Map<String, Object> userMap = new HashMap<>();
                            FirebaseUser fireBaseUser = mAuth.getCurrentUser();

                            userMap.put(fireBaseUser.getUid(), users);
                            usersRef.updateChildren(userMap);

                            registrationSuccess.postValue(true);

                        } else {
                            // TODO: Create toast
                            System.out.println("registration failed");

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            registrationSuccess.postValue(false);
                        }
                    }
                });

    }

    @Override
    public void setUserType(UserType userType) {
        database.getReference().child(Constants.USER_TABLE).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("type").setValue(userType);
    }

}
