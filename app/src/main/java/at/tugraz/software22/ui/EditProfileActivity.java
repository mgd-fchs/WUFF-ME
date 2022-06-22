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
import at.tugraz.software22.ui.viewmodel.EditProfileViewModel;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private EditProfileViewModel viewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        if (!viewModel.getCurrentUser().getPicturePaths().isEmpty()){
            String path = viewModel.getCurrentUser().getPicturePaths().get(0);
            userViewModel.getPictureLiveData().observe(this, bytes -> {
                Bitmap profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                binding.imageViewProfilePicture.setImageBitmap(profilePicture);
                binding.imageViewProfilePicture.setVisibility(View.VISIBLE);
            });
            userViewModel.loadPicture(path);
        } else {
            binding.imageViewProfilePicture.setVisibility(View.GONE);
        }

        binding.textViewUserName.setText(viewModel.getUsername());
        binding.textViewUserType.setText(viewModel.getUserTyp());

        binding.editTextUserName.setText(viewModel.getUsername());
        binding.imageButtonEditUserName.setOnClickListener( it -> {
            if(binding.editTextUserName.isEnabled()){
                binding.editTextUserName.clearFocus();
                binding.editTextUserName.setEnabled(false);
                String newName = binding.editTextUserName.getText().toString().trim();
                if (newName.isEmpty()){
                    binding.editTextUserName.setError(getString(R.string.edit_profile_name_empty_error));
                } else {
                    viewModel.updateUserName(binding.editTextUserName.getText().toString());
                }
            } else {
                binding.editTextUserName.setEnabled(true);
                binding.editTextUserName.requestFocus();
            }
        });
        if (viewModel.getUserBirthday() != null) {
            binding.editTextAge.setText(viewModel.getUserBirthday().toString());
        }
        binding.imageButtonEditAge.setOnClickListener(view -> {
            if(binding.editTextAge.isEnabled()) {
                LocalDate date;
                try{
                    date = LocalDate.parse(binding.editTextAge.getText().toString());
                } catch (DateTimeParseException e){
                    binding.editTextAge.setError(getString(R.string.edit_profile_age_error));
                    return;
                }
                binding.editTextAge.setEnabled(false);
                viewModel.updateUserBirthday(date);
            } else {
                binding.editTextAge.setEnabled(true);
                binding.editTextAge.requestFocus();
            }
        });

        binding.editTextJob.setText(viewModel.getUserJob());
        binding.imageButtonEditJob.setOnClickListener(view -> {
            if(binding.editTextJob.isEnabled()){
                binding.editTextJob.setEnabled(false);
                viewModel.updateUserJob(binding.editTextJob.getText().toString());
            } else {
                binding.editTextJob.setEnabled(true);
                binding.editTextJob.requestFocus();
            }
        });
    }
}
