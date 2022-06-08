package at.tugraz.software22.ui;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private static Application wuffApp;
    private UserViewModel userViewModel;
    public static final String INTENT_USERNAME = "";
    private static String username;

    private ImageView profilePicturePreview;

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    this::onCreateActivityResult);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = (EditText)findViewById(R.id.email);
        EditText passwordInput = (EditText)findViewById(R.id.password);
        EditText usernameInput = (EditText)findViewById(R.id.username);
        SwitchCompat toggleBtn = (SwitchCompat) findViewById(R.id.toggle_register);
        ImageView uploadImage = (ImageView)findViewById(R.id.image_button_add_profile_picture);
        profilePicturePreview = (ImageView)findViewById(R.id.profile_picture_preview);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        wuffApp = getApplication();

        Button loginBtn = (Button)findViewById(R.id.login_btn);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                uploadImage.setVisibility(View.VISIBLE);
                profilePicturePreview.setVisibility(View.VISIBLE);

            }
        });

        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usernameInput.getVisibility() == View.VISIBLE) {
                    username = usernameInput.getText().toString();

                    if (username.isEmpty()) {
                        usernameInput.setError("Please enter a username!");
                        usernameInput.requestFocus();
                    }
                }

                validatePasswordField(passwordInput);
                validateEmailField(emailInput);

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                User users = new User(username, password, email);

                userViewModel.registerUser(users);
            }

        });

        userViewModel.getUserService().getUserState().observe(this, result -> {
            if (result == UserState.LOGGED_IN_FROM_REGISTRATION){
                Intent intent = new Intent(LoginActivity.this, UsertypeSelectionActivity.class);
                startActivity(intent);
            }
            else if (result == UserState.LOGGED_IN_FROM_LOGIN) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), "Login/Registration unsuccessful!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    private void validatePasswordField(EditText passwordField){
        String password = passwordField.getText().toString();
        if (password.isEmpty()) {
            passwordField.setError("Please enter a password!");
            passwordField.requestFocus();
        }

        if (password.length() < 6){
            passwordField.setError("Password needs to contain at least 6 characters!");
            passwordField.requestFocus();
        }
    }

    private void validateEmailField(EditText emailField){
        String email = emailField.getText().toString();
        if (email.isEmpty()) {
            emailField.setError("Please enter an email address!");
            emailField.requestFocus();
        }

        if (!email.matches("^(.+)@(\\S+)$")) {
            emailField.setError("This email address is invalid!");
            emailField.requestFocus();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent createTakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(createTakePictureIntent);
    }

    private void onCreateActivityResult(ActivityResult result){
        if(result.getResultCode() == RESULT_OK && result.getData() != null){
            Bundle extras = result.getData().getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePicturePreview.setImageBitmap(imageBitmap);
        }
    }
}