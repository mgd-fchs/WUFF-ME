package at.tugraz.software22.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

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
        binding.imageButtonEditUserName.setOnClickListener( it -> {
            if(binding.editTextUserName.isEnabled()){
                binding.editTextUserName.setEnabled(false);
                viewModel.updateUserName(binding.editTextUserName.getText().toString());
            } else {
                binding.editTextUserName.setEnabled(true);
                binding.editTextAge.requestFocus();
            }
        });

        binding.editTextAge.setText(viewModel.getUserBirthday().toString());
        binding.imageButtonEditAge.setOnClickListener(view -> {
            if(binding.editTextAge.isEnabled()) {
                binding.editTextAge.setEnabled(false);
                viewModel.updateUserBirthday(LocalDate.parse(binding.editTextAge.getText().toString()));
            } else {
                binding.editTextAge.setText("");
                binding.editTextAge.setEnabled(true);
                binding.editTextAge.requestFocus();
            }
        });
    }
}
