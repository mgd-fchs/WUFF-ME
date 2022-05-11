package at.tugraz.software22;

import android.app.Application;
import android.util.Log;

import androidx.annotation.VisibleForTesting;

import at.tugraz.software22.domain.repository.UserRepository;

public class WuffMeApplication extends Application {

    private static UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }


    @VisibleForTesting
    public static void setUserRepository(UserRepository testUserRepository) {
        Log.i("Application", testUserRepository.toString());
        userRepository = testUserRepository;
    }
}
