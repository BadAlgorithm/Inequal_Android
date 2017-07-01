package com.hazup.isochoric.greaterorlessthan.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.utils.Background;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Authenticate extends AppCompatActivity {

    private WebView authWebView;
    Twitter twitter = TwitterFactory.getSingleton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        authWebView = (WebView) findViewById(R.id.authenticate_web_view);
        authWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                if (url.startsWith("http://www.isentropical.com/")) {
                    Uri uri = Uri.parse(url);
                    final String oauthVerifier = uri.getQueryParameter("oauth_verifier");
                    if (oauthVerifier != null) {
                        Background.runInBackground(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
                                    twitter.setOAuthAccessToken(accessToken);

                                    Intent intent = new Intent();
                                    intent.putExtra("access token", accessToken.getToken());
                                    intent.putExtra("access token secret", accessToken.getTokenSecret());
                                    Log.d("twitterApp", "returning result");
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } catch (Exception e) {
                                    System.out.println(e.toString());
                                }
                            }
                        });
                    } else {
                    }
                }
                super.onLoadResource(view, url);
            }
        });
        Background.runInBackground(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestToken requestToken = twitter.getOAuthRequestToken();
                    final String requestUrl = requestToken.getAuthenticationURL();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            authWebView.loadUrl(requestUrl);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
    }
}
