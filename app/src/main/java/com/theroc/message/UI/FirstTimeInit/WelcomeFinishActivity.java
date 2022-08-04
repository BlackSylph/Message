package com.theroc.message.UI.FirstTimeInit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.theroc.message.UI.MainActivity;
import com.theroc.message.R;
import com.theroc.message.Utilities.PrefManager;

public class WelcomeFinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_finish);

        PrefManager prefManager = new PrefManager(getApplicationContext());
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(WelcomeFinishActivity.this, MainActivity.class));
            finish();
        }

        Button buttonPrevious = findViewById(R.id.welcomeFinishPreviousButton);
        buttonPrevious.setOnClickListener((View v) -> openWelcomeTimeActivity());

        Button buttonNext = findViewById(R.id.welcomeFinishNextButton);
        buttonNext.setOnClickListener((View v) -> openMainActivity());
    }

    private void openWelcomeTimeActivity() {

        Intent intent = new Intent(this, WelcomeTimeActivity.class);
        startActivity(intent);
        finish();
    }

    private void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}