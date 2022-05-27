package at.tugraz.software22;

import android.app.Application;

import androidx.annotation.VisibleForTesting;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import at.tugraz.software22.domain.service.UserService;

public class WuffApplication extends Application {

    private static UserService userService;
    private static Executor backgroundExecutor;

    private FirebaseDatabase createDatabaseInstance() {
        return FirebaseDatabase.getInstance();
    }

    private FirebaseAuth createAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserService(createDatabaseInstance(), createAuthInstance());
        }
        return userService;
    }

    public Executor getBackgroundExecutor() {
        if (backgroundExecutor == null) {
            backgroundExecutor = Executors.newFixedThreadPool(4);
        }
        return backgroundExecutor;
    }

    @VisibleForTesting
    public static void setBackgroundExecutor(Executor testBackgroundExecutor) {
        backgroundExecutor = testBackgroundExecutor;
    }
}
