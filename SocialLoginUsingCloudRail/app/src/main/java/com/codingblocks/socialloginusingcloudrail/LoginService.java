package com.codingblocks.socialloginusingcloudrail;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cloudrail.si.exceptions.AuthenticationException;
import com.cloudrail.si.interfaces.Profile;
import com.cloudrail.si.services.Facebook;
import com.cloudrail.si.services.GitHub;
import com.cloudrail.si.services.GooglePlus;
import com.cloudrail.si.services.Instagram;
import com.cloudrail.si.services.LinkedIn;
import com.cloudrail.si.services.MicrosoftLive;
import com.cloudrail.si.services.Twitter;
import com.cloudrail.si.services.Yahoo;
import com.cloudrail.si.types.DateOfBirth;

public class LoginService extends IntentService {

    public static final String EXTRA_PROFILE = "profile";
    public static final String EXTRA_SOCIAL_ACCOUNT = "account";
    private static final String TAG = "VIVZ";

    public LoginService() {
        super("Social Login Service");
    }

    private Profile init(SocialMediaProvider provider) throws AuthenticationException {
        //Superclass reference variable = subclass object
        //Profile is an interface and Facebook, Twitter, Yahoo etc implement that interface.
        Profile profile = null;
        switch (provider) {
            case TWITTER:
                profile = new Twitter(this, getString(R.string.twitter_app_key), getString(R.string.twitter_app_secret));
                break;
            case LINKED_IN:
                profile = new LinkedIn(this, getString(R.string.linked_in_app_key), getString(R.string.linked_in_app_secret));
                break;
            case GITHUB:
                profile = new GitHub(this, getString(R.string.github_app_key), getString(R.string.github_app_secret));
                break;
        }
        //At run time, profile will contain one of the 8 objects above and will fetch details from that particular provider
        return profile;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                Bundle extras = intent.getExtras();
                //Guess what I sent from the Activity which calls this Service! Our Enumeration variable!
                //It contains FACEBOOK or TWITTER or ... and is Serializable!
                SocialMediaProvider provider = (SocialMediaProvider) extras.getSerializable(EXTRA_PROFILE);

                //You already know which social account the user will use, just create an object of it.
                Profile profile = init(provider);

                //Create a User Account object with all the details
                UserAccount account = init(profile);

                //Start another Activity to show the user details
                Intent detailsIntent = new Intent(getApplicationContext(), DetailsActivity.class);

                //To directly start activities from an IntentService, we need to add this flag, Google if curious!
                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //Send the User Account object with all details
                detailsIntent.putExtra(EXTRA_SOCIAL_ACCOUNT, account);
                startActivity(detailsIntent);
            } catch (AuthenticationException e) {

                //When you cancel in the middle of a login, cloud rail was throwing this exception, hence!
                Log.e(TAG, "onHandleIntent: You cancelled", e);

            }
        }
    }

    private UserAccount init(Profile profile) {
        //The usual get-set stuff
        UserAccount account = new UserAccount();
        account.setIdentifier(profile.getIdentifier());
        account.setFullName(profile.getFullName());
        account.setEmail(profile.getEmail());
        account.setDescription(profile.getDescription());
        DateOfBirth dob = profile.getDateOfBirth();
        if (dob != null) {
            Long day = dob.getDay();
            Long month = dob.getMonth();
            Long year = dob.getYear();
            if (day != null) {
                account.setDay(day);
            }
            if (month != null) {
                account.setMonth(month);
            }
            if (year != null) {
                account.setYear(year);
            }
        }
        account.setGender(profile.getGender());
        account.setPictureURL(profile.getPictureURL());
        account.setLocale(profile.getLocale());
        return account;
    }
}

