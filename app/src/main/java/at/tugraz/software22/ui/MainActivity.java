package at.tugraz.software22.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import at.tugraz.software22.R;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

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
        editProfile.setOnClickListener(v -> startActivity(new Intent(this, EditProfileActivity.class)));

        TextView interestingUserName = findViewById(R.id.textViewNameOfInterestingUser);
        ImageView interestingUserPicture = findViewById(R.id.imageViewInterestingUser);
        userViewModel.getNextInterestingUserLiveData().observe(this, user -> {
            interestingUserName.setText(user.getUsername());
            userViewModel.getPictureService().downloadPicture(user.getPicturePaths().get(0)).observe(this, picture -> {
                Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                interestingUserPicture.setImageBitmap(Bitmap.createScaledBitmap(bmp, interestingUserPicture.getWidth(), interestingUserPicture.getHeight(), false));
            });
        });

        userViewModel.loadNextInterestingUser();

    }

}