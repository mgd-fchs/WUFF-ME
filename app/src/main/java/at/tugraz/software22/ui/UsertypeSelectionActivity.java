package at.tugraz.software22.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import at.tugraz.software22.R;

public class UsertypeSelectionActivity extends AppCompatActivity {

    private int selected_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertype_selection);

        Button buttonSelectUsertype = findViewById(R.id.buttonSelectUsertype);
        CheckBox checkBoxOwner = findViewById(R.id.checkBoxOwner);
        CheckBox checkBoxSearcher = findViewById(R.id.checkBoxSearcher);

        buttonSelectUsertype.setOnClickListener(view -> {
            if (checkBoxOwner.isChecked())
                selected_type += 1;
            if (checkBoxSearcher.isChecked())
                selected_type += 2;

            Toast.makeText(UsertypeSelectionActivity.this, "User Selected: " + selected_type, Toast.LENGTH_LONG).show();
            selected_type = 0;
        });
    }
}
