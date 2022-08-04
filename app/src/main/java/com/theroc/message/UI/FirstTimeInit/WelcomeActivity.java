package com.theroc.message.UI.FirstTimeInit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.theroc.message.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button buttonNext = findViewById(R.id.welcomeNextButton);
        buttonNext.setOnClickListener((View v) -> openFirstNameActivity());
    }

    private void openFirstNameActivity() {

        Intent intent = new Intent(this, WelcomeFirstNameActivity.class);
        startActivity(intent);
        finish();
    }
}