package com.theroc.message.UI.FirstTimeInit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.theroc.message.R;
import com.theroc.message.Utilities.PrefManager;

import java.util.ArrayList;

public class WelcomeReasonActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerSelection;
    private ArrayList<String> spinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_reason);

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        Spinner reasonSpinner = findViewById(R.id.welcomeReasonSpinner);
        int[] spinnerIntList = getResources().getIntArray(R.array.spinner_reason);
        spinnerList = new ArrayList<>();
        spinnerList.add("Αυτόματα");
        for (int value : spinnerIntList) {
            spinnerList.add(String.valueOf(value));
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(WelcomeReasonActivity.this, android.R.layout.simple_spinner_item, spinnerList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonSpinner.setAdapter(adapterSpinner);
        reasonSpinner.setOnItemSelectedListener(WelcomeReasonActivity.this);

        Button buttonPrevious = findViewById(R.id.welcomeReasonPreviousButton);
        buttonPrevious.setOnClickListener((View v) -> openWelcomeAddressActivity());

        Button buttonNext = findViewById(R.id.welcomeReasonNextButton);
        buttonNext.setOnClickListener((View v) -> {
            PrefManager prefManager = new PrefManager(this);
            if (spinnerSelection.equals("Αυτόματα")) {
                prefManager.getEditor().putString("reason", "_auto_").commit();
            } else {
                prefManager.getEditor().putString("reason", spinnerSelection).commit();
            }
            openWelcomeTimeActivity();
        });
    }

    private void openWelcomeAddressActivity() {

        Intent intent = new Intent(this, WelcomeAddressActivity.class);
        startActivity(intent);
        finish();
    }

    private void openWelcomeTimeActivity() {

        Intent intent = new Intent(this, WelcomeTimeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelection = spinnerList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}