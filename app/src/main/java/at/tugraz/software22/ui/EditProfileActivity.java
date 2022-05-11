package at.tugraz.software22.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import at.tugraz.software22.WuffMeApplication;
import at.tugraz.software22.databinding.ActivityEditProfileBinding;
import at.tugraz.software22.domain.entity.User;

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

        binding.textViewUserName.setText(user.getUsername());
        binding.imageButtonEditUserName.setOnClickListener( it -> {
            if(binding.editTextUserName.isEnabled()){
                binding.editTextUserName.setEnabled(false);
                user.setUsername(binding.editTextUserName.getText().toString());
                userRepository.updateUser(user);
            } else {
                binding.editTextUserName.setEnabled(true);
            }
        });
    }
}
