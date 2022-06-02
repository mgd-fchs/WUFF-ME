package at.tugraz.software22.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserState;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private static String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = (EditText)findViewById(R.id.email);
        EditText passwordInput = (EditText)findViewById(R.id.password);
        EditText usernameInput = (EditText)findViewById(R.id.username);
        Switch toggleBtn = (Switch) findViewById(R.id.toggle_register);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Button loginBtn = (Button)findViewById(R.id.login_btn);

        toggleBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                usernameInput.setVisibility(View.VISIBLE);
                loginBtn.setText(R.string.action_register);
            } else {
                usernameInput.setVisibility(View.INVISIBLE);
                loginBtn.setText(R.string.action_sign_in);
            }
        });

        loginBtn.setOnClickListener(v -> {

            if (usernameInput.getVisibility() == View.VISIBLE) {
                username = usernameInput.getText().toString();

                if (username.isEmpty()) {
                    usernameInput.setError(getString(R.string.enter_username_alert));
                    usernameInput.requestFocus();
                    return;
                }
            }

            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (email.isEmpty()) {
                emailInput.setError(getString(R.string.enter_email_alert));
                emailInput.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                passwordInput.setError(getString(R.string.enter_password_alert));
                passwordInput.requestFocus();
                return;
            }

            if (password.length() < 6){
                passwordInput.setError(getString(R.string.invalid_password_alert));
                passwordInput.requestFocus();
                return;
            }

            if (!email.matches("^(.+)@(\\S+)$")) {
                emailInput.setError(getString(R.string.invalid_email_alert));
                emailInput.requestFocus();
                return;
            }

            User users = new User(username, password, email);

            if (usernameInput.getVisibility() == View.VISIBLE) {
                userViewModel.registerUser(users);
            } else {
                userViewModel.loginUser(users);
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
                toast = Toast.makeText(getApplicationContext(), getString(R.string.login_register_unsuccessful_alert), Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }
}
