package at.tugraz.software22.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import at.tugraz.software22.Constants;
import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.exception.UserNotLoggedInException;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    private final ActivityResultLauncher<Intent> selectTypeActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::onSelectedTypeActivityResult);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(R.layout.activity_main);

        TextView logout = findViewById(R.id.logout);

        logout.setOnClickListener(v -> {
            userViewModel.logout();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        Button editProfile = findViewById(R.id.buttonEditProfile);
        editProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfileActivity.class));
        });

        TextView interestingUserName = findViewById(R.id.textViewNameOfInterestingUser);
        ImageView interestingUserPicture = findViewById(R.id.imageViewInterestingUser);
        userViewModel.getMatcherService().getNextInterestingProfile().observe(this, user -> {
            interestingUserName.setText(user.getUsername());
            userViewModel.getPictureService().downloadPicture(user.getPicturePaths().get(0)).observe(this, picture -> {
                Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                interestingUserPicture.setImageBitmap(Bitmap.createScaledBitmap(bmp, interestingUserPicture.getWidth(), interestingUserPicture.getHeight(), false));
            });
        });

    }

    private void onSelectedTypeActivityResult(ActivityResult result) {
        Toast.makeText(this, "Type: " + result.getData().getStringExtra(FirebaseAuth.getInstance().getCurrentUser().getUid()), Toast.LENGTH_LONG).show();
    }
}