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
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private UserViewModel userViewModel;
    private User currentUser;
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
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        currentUser = userViewModel.getUserLiveData().getValue();
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

//        if (!viewModel.getCurrentUser().getPicturePaths().isEmpty()){
//            String path = viewModel.getCurrentUser().getPicturePaths().get(0);
//            userViewModel.getPictureService().downloadPicture(path).observe(this, bytes -> {
//                Bitmap profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                binding.imageViewProfilePicture.setImageBitmap(profilePicture);
//                binding.imageViewProfilePicture.setVisibility(View.VISIBLE);
//            });
//        } else {
//            binding.imageViewProfilePicture.setVisibility(View.GONE);
//        }

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
