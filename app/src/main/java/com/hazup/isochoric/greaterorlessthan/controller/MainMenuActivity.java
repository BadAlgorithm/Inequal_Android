package com.hazup.isochoric.greaterorlessthan.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.controller.facade.SharedPreferenceManager;
import com.hazup.isochoric.greaterorlessthan.dao.PlayerDao;
import com.hazup.isochoric.greaterorlessthan.dao.sqlite_open_helper.DbGameOpenHelper;
import com.hazup.isochoric.greaterorlessthan.model.Player;
import com.hazup.isochoric.greaterorlessthan.utils.Background;
import com.hazup.isochoric.greaterorlessthan.utils.IdGenerator;
import com.hazup.isochoric.greaterorlessthan.utils.MediaPlayerSingleton;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button gameBtn;
    private Button highScoresBtn;
    private Button settingsBtn;
    private Button exitBtn;

    private LinearLayout newUserPromptContainer;
    private EditText newUserEditText;
    private Button submitNewUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        newUserPromptContainer = (LinearLayout) findViewById(R.id.new_user_prompt_container);
        gameBtn = (Button) findViewById(R.id.play_game_btn);
        gameBtn.setOnClickListener(this);
        highScoresBtn = (Button) findViewById(R.id.high_scores_btn);
        highScoresBtn.setOnClickListener(this);
        settingsBtn = (Button) findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(this);
        exitBtn = (Button) findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(this);
        newUserEditText = (EditText) findViewById(R.id.new_username_edit_text);
        submitNewUserBtn = (Button) findViewById(R.id.new_user_submit_btn);
        submitNewUserBtn.setOnClickListener(this);

        // Show new user container prompt if there are no active users (first time opening app)
        if (checkIfActivePlayer()) {
            removeNewPlayerPrompt();
        }
        // Initialize db connections
        DbGameOpenHelper dbGameOpenHelper = DbGameOpenHelper.getInstance(this.getApplicationContext());
        dbGameOpenHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_game_btn:
                gotToNewGame();
                break;
            case R.id.high_scores_btn:
                goToHighScores();
                break;
            case R.id.settings_btn:
                gotToSettings();
                break;
            case R.id.exit_btn:
                gotToRules();
                break;
            case R.id.new_user_submit_btn:
                submitNewUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaPlayerSingleton.getInstance(this).startMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayerSingleton.getInstance(this).pauseMusic();
    }

    private boolean checkIfActivePlayer() {
        Player activePlayer = SharedPreferenceManager.getActivePlayer(this);
        if (activePlayer == null || activePlayer.getPlayerName() == null) {
            return false;
        }
        return true;
    }

    private void submitNewUser(){
        String newUserInput = newUserEditText.getText().toString();
        String newPlayerId = IdGenerator.generateUUID();
        Player player = new Player(newUserInput, newPlayerId);
        addNewUserToPref(player);
        addNewPlayerToDb(player);
        Toast.makeText(this, String.format("New user %s added", player.getPlayerName()), Toast.LENGTH_SHORT).show();
    }

    private void addNewUserToPref(Player player) {
        SharedPreferenceManager.submitLatestUserPref(player, this);
        removeNewPlayerPrompt();
    }

    private void addNewPlayerToDb(final Player player){
        final SQLiteDatabase db = DbGameOpenHelper.getInstance(this).getWritableDatabase();
        Background.runInBackground(new Runnable() {
            @Override
            public void run() {
                PlayerDao playerDao = PlayerDao.getInstance();
                playerDao.insertPlayer(db, player);
            }
        });
    }

    private void removeNewPlayerPrompt(){
        newUserPromptContainer.setVisibility(View.GONE);
    }

    private void gotToNewGame() {
        Intent gotToNewGameIntent = new Intent(this, GameScreenActivity.class);
        startActivity(gotToNewGameIntent);
    }

    private void gotToRules() {
        Intent gotToRulesIntent = new Intent(this, ViewRulesActivity.class);
        startActivity(gotToRulesIntent);
    }

    private void goToHighScores(){
        Intent gotToHighScoresIntent = new Intent(this, HighScoresActivity.class);
        startActivity(gotToHighScoresIntent);
    }

    private void gotToSettings() {
        Intent goToSettingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(goToSettingsIntent);
    }
}
