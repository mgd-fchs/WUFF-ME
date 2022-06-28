package at.tugraz.software22.ui.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.concurrent.Executor;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.domain.repository.PictureRepository;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.domain.service.AuthenticateService;
import at.tugraz.software22.domain.service.MatcherService;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userService;
    private final PictureRepository pictureRepository;
    private final MatcherService matcherService;
    private final AuthenticateService authenticateService;
    private final Executor executor;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<UserState> userStateMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<User> nextInterestingUserLiveData = new MutableLiveData<>();
    private final MutableLiveData<byte[]> pictureLiveData = new MutableLiveData<>();

    public UserViewModel(Application application) {
        super(application);

        WuffApplication userApplication = (WuffApplication) application;
        userService = userApplication.getUserService();
        executor = userApplication.getBackgroundExecutor();
        pictureRepository = userApplication.getPictureService();
        matcherService = userApplication.getMatcherService();
        this.authenticateService = userApplication.getAuthenticateService();
    }

    public void addPictureToLoggedInUser(Uri imageUri){
        if (imageUri != null){
            this.userService.addPicture(imageUri);
        }
    }

    public MutableLiveData<User> getUserLiveData() {
        return new MutableLiveData<User>(userService.getLoggedInUser());
    }

    public UserRepository getUserService() {
        return userService;
    }

    public MatcherService getMatcherService() {
        return matcherService;
    }

    public void registerUser(String email, String password, String username) {
        executor.execute(() -> authenticateService.registerUser(email, password, username,
                userLiveData, userStateMutableLiveData));
    }

    public void loginUser(String email, String password) {
        executor.execute(() -> authenticateService.loginUser(email, password,
                userLiveData, userStateMutableLiveData));
    }

    public void logout() {
        executor.execute(() -> authenticateService.logout(userStateMutableLiveData));
    }

    public MutableLiveData<UserState> getUserStateMutableLiveData() {
        return userStateMutableLiveData;
    }

    public LiveData<User> getNextInterestingUserLiveData() {
        return nextInterestingUserLiveData;
    }

    public void loadNextInterestingUser() {
        executor.execute(() -> matcherService.getNextInterestingProfile(nextInterestingUserLiveData,
                userService.getLoggedInUser().getType()));
    }

    public LiveData<byte[]> getPictureLiveData() {
        return pictureLiveData;
    }

    public void loadPicture(String path) {
        executor.execute(() -> pictureRepository.downloadPicture(path, pictureLiveData));
    }

    public void updateUser(User newUser) {
        executor.execute(() -> {
            userService.updateUser(newUser);
            userLiveData.postValue(newUser);
        });

    }
}
