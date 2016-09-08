package com.codingblocks.socialloginusingcloudrail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    //We are using Cloud Rail Api(https://cloudrail.com/)

    private SocialMediaProvider mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void performTwitterLogin(View view) {
        mProfile = SocialMediaProvider.TWITTER;
        performLogin();
    }


    public void performLinkedInLogin(View view) {
        mProfile = SocialMediaProvider.LINKED_IN;
        performLogin();
    }

    public void performGitHubLogin(View view) {
        mProfile = SocialMediaProvider.GITHUB;
        performLogin();
    }


    //All this needs to be performed in the background thread. Android offers me several options with which I can run my code
    //in a background thread. I could use an AsyncTask but an IntentService is a better solution as its lifecycle is independent
    //of the Activity calling it. Even if the user exits the MainActivity or rotates the screen,
    //the IntentService will remain unaffected and it will run the code needed to perform login.

    public void performLogin() {
        Intent intent = new Intent(this, LoginService.class);
        intent.putExtra(LoginService.EXTRA_PROFILE, mProfile);
        startService(intent);
    }
}
