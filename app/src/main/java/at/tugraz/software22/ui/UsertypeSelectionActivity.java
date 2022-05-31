package at.tugraz.software22.ui;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import at.tugraz.software22.R;
import at.tugraz.software22.WuffApplication;
import at.tugraz.software22.domain.enums.UserType;

public class UsertypeSelectionActivity extends AppCompatActivity {

    private UserType userType = UserType.NON;
    Button buttonSelectUsertype;
    CheckBox checkBoxOwner;
    CheckBox checkBoxSearcher;
    private static Application wuffApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertype_selection);

        initialiseViews();
        wuffApp = getApplication();

        buttonSelectUsertype.setOnClickListener(view -> {
            setUserType();
            ((WuffApplication) wuffApp).getUserService().setUserType(userType);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initialiseViews() {
        buttonSelectUsertype = findViewById(R.id.buttonSelectUsertype);
        checkBoxOwner = findViewById(R.id.checkBoxOwner);
        checkBoxSearcher = findViewById(R.id.checkBoxSearcher);
    }

    private void setUserType() {
        if (checkBoxOwner.isChecked() && checkBoxSearcher.isChecked())
            userType = UserType.BOTH;
        else if (checkBoxOwner.isChecked())
            userType = UserType.OWNER;
        else if (checkBoxSearcher.isChecked())
            userType = UserType.SEARCHER;
    }
}
