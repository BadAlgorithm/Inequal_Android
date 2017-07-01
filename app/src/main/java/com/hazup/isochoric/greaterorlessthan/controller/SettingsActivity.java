package com.hazup.isochoric.greaterorlessthan.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.controller.facade.SharedPreferenceManager;
import com.hazup.isochoric.greaterorlessthan.model.Player;
import com.hazup.isochoric.greaterorlessthan.utils.IdGenerator;

public class SettingsActivity extends BaseActionBarActivity {

    private Button applySettingsBtn;
    private RadioGroup settingsRadioGroup;
    private EditText playerNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsRadioGroup = (RadioGroup) findViewById(R.id.settings_radio_group);
        playerNameEditText = (EditText) findViewById(R.id.settings_name_edit_text);
        applySettingsBtn = (Button) findViewById(R.id.apply_settings_btn);
        applySettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserInput();
            }
        });
    }

    private void validateUserInput() {
        String newPlayerName = playerNameEditText.getText().toString();
        if (newPlayerName.equals("")) {
            Toast.makeText(this, "Player Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
        } else {
            applySettings();
        }
    }

    private void applySettings() {
        int selectedGameplayMode = settingsRadioGroup.getCheckedRadioButtonId();
        String preference;
        switch (selectedGameplayMode) {
            case R.id.accel_radio_btn:
                preference = SharedPreferenceManager.ACCEL_INDICATOR;
                break;
            case R.id.swipe_radio_btn:
                preference = SharedPreferenceManager.GESTURE_INDICATOR;
                break;
            default:
                preference = SharedPreferenceManager.GESTURE_INDICATOR;
                break;
        }
        SharedPreferenceManager.submitGameplayMode(preference, this);

        String newPlayerName = playerNameEditText.getText().toString();
        String playerId = IdGenerator.generateUUID();
        Player newPlayer = new Player(newPlayerName, playerId);
        SharedPreferenceManager.submitLatestUserPref(newPlayer, this);
        Toast.makeText(this, "Settings Applied", Toast.LENGTH_SHORT).show();
    }

}
