package com.unpas.chatak;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class Chatak extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
	}
	public void setSession(String name, String email){
		Chatak.sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, name);
		Chatak.sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
		Chatak.sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
	}

    public static SharedPrefManager sharedPrefManager;
    public static GoogleSignInClient mGoogleSignInClient;

	//firebase oauth
	public static FirebaseAuth mAuth;
	//firebase profile info
	public static FirebaseUser firebaseUser;
	//facebook oauth
	public static CallbackManager callbackManager;
	//facebook session
	public static AccessToken accessToken;
	//facebook login manager
	public static LoginManager loginManager;

}
