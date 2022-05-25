package at.tugraz.software22.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import at.tugraz.software22.Constants;
import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.Users;
import at.tugraz.software22.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private static Application wuffApp;
    private UserViewModel userViewModel;
    private static String username;

    private final ActivityResultLauncher<Intent> selectTypeActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::onSelectedTypeActivityResult);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(Constants.USER_TABLE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(R.layout.activity_main);
        wuffApp = getApplication();

        TextView textView = findViewById(R.id.textViewFinalApp);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                textView.setText(users.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Cannot read from database: " + databaseError.getCode());
            }
        });

        Intent intent = new Intent(this, UsertypeSelectionActivity.class);
        selectTypeActivityLauncher.launch(intent);
    }

    private void onSelectedTypeActivityResult(ActivityResult result) {
        Toast.makeText(this, "Type: " + result.getData().getStringExtra(FirebaseAuth.getInstance().getCurrentUser().getUid()), Toast.LENGTH_LONG).show();
    }
}