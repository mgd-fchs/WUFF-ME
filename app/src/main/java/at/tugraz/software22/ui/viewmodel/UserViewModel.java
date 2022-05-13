package at.tugraz.software22.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.concurrent.Executor;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.domain.service.UserService;

public class UserViewModel extends AndroidViewModel {

    private final UserService userService;
    private final Executor executor;
    private static boolean ret;
    private static String username;

    public UserViewModel(Application application) {
        super(application);

        WuffApplication userApplication = (WuffApplication) application;
        userService = userApplication.getUserService();
        executor =  userApplication.getBackgroundExecutor();
    }

    public void loadData() {
        executor.execute(() -> {

        });
    }

    public void registerUser(Users users) {

        executor.execute(() -> {
            this.userService.registerUser(executor, users);
        });
    }

    public UserService getUserService(){
        return userService;
    }

}
