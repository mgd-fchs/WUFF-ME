package at.tugraz.software22.ui;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private static Application wuffApp;
    private UserViewModel userViewModel;
    public static final String INTENT_USERNAME = "";
    private File profilePictureFile = null;

    private ImageView profilePicturePreview;

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    this::onCreateActivityResult);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.email);
        EditText passwordInput = findViewById(R.id.password);
        EditText usernameInput = findViewById(R.id.username);

        SwitchCompat toggleBtn =  findViewById(R.id.toggle_register);
        ImageView uploadImage = findViewById(R.id.image_button_add_profile_picture);
        profilePicturePreview = findViewById(R.id.profile_picture_preview);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        wuffApp = getApplication();

        Button loginBtn = findViewById(R.id.login_btn);

        uploadImage.setOnClickListener(view -> {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "JPEG_" + timeStamp;
            File storageDirectory = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                profilePictureFile = File.createTempFile(
                        fileName,
                        ".jpg",
                        storageDirectory
                );
                Uri imageUri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        "at.tugraz.software22.WuffApplication.provider",
                        profilePictureFile);
                dispatchTakePictureIntent(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadImage.setVisibility(View.VISIBLE);
            profilePicturePreview.setVisibility(View.VISIBLE);
        });

        toggleBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                usernameInput.setVisibility(View.VISIBLE);
                uploadImage.setVisibility(View.VISIBLE);
                loginBtn.setText(R.string.register_btn);
            } else {
                usernameInput.setVisibility(View.GONE);
                profilePicturePreview.setVisibility(View.GONE);
                uploadImage.setVisibility(View.GONE);
                loginBtn.setText(R.string.login_btn);
            }
        });


        loginBtn.setOnClickListener(v -> {
            String username = "";

            if (usernameInput.getVisibility() == View.VISIBLE) {
                username = usernameInput.getText().toString();

                if (username.isEmpty()) {
                    usernameInput.setError("Please enter a username!");
                    usernameInput.requestFocus();
                    return;
                }
            }

            if(validatePasswordField(passwordInput) || validateEmailField(emailInput)) return;

            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (usernameInput.getVisibility() == View.VISIBLE) {
                userViewModel.registerUser(email, password, username);
            } else {
                userViewModel.loginUser(email, password);
            }
        });

        userViewModel.getUserStateMutableLiveData().observe(this, result -> {
            if (result == UserState.LOGGED_IN_FROM_REGISTRATION) {
                if (profilePictureFile != null) {
                    userViewModel.getUserService().addPicture(profilePictureFile);
                }
                Intent intent = new Intent(LoginActivity.this, UsertypeSelectionActivity.class);
                startActivity(intent);
            }
            else if (result == UserState.LOGGED_IN_FROM_LOGIN) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), getString(R.string.login_register_unsuccessful_alert), Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    private boolean validatePasswordField(EditText passwordField){
        String password = passwordField.getText().toString();
        if (password.isEmpty()) {
            passwordField.setError("Please enter a password!");
            passwordField.requestFocus();
            return true;
        }

        if (password.length() < 6){
            passwordField.setError("Password needs to contain at least 6 characters!");
            passwordField.requestFocus();
            return true;
        }

        return false;
    }

    private boolean validateEmailField(EditText emailField){
        String email = emailField.getText().toString();
        if (email.isEmpty()) {
            emailField.setError("Please enter an email address!");
            emailField.requestFocus();
            return true;
        }

        if (!email.matches("^(.+)@(\\S+)$")) {
            emailField.setError("This email address is invalid!");
            emailField.requestFocus();
            return true;
        }

        return false;
    }

    private void dispatchTakePictureIntent(Uri savedPicture) {
        Intent createTakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        createTakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, savedPicture);
        activityResultLauncher.launch(createTakePictureIntent);
    }

    private void onCreateActivityResult(ActivityResult result){
        if(result.getResultCode() == RESULT_OK){
            Bitmap imageBitmap = BitmapFactory.decodeFile(profilePictureFile.getAbsolutePath());
            profilePicturePreview.setImageBitmap(imageBitmap);
        }
    }
}