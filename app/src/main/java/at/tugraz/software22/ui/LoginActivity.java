package at.tugraz.software22.ui;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private static Application wuffApp;
    private UserViewModel userViewModel;
    public static final String INTENT_USERNAME = "";
    private static String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = (EditText)findViewById(R.id.email);
        EditText passwordInput = (EditText)findViewById(R.id.password);
        EditText usernameInput = (EditText)findViewById(R.id.username);
        Switch toggleBtn = (Switch) findViewById(R.id.toggle_register);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        wuffApp = getApplication();

        Button loginBtn = (Button)findViewById(R.id.login_btn);

        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    usernameInput.setVisibility(View.VISIBLE);
                    loginBtn.setText("REGISTER");
                } else {
                    usernameInput.setVisibility(View.INVISIBLE);
                    loginBtn.setText("LOG IN");
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
                        return;
                    }
                }

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (email.isEmpty()) {
                    emailInput.setError("Please enter an email address!");
                    emailInput.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordInput.setError("Please enter a password!");
                    passwordInput.requestFocus();
                    return;
                }

                if (password.length() < 6){
                    passwordInput.setError("Password needs to contain at least 6 characters!");
                    passwordInput.requestFocus();
                    return;
                }

                if (!email.matches("^(.+)@(\\S+)$")) {
                    emailInput.setError("This email address is invalid!");
                    emailInput.requestFocus();
                    return;
                }

                Users users = new Users(username, password, email);

                if (usernameInput.getVisibility() == View.VISIBLE) {
                    userViewModel.registerUser(users);
                } else {
                    userViewModel.loginUser(users);
                }
            }

        });

        userViewModel.getUserService().getRegistrationSuccess().observe(this, result -> {
            if (result){

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), "Login/Registration unsuccessful!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }
}
