package at.tugraz.software22.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.service.UserService;

public class UsertypeSelectionActivity extends AppCompatActivity {

    private UserType userType = UserType.NON;
    Button buttonSelectUsertype;
    CheckBox checkBoxOwner;
    CheckBox checkBoxSearcher;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertype_selection);

        initialiseViews();
        userService = new UserService();

        buttonSelectUsertype.setOnClickListener(view -> {
            setUserType();
            userService.setUserType("hans", userType);
            Intent selectedTypeIntent = new Intent();
            selectedTypeIntent.putExtra("hans", userType.name());
            setResult(RESULT_OK, selectedTypeIntent);
            finish();
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
