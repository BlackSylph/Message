package com.theroc.message.UI.FirstTimeInit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.theroc.message.R;
import com.theroc.message.Utilities.PrefManager;
import com.theroc.message.Utilities.StringOperations;

public class WelcomeAddressActivity extends AppCompatActivity {

    private boolean permissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_address);

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        EditText editText = findViewById(R.id.welcomeAddressEditText);

        RadioGroup radioGroup = findViewById(R.id.welcomeAddressRadioGroup);
        if (radioGroup.getCheckedRadioButtonId() == R.id.welcomeAddressAutoRadioButton) {
            editText.setEnabled(false);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        } else {
            editText.setEnabled(true);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
        }

        RadioButton autoRadioButton = findViewById(R.id.welcomeAddressAutoRadioButton);
        autoRadioButton.setOnClickListener((View v) -> {
            editText.setEnabled(false);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        });

        RadioButton manualRadioButton = findViewById(R.id.welcomeAddressManualRadioButton);
        manualRadioButton.setOnClickListener((View v) -> {
            editText.setEnabled(true);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
        });

        Button buttonPrevious = findViewById(R.id.welcomeAddressPreviousButton);
        buttonPrevious.setOnClickListener((View v) -> openWelcomeLastNameActivity());

        Button buttonNext = findViewById(R.id.welcomeAddressNextButton);
        buttonNext.setOnClickListener((View v) -> {
            PrefManager prefManager = new PrefManager(this);
            if (radioGroup.getCheckedRadioButtonId() == R.id.welcomeAddressAutoRadioButton) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 34);
                    return;
                }
                prefManager.getEditor().putString("address", "_auto_").commit();
            } else {
                String input = StringOperations.sanitize(editText.getText().toString());
                if (input.isEmpty()) {
                    editText.setError("Πρέπει να συμπληρώσετε την διεύθυνση κατοικίας σας");
                } else {
                    prefManager.getEditor().putString("address", input).commit();
                }
            }
            openWelcomeReasonActivity();
        });
    }

    private void openWelcomeLastNameActivity() {

        Intent intent = new Intent(this, WelcomeLastNameActivity.class);
        startActivity(intent);
        finish();
    }

    private void openWelcomeReasonActivity() {

        Intent intent = new Intent(this, WelcomeReasonActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 34) {
            permissionGranted = true;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = false;
                    break;
                }
            }
        }
    }
}