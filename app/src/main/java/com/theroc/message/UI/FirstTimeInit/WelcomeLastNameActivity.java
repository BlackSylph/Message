package com.theroc.message.UI.FirstTimeInit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.theroc.message.R;
import com.theroc.message.Utilities.PrefManager;
import com.theroc.message.Utilities.StringOperations;

public class WelcomeLastNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_last_name);

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        EditText editText = findViewById(R.id.welcomeLastNameEditText);

        Button buttonPrevious = findViewById(R.id.welcomeLastNamePreviousButton);
        buttonPrevious.setOnClickListener((View v) -> openWelcomeFirstNameActivity());

        Button buttonNext = findViewById(R.id.welcomeLastNameNextButton);
        buttonNext.setOnClickListener((View v) -> {
            String input = StringOperations.sanitize(editText.getText().toString());
            if (input.isEmpty()) {
                editText.setError("Πρέπει να συμπληρώσετε το επίθετό σας");
            } else {
                PrefManager prefManager = new PrefManager(this);
                prefManager.getEditor().putString("lastName", input).commit();
                openWelcomeAddressActivity();
            }
        });
    }

    private void openWelcomeFirstNameActivity() {

        Intent intent = new Intent(this, WelcomeFirstNameActivity.class);
        startActivity(intent);
        finish();
    }

    private void openWelcomeAddressActivity() {

        Intent intent = new Intent(this, WelcomeAddressActivity.class);
        startActivity(intent);
        finish();
    }
}