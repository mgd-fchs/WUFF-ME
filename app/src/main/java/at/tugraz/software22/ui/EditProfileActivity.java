package at.tugraz.software22.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import at.tugraz.software22.R;
import at.tugraz.software22.databinding.ActivityEditProfileBinding;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private UserViewModel userViewModel;
    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        currentUser = userViewModel.getUserLiveData().getValue();

        binding.imageButtonEditUserName.setOnClickListener( it -> {
            if(binding.editTextUserName.isEnabled()){
                binding.editTextUserName.setEnabled(false);
                String newName = binding.editTextUserName.getText().toString().trim();
                if (newName.isEmpty()){
                    binding.editTextUserName.setError(getString(R.string.edit_profile_name_empty_error));
                } else {
                    currentUser.setUsername(binding.editTextUserName.getText().toString());
                    userViewModel.updateUser(currentUser);
                }
            } else {
                binding.editTextUserName.setEnabled(true);
                binding.editTextUserName.requestFocus();
            }
        });

        binding.imageButtonEditAge.setOnClickListener(view -> {
            if(binding.editTextAge.isEnabled()) {
                LocalDate date;
                try{
                    date = LocalDate.parse(binding.editTextAge.getText().toString());
                } catch (DateTimeParseException e){
                    binding.editTextAge.setError(getString(R.string.edit_profile_age_error));
                    return;
                }
                currentUser.setBirthday(date);
                userViewModel.updateUser(currentUser);
                binding.editTextAge.setEnabled(false);
            } else {
                binding.editTextAge.setEnabled(true);
                binding.editTextAge.requestFocus();
            }
        });

        binding.imageButtonEditJob.setOnClickListener(view -> {
            if(binding.editTextJob.isEnabled()){
                binding.editTextJob.setEnabled(false);
                currentUser.setJob(binding.editTextJob.getText().toString());
                userViewModel.updateUser(currentUser);
            } else {
                binding.editTextJob.setEnabled(true);
                binding.editTextJob.requestFocus();
            }
        });

        userViewModel.getUserLiveData().observe(this, user -> {
            currentUser = user;

            if (!user.getPicturePaths().isEmpty()) {
                String path = user.getPicturePaths().get(0);
                userViewModel.getPictureLiveData().observe(this, bytes -> {
                    Bitmap profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    binding.imageViewProfilePicture.setImageBitmap(profilePicture);
                    binding.imageViewProfilePicture.setVisibility(View.VISIBLE);
                });
                userViewModel.loadPicture(path);
            } else {
                binding.imageViewProfilePicture.setVisibility(View.GONE);
            }

            binding.textViewUserName.setText(user.getUsername());
            binding.textViewUserType.setText(user.getType().toString());
            binding.editTextUserName.setText(user.getUsername());

            if (user.getBirthday() != null) {
                binding.editTextAge.setText(user.getBirthday().toString());
            }

            if (user.getJob() != null) {
                binding.editTextJob.setText(user.getJob());
            }

        });

    }
}
