package com.theroc.message.UI.FirstTimeInit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.theroc.message.R;
import com.theroc.message.Utilities.PrefManager;

public class WelcomeTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_time);

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        RadioGroup radioGroup = findViewById(R.id.welcomeTimeRadioGroup);

        Button buttonPrevious = findViewById(R.id.welcomeTimePreviousButton);
        buttonPrevious.setOnClickListener((View v) -> openWelcomeReasonActivity());

        Button buttonNext = findViewById(R.id.welcomeTimeNextButton);
        buttonNext.setOnClickListener((View v) -> {
            PrefManager prefManager = new PrefManager(this);
            if (radioGroup.getCheckedRadioButtonId() == R.id.welcomeTimeAutoRadioButton) {
                prefManager.getEditor().putInt("time", 8).commit();
            } else {
                prefManager.getEditor().putInt("time", 26).commit();
            }
            openWelcomeFinishActivity();
        });
    }

    private void openWelcomeReasonActivity() {

        Intent intent = new Intent(this, WelcomeReasonActivity.class);
        startActivity(intent);
        finish();
    }

    private void openWelcomeFinishActivity() {

        Intent intent = new Intent(this, WelcomeFinishActivity.class);
        startActivity(intent);
        finish();
    }
}