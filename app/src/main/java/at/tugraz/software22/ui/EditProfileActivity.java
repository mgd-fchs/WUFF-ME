package at.tugraz.software22.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import at.tugraz.software22.R;
import at.tugraz.software22.WuffMeApplication;
import at.tugraz.software22.data.UserRepository;
import at.tugraz.software22.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WuffMeApplication wuffMeApplication = (WuffMeApplication) getApplication();
        var userRepository = wuffMeApplication.getUserRepository();
        var user = userRepository.getLoggedInUser();

        binding.textViewUserName.setText(user.getName());
    }
}
