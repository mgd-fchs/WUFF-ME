package at.tugraz.software22.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(R.layout.activity_main);

        userViewModel.getUserLiveData().observe(this, user -> {
            SwipingFragment swipingFragment = SwipingFragment.newInstance(user);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, swipingFragment).commit();
            setupButtons(user);
        });




    }

    private void setupButtons(User user) {

        TextView textViewLogout = findViewById(R.id.logout);
        Button buttonSwiping = findViewById(R.id.buttonSwiping);
        Button buttonMatches = findViewById(R.id.buttonMatches);
        Button buttonMessages = findViewById(R.id.buttonMessages);
        Button buttonEditProfile = findViewById(R.id.buttonEditProfile);


        textViewLogout.setOnClickListener(v -> {
            userViewModel.logout();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonSwiping.setOnClickListener(v -> {
            SwipingFragment swipingFragment = SwipingFragment.newInstance(user);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, swipingFragment).commit();
        });

        buttonMatches.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new MatchesFragment()).commit());

        buttonMessages.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new ChatFragment()).commit());

        buttonEditProfile.setOnClickListener(v -> startActivity(new Intent(this, EditProfileActivity.class)));
    }

}
