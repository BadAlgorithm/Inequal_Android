package com.hazup.isochoric.greaterorlessthan.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.utils.MediaPlayerSingleton;

public abstract class BaseActionBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.settings_menu_item:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.rules_menu_item:
                intent = new Intent(this, ViewRulesActivity.class);
                break;
            case R.id.home_menu_item:
                intent = new Intent(this, MainMenuActivity.class);
                break;
            case R.id.high_scores_menu_item:
                intent = new Intent(this, HighScoresActivity.class);
                break;
        }
        if (intent != null){
            startActivity(intent);
            finish();
        }

        return true;
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
}
