package at.tugraz.software22.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.time.LocalDate;

import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import at.tugraz.software22.domain.repository.UserRepository;

public class EditProfileViewModel extends AndroidViewModel {

    private User currentUser;
    private UserRepository userRepository;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        userRepository = ((WuffApplication)application).getUserService();
        currentUser = userRepository.getLoggedInUser();
    }

    public String getUsername(){
        return currentUser.getUsername();
    }

    public void updateUserName(String newUserName) throws UserNotLoggedInException {
        currentUser.setUsername(newUserName);
        userRepository.updateUser(currentUser);
    }

    public LocalDate getUserBirthday() {
        return currentUser.getBirthday();
    }

    public void updateUserBirthday(LocalDate newBirthday) throws UserNotLoggedInException {
        currentUser.setBirthday(newBirthday);
        userRepository.updateUser(currentUser);
    }

    public String getUserJob() {
        return currentUser.getJob();
    }

    public void updateUserJob(String newJob) throws UserNotLoggedInException {
        currentUser.setJob(newJob);
        userRepository.updateUser(currentUser);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
