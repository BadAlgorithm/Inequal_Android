package com.hazup.isochoric.greaterorlessthan.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.utils.Background;
import com.hazup.isochoric.greaterorlessthan.utils.TwitterIntentKeys;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class PostToTwitterActivity extends BaseActionBarActivity implements View.OnClickListener{

    private static final int AUTHENTICATE = 1;
    private static final String DUPLICATE_MESSAGE = "Cannot Duplicate Tweets, Try Again Later";

    private Twitter twitter = TwitterFactory.getSingleton();
    private final int DUPLICATE_ERROR = 187; //Twitter Exception, duplicate post error code

    private EditText userTweetEditText;

    private String playerName;
    private String gameScore;
    private String gameTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_twitter);
        setTitle("Post To Twitter");
        userTweetEditText = (EditText) findViewById(R.id.user_tweet_input);
        Button postToTwitterBtn = (Button) findViewById(R.id.authenticate_twitter_btn);
        postToTwitterBtn.setOnClickListener(this);

        Bundle recievedExtras = getIntent().getExtras();
        if (recievedExtras != null){
            playerName = recievedExtras.getString(TwitterIntentKeys.USER_NAME_KEY);
            gameScore = recievedExtras.getString(TwitterIntentKeys.SCORE_KEY);
            gameTime = recievedExtras.getString(TwitterIntentKeys.TIMESTAMP_KEY);
            userTweetEditText.setText(getDefualtTwitterStr());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.authenticate_twitter_btn:
                Intent authIntent = new Intent(this, Authenticate.class);
                startActivityForResult(authIntent, AUTHENTICATE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == AUTHENTICATE && resultCode == RESULT_OK){
            final Context context = this;
            Background.runInBackground(new Runnable() {
                @Override
                public void run() {
                    String token = data.getStringExtra("access token");
                    String secret = data.getStringExtra("access token secret");
                    AccessToken accessToken = new AccessToken(token, secret);
                    twitter.setOAuthAccessToken(accessToken);
                    try {
                        twitter.updateStatus(getTweetFromEditText());
                        Toast.makeText(context, getString(R.string.tweet_successful), Toast.LENGTH_SHORT).show();
                    } catch (final TwitterException te){
                        if (te.getErrorCode() == DUPLICATE_ERROR){
                            Toast.makeText(context, DUPLICATE_MESSAGE, Toast.LENGTH_SHORT).show();
                        }
                    } catch (final Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("twitterApp", "Failed Auth");
                            }
                        });
                    }
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getDefualtTwitterStr(){
        return String.format(getString(R.string.def_prompt), playerName, gameScore, gameTime);
    }

    private String getTweetFromEditText(){
        return userTweetEditText.getText().toString();
    }

}