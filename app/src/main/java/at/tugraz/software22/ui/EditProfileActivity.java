package at.tugraz.software22.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import at.tugraz.software22.R;
import at.tugraz.software22.databinding.ActivityEditProfileBinding;
import at.tugraz.software22.ui.viewmodel.EditProfileViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private EditProfileViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        binding.textViewUserName.setText(viewModel.getUsername());

        binding.editTextUserName.setText(viewModel.getUsername());
        binding.imageButtonEditUserName.setOnClickListener( it -> {
            if(binding.editTextUserName.isEnabled()){
                binding.editTextUserName.setEnabled(false);
                viewModel.updateUserName(binding.editTextUserName.getText().toString());
            } else {
                binding.editTextUserName.setEnabled(true);
                binding.editTextUserName.requestFocus();
            }
        });

        binding.editTextAge.setText(viewModel.getUserBirthday().toString());
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
