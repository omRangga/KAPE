package com.unpas.chatak;

import android.app.Application;
import android.util.Log;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.unpas.chatak.transaksi_page.SignInActivity;

//facebook

public class FacebookAuth extends Application {
    private String account_name;

    public String getAccount_name() {
        return account_name;
    }
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
    //facebook oauth
    public static CallbackManager callbackManager;
    //facebook session
    public static AccessToken accessToken;
    //facebook login manager
    public static LoginManager loginManager;
    Button loginButton;
    final static String TAG="FacebookAuth";
    SignInActivity signInActivity = new SignInActivity();
    @Override
    public void onCreate() {
        //facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        //CallbackRegistration();
        super.onCreate();
    }
    public void CallbackRegistration(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG,"facebookAuth Success, access token -> "+checkAccessTokenFacebook());
                        ProfileTracker profileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                if (currentProfile!=null){
                                    Log.d(TAG,"facebookAuth get_user data "+currentProfile.getName());
                                    setAccount_name(currentProfile.getName());
                                    Chatak.sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, getAccount_name());
                                    Chatak.sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                }else{
                                    Log.e(TAG,"facebookAuth get_user data is NULL" );
                                }
                            }
                        };
                    }
                    @Override
                    public void onCancel() {
                        // App code
                        Log.d(TAG,"facebookAuth: Canceled");
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(TAG,"facebookAuth :error"+exception.getMessage());
                    }
                });
    }
    public boolean checkAccessTokenFacebook(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }
}
