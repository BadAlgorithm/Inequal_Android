package com.hazup.isochoric.greaterorlessthan.controller;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.adapters.HighScoresListViewAdapter;
import com.hazup.isochoric.greaterorlessthan.dao.HighScoreDao;
import com.hazup.isochoric.greaterorlessthan.dao.sqlite_open_helper.DbGameOpenHelper;
import com.hazup.isochoric.greaterorlessthan.model.Score;
import com.hazup.isochoric.greaterorlessthan.utils.Background;

import java.util.ArrayList;
import java.util.List;

public class HighScoresActivity extends BaseActionBarActivity {

    List<Score> scores;

    private ListView scoresListView;
    private HighScoresListViewAdapter lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        scores = new ArrayList<>();
        scoresListView = (ListView) findViewById(R.id.highscores_list_view);
        lvAdapter = new HighScoresListViewAdapter(this, scores);
        scoresListView.setAdapter(lvAdapter);
        getScores();
    }

    private void getScores(){
        Background.runInBackground(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = DbGameOpenHelper.getInstance(getApplicationContext()).getWritableDatabase();
                HighScoreDao dao = HighScoreDao.getInstance();
                scores.getClass();
                scores.addAll(dao.findAllScores(db));
                lvAdapter.notifyDataSetChanged();
            }
        });
    }
}
