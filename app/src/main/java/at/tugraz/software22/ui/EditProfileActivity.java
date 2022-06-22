package at.tugraz.software22.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

import at.tugraz.software22.R;
import at.tugraz.software22.databinding.ActivityEditProfileBinding;
import at.tugraz.software22.ui.viewmodel.EditProfileViewModel;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private EditProfileViewModel viewModel;
    private UserViewModel userViewModel;
    private Uri imageUri = null;
    private ImageView profilePicturePreview;

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    this::onCreateActivityResult);
    private final ActivityResultLauncher<Intent> activityResultSelectImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    this::onImageSelectedResult);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        profilePicturePreview = binding.imageViewProfilePicture;

        binding.imageButtonEditAddProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "JPEG_" + timeStamp;
                File storageDirectory = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File profilePictureFile = File.createTempFile(
                            fileName,
                            ".jpg",
                            storageDirectory
                    );
                    imageUri = FileProvider.getUriForFile(
                            getApplicationContext(),
                            "at.tugraz.software22.WuffApplication.provider",
                            profilePictureFile);
                    dispatchTakePictureIntent(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.imageButtonEditAddProfilePictureFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent galleryPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryPictureIntent.setType("image/*");
                    activityResultSelectImageLauncher.launch(galleryPictureIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (!viewModel.getCurrentUser().getPicturePaths().isEmpty()){
            String path = viewModel.getCurrentUser().getPicturePaths().get(0);
            userViewModel.getPictureService().downloadPicture(path).observe(this, bytes -> {
                Bitmap profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                binding.imageViewProfilePicture.setImageBitmap(profilePicture);
                binding.imageViewProfilePicture.setVisibility(View.VISIBLE);
            });
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

    private void dispatchTakePictureIntent(Uri savedPicture) {
        Intent createTakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        createTakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, savedPicture);
        activityResultLauncher.launch(createTakePictureIntent);
    }

    private void onCreateActivityResult(ActivityResult result){
        if(result.getResultCode() == RESULT_OK){
            profilePicturePreview.setImageURI(imageUri);
        }
    }

    private void onImageSelectedResult(ActivityResult result){
        if(result.getResultCode() == RESULT_OK){
            Uri selectedImage = result.getData().getData();
            imageUri = selectedImage;
            profilePicturePreview.setImageURI(selectedImage);
        }
    }
}
