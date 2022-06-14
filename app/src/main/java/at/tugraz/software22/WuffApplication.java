package at.tugraz.software22;

import android.app.Application;

import androidx.annotation.VisibleForTesting;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import at.tugraz.software22.domain.repository.PictureRepository;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.domain.service.MatcherService;
import at.tugraz.software22.domain.service.PictureService;
import at.tugraz.software22.domain.service.UserService;

public class WuffApplication extends Application {

    private static UserRepository userRepository;
    private static PictureRepository pictureRepository;
    private static MatcherService matcherService;
    private static Executor backgroundExecutor;

    private FirebaseDatabase createDatabaseInstance() {
        return FirebaseDatabase.getInstance();
    }

    private FirebaseAuth createAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    private FirebaseStorage createFirebaseStorageInstance() {
        return FirebaseStorage.getInstance();
    }

    public UserRepository getUserService() {
        if (userRepository == null) {
            userRepository = new UserService(createDatabaseInstance(), createAuthInstance(), createFirebaseStorageInstance());
        }
        return userRepository;
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

    @VisibleForTesting
    public static void setUserRepository(UserRepository userRepository) {
        WuffApplication.userRepository = userRepository;
    }

    public PictureRepository getPictureService() {
        if (pictureRepository == null) {
            pictureRepository = new PictureService(createFirebaseStorageInstance());
        }
        return pictureRepository;
    }

    public MatcherService getMatcherService() {
        if (matcherService == null) {
            matcherService = new MatcherService(createDatabaseInstance(), getUserService());
        }
        return matcherService;
    }
    @VisibleForTesting
    public static void setMatcherService(MatcherService matcherService) {
        WuffApplication.matcherService = matcherService;
    }
}
