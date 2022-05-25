package at.tugraz.software22.domain.service;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.ui.LoginActivity;
import at.tugraz.software22.ui.MainActivity;

public class UserService implements UserRepository {
    final FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    private static final String TAG = "test";
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();

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
                            System.out.println("registration failed");

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            registrationSuccess.postValue(false);
                        }
                    }
                });

    }

    @Override
    public void loginUser(Executor exec, Users user) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(exec, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            registrationSuccess.postValue(true);

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            registrationSuccess.postValue(false);

                        }
                    }
                });
    }

    public void logout() {
        mAuth.signOut();
        registrationSuccess.postValue(false);
    }

}
