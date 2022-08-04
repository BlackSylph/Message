package com.theroc.message.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.theroc.message.R;
import com.theroc.message.Utilities.PrefManager;
import com.theroc.message.Utilities.StringOperations;

import java.util.ArrayList;

public class OptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerSelection;
    private final ArrayList<String> spinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        EditText firstNameEditText = findViewById(R.id.optionFirstNameEditText);
        EditText lastNameEditText = findViewById(R.id.optionLastNameEditText);
        RadioButton addressAutoRadioButton = findViewById(R.id.optionAddressAutoRadioButton);
        RadioButton addressManualRadioButton = findViewById(R.id.optionAddressManualRadioButton);
        EditText addressEditText = findViewById(R.id.optionAddressEditText);
        RadioButton timeAutoRadioButton = findViewById(R.id.optionTimeAutoRadioButton);
        ;
        RadioButton timeManualRadioButton = findViewById(R.id.optionTimeManualRadioButton);
        Spinner reasonSpinner = findViewById(R.id.optionReasonSpinner);

        // Retrieve data //
        PrefManager prefManager = new PrefManager(getApplicationContext());

        //First name
        firstNameEditText.setText(prefManager.getPref().getString("firstName", ""));
        //Last name
        lastNameEditText.setText(prefManager.getPref().getString("lastName", ""));
        //Address
        String addressString = prefManager.getPref().getString("address", "");
        if (addressString.equals("_auto_")) {
            addressAutoRadioButton.setChecked(true);
            addressManualRadioButton.setChecked(false);
            addressEditText.setEnabled(false);
            addressEditText.setFocusable(false);
            addressEditText.setFocusableInTouchMode(false);
        } else {
            addressAutoRadioButton.setChecked(false);
            addressManualRadioButton.setChecked(true);
            addressEditText.setEnabled(true);
            addressEditText.setFocusable(true);
            addressEditText.setFocusableInTouchMode(true);
            addressEditText.setText(addressString);
        }
        //Time
        int timeInt = prefManager.getPref().getInt("time", 8);
        if (timeInt == 8) {
            timeAutoRadioButton.setChecked(true);
            timeManualRadioButton.setChecked(false);
        } else {
            timeAutoRadioButton.setChecked(false);
            timeManualRadioButton.setChecked(true);
        }
        //Reason
        String reasonString = prefManager.getPref().getString("reason", "_auto_");
        int[] spinnerIntList = getResources().getIntArray(R.array.spinner_reason);
        if (reasonString.equals("_auto_")) {
            spinnerList.add("Αυτόματα");
            for (int value : spinnerIntList) {
                spinnerList.add(String.valueOf(value));
            }
        } else {
            spinnerList.add(reasonString);
            spinnerList.add("Αυτόματα");
            for (int value : spinnerIntList) {
                if (value != Integer.parseInt(reasonString)) {
                    spinnerList.add(String.valueOf(value));
                }
            }
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonSpinner.setAdapter(adapterSpinner);
        reasonSpinner.setOnItemSelectedListener(this);

        addressAutoRadioButton.setOnClickListener((View v) -> {
            addressEditText.setEnabled(false);
            addressEditText.setFocusable(false);
            addressEditText.setFocusableInTouchMode(false);
        });

        addressManualRadioButton.setOnClickListener((View v) -> {
            addressEditText.setEnabled(true);
            addressEditText.setFocusable(true);
            addressEditText.setFocusableInTouchMode(true);
        });

        //Back button
        ImageButton backImageButton = findViewById(R.id.messageBackImageButton);
        backImageButton.setOnClickListener((View v) -> openMainActivity());

        //Save button
        Button saveButton = findViewById(R.id.optionSaveButton);
        saveButton.setOnClickListener((View v) -> {
            if (StringOperations.sanitize(firstNameEditText.getText().toString()).isEmpty()) {
                firstNameEditText.setError("Πρέπει να συμπληρώσετε το όνομά σας");
            } else {
                prefManager.getEditor().putString("firstName", StringOperations.sanitize(firstNameEditText.getText().toString())).commit();
            }
            if (StringOperations.sanitize(lastNameEditText.getText().toString()).isEmpty()) {
                lastNameEditText.setError("Πρέπει να συμπληρώσετε το επίθετό σας");
            } else {
                prefManager.getEditor().putString("lastName", StringOperations.sanitize(lastNameEditText.getText().toString())).commit();
            }
            if (addressAutoRadioButton.isChecked()) {
                prefManager.getEditor().putString("address", "_auto_").commit();
            } else {
                if (StringOperations.sanitize(addressEditText.getText().toString()).isEmpty()) {
                    addressEditText.setError("Πρέπει να συμπληρώσετε τη διεύθυνση κατοικίας σας");
                } else {
                    prefManager.getEditor().putString("address", StringOperations.sanitize(addressEditText.getText().toString())).commit();
                }
            }
            if (timeAutoRadioButton.isChecked()) {
                prefManager.getEditor().putInt("time", 8).commit();
            } else {
                prefManager.getEditor().putInt("time", 26).commit();
            }
            if (spinnerSelection.equals("Αυτόματα")) {
                prefManager.getEditor().putString("reason", "_auto_").commit();
            } else {
                prefManager.getEditor().putString("reason", spinnerSelection).commit();
            }
            openMainActivity();
        });
    }

    private void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
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