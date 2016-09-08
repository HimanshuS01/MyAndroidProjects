package com.example.chhavi.codinglibraryapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 12345;
    SignInButton signInButton;
    Button signoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Facebook Integration");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button_fb);
        loginButton.setReadPermissions(Arrays.asList("email", "user_friends", "public_profile", "user_about_me", "user_status"));

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("AccessToken", loginResult.getAccessToken().getToken().toString());
                Toast.makeText(MainActivity.this, loginResult.getAccessToken().getToken(), Toast.LENGTH_LONG).show();
                AccessToken accessToken = loginResult.getAccessToken();

                //The GraphRequest class has a newMeRequest method which calls the /user/me endpoint to fetch the user data for the given access token.
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("JsonObject", object.toString());
                        String email = object.optString("email");
                        String uid = object.optString("id");

                        Toast.makeText(MainActivity.this, "Email:" + email + "\n" + "UserId:" + uid, Toast.LENGTH_LONG).show();
                        // Application code
                    }
                });

                //https://developers.facebook.com/docs/graph-api/reference/user
                //If you need any additional fields, or want to reduce the response payload for performance reasons, you can add a fields parameter.
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,link,about,birthday,education,bio,devices");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("error", error.toString());
                Toast.makeText(MainActivity.this,
                        error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();

        ShareButton shareButton = (ShareButton) findViewById(R.id.share_facebook);
        shareButton.setShareContent(content);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile().requestId()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.google_sign_in);
        signoutButton = (Button) findViewById(R.id.google_sign_out);
        signInButton.setOnClickListener(this);
        signoutButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //handleSignInResult(result);
            handleGoogleIntent(result);

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Google failure", "onConnectionFailed:" + connectionResult);
    }

    private void handleGoogleIntent(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(MainActivity.this, acct.getDisplayName() + "\n" + acct.getEmail() + "\n" + acct.getId(), Toast.LENGTH_LONG).show();
            updateUI(true);
        } else {
            //user chose not to sign in
            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.google_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.google_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
        }
    }

    private void signInWithGplus() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOutFromGplus() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                updateUI(false);
            }
        });
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            signInButton.setVisibility(View.GONE);
            signoutButton.setVisibility(View.VISIBLE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
            signoutButton.setVisibility(View.GONE);
        }
    }
}
