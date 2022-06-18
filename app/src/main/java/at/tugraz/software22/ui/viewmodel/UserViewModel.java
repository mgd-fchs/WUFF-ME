package at.tugraz.software22.ui.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;

import java.io.File;
import java.util.concurrent.Executor;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import at.tugraz.software22.domain.repository.PictureRepository;
import at.tugraz.software22.domain.repository.UserRepository;
import at.tugraz.software22.domain.service.UserService;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userService;
    private final PictureRepository pictureRepository;
    private final Executor executor;

    public UserViewModel(Application application) {
        super(application);

        WuffApplication userApplication = (WuffApplication) application;
        userService = userApplication.getUserService();
        executor =  userApplication.getBackgroundExecutor();
        pictureRepository = userApplication.getPictureService();
    }

    public void registerUser(User users, Uri imageUri) {
        executor.execute(() -> {
            this.userService.registerUser(executor, users);
            if (imageUri != null){
                try {
                    this.userService.addPicture(imageUri);
                } catch (UserNotLoggedInException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loginUser(User users) {
        executor.execute(() -> this.userService.loginUser(executor, users));
    }

    public UserRepository getUserService(){
        return userService;
    }

    public PictureRepository getPictureService(){
        return pictureRepository;
    }

    public void logout() {
        executor.execute(this.userService::logout);
    }

}
