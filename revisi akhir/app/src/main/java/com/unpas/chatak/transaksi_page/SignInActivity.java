package com.unpas.chatak.transaksi_page;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.protobuf.StringValue;
import com.unpas.chatak.Chatak;
import com.unpas.chatak.FacebookAuth;
import com.unpas.chatak.KetentuanPenggunaActivity;
import com.unpas.chatak.LinkAccountActivity;
import com.unpas.chatak.SharedPrefManager;
import com.unpas.chatak.User;
import com.unpas.chatak.main_page.MainActivity;
import com.unpas.chatak.model.UserModel;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.rest.UserApiInterface;
import com.unpas.chatak.R;

//facebook

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.unpas.chatak.Chatak.callbackManager;
import static com.unpas.chatak.Chatak.mGoogleSignInClient;
import static com.unpas.chatak.Chatak.sharedPrefManager;
import static com.unpas.chatak.main_page.MainActivity.navView;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
	//deklarasi variable
	private String sInEmail,sInPassword;
	EditText etEmail,etPass;
	private final int RC_SIGN_IN=9001;
	private FirebaseAuth mAuth;
	//remember me
	private Boolean saveLogin;
	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	CheckBox cbIngatkan;
	//web view untuk lupa kata sandi
	Intent webviewintent ;
	//facebook
	Button loginButton ;
	private final String  TAG="SignInAct_test",GOOGLE="google",FACEBOOK="facebook";
	public static User user = new User();
	private ProgressDialog progressDialog;
	//session



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		mAuth = FirebaseAuth.getInstance();
		//session pengecekan apakah sudah pernah login ?
		Chatak.sharedPrefManager = new SharedPrefManager(this);
		if (Chatak.sharedPrefManager.getSPSudahLogin()) {
			startActivity(new Intent(SignInActivity.this, MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
			finish();
		}
		webviewintent = new Intent(getApplicationContext(), KetentuanPenggunaActivity.class);

		/*login config*/
		inputConf();
		rememberMeConf();
		//google login conf
		googleConf();
		//facebook login conf
		fbConf();
		//proggressDialogueConf
		progressDialogueConf();
		/*end of login conf*/

	}
	/*conf method*/
	private void rememberMeConf() {
		//get data remember me
		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();
		saveLogin = loginPreferences.getBoolean("saveLogin", false);
		//^if available, then put data to edit text
		getRememberme(saveLogin);
	}
	private void googleConf() {
		//google  sign in
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.build();
		Chatak.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

		//^Set the dimensions of the sign-in button.
		SignInButton signInButton = findViewById(R.id.btnSignIn_google);
		signInButton.setSize(SignInButton.SIZE_STANDARD);

	}
	private void inputConf() {
		//assign inputan
		etEmail = findViewById(R.id.editTextEmail_sign_in);
		etPass = findViewById(R.id.editTextPassword_sign_in);
		//assign tombol
		findViewById(R.id.btnSignIn_google).setOnClickListener(this);
		findViewById(R.id.btnSignUpChatak).setOnClickListener(this);
		findViewById(R.id.imageButtonBackSignIn).setOnClickListener(this);
		findViewById(R.id.btnSignIn).setOnClickListener(this);
		findViewById(R.id.textViewLupaSandi).setOnClickListener(this);
		cbIngatkan = findViewById(R.id.cbIngatSaya);
	}
	private void fbConf(){
		// Initialize Facebook Login button
		FacebookAuth.callbackManager = CallbackManager.Factory.create();
		final LoginButton loginButton = findViewById(R.id.login_button);
		loginButton.setReadPermissions("email", "public_profile");
		loginButton.registerCallback(FacebookAuth.callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				Log.d(TAG, "facebook:onSuccess:" + loginResult);
				handleFacebookAccessToken(loginResult.getAccessToken());
			}

			@Override
			public void onCancel() {
				Log.d(TAG, "facebook:onCancel");
				// ...
			}

			@Override
			public void onError(FacebookException error) {
				Log.d(TAG, "facebook:onError", error);
				// ...
			}
		});
	}
	private void progressDialogueConf(){
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Mencoba masuk");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
	}
	/*end of conf method*/



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSignIn_google:
				signInGoogle();
				break;
			case R.id.btnSignUpChatak:
				startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
				break;
			case R.id.imageButtonBackSignIn:
				super.onBackPressed();
				navView.setSelectedItemId(R.id.navigation_home);
				break;
			case R.id.btnSignIn:
				//put login data to remember me if cb is checked
				setRememberme(cbIngatkan.isChecked());
				//login
				doLogin(sInEmail,sInPassword);
				break;
			case R.id.textViewLupaSandi:
				webviewintent.putExtra("kebijakan_privasi","http://www.chatak.xyz/");
				startActivity(webviewintent);
				break;
		}
	}
	private void getRememberme(boolean svLogin) {
		if (svLogin == true) {
			etEmail.setText(loginPreferences.getString("username", ""));
			etPass.setText(loginPreferences.getString("password", ""));
			cbIngatkan.setChecked(true);
		}
	}
	private void setRememberme(boolean chechbox) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);

		sInEmail = etEmail.getText().toString();
		sInPassword = etPass.getText().toString();

		if (chechbox) {
			loginPrefsEditor.putBoolean("saveLogin", true);
			loginPrefsEditor.putString("username", sInEmail);
			loginPrefsEditor.putString("password", sInPassword);
			loginPrefsEditor.commit();
		} else {
			loginPrefsEditor.clear();
			loginPrefsEditor.commit();
		}
	}


	private void clearForm(){
		etEmail.setText("");
		etPass.setText("");
	}
	private boolean cekFormSignIn(String email, String pass){
		etEmail.setError(null);
		etPass.setError(null);
		View focusView=null;
		boolean formStats=false;
		//cek password
		if (pass.matches("")){
			etPass.setError("tidak boleh kosong");
			focusView=etPass;
			formStats=true;
		}
		//cek format email
		if (!SignUpActivity.isEmailValid(email)){
			etEmail.setError("format email tidak benar");
			focusView=etEmail;
			formStats=true;
		}
		//cek email kosong ?
		if (email.matches("")){
			etEmail.setError("tidak boleh kosong");
			focusView=etEmail;
			formStats=true;
		}
		//menandai kolom yang tidak sesuai
		if (formStats) {
			focusView.requestFocus();
		}
		return formStats;
	}

	/*Handle Login Google, Facebook, api.chatak.xyz*/
	//Login google integrtd firebase
	private void signInGoogle() {
		Intent signInIntent = Chatak.mGoogleSignInClient.getSignInIntent();
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Pass the activity result back to the Facebook SDK
		FacebookAuth.callbackManager.onActivityResult(requestCode, resultCode, data);
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				// Google Sign In was successful, authenticate with Firebase
				GoogleSignInAccount account = task.getResult(ApiException.class);
				Log.w(TAG, "Google_sign_in_success");
				firebaseAuthWithGoogle(account,account.getEmail());
				//atur nama class user



			} catch (ApiException e) {
				// Google Sign In failed, update UI appropriately
				Log.w(TAG, "Google_sign_in_failed", e);
				// ...
			}
		}
	}
	private void firebaseAuthWithGoogle(final GoogleSignInAccount acct, final String user_email) {
		Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							progressDialog.show();
							Log.i(TAG,"firebaseSignInAuthGoogle:Success");
							FirebaseUser user = mAuth.getCurrentUser();
							//check is current google login new user ?
							isNewUser(user,user_email,"google");
						} else {
							progressDialog.dismiss();
							// If sign in fail, display a message to the user.
							Log.w(TAG, "signInWithCredential:failure", task.getException());
						}
					}
				});
	}
	//Login facebook integrtd firebase
	private void handleFacebookAccessToken(AccessToken token) {
		progressDialog.show();
		try {
		Log.d(TAG, "handleFacebookAccessToken:" + token);
		final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
		//handle link account / same email ( google or facebook)
//		linkWithCredential(credential);
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							progressDialog.dismiss();
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "signInWithCredential Facebook and Firebase:success");
							FirebaseUser FUser = mAuth.getCurrentUser();
							isNewUser(FUser,task.getResult().getUser().getEmail(),"facebook");
						} else {
							mergeLinkAccountData(credential);
						}
					}
				}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				progressDialog.dismiss();
				// If sign in fails, display a message to the user.
				Log.e(TAG, "signInWithCredential:failure"+e.getMessage());
				//if login facebook failed. logout facebook
				LoginManager.getInstance().logOut();
//							warn user
				Toast.makeText(SignInActivity.this, "Email menggunakan metode login lain.",
						Toast.LENGTH_LONG).show();
			}
		});
		}catch (Exception e){
			Log.e(TAG, "MergelinkWithCredential:failure"+e.getMessage());
		}
	}
	private void mergeLinkAccountData(AuthCredential credential){
		try {
		FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						FirebaseUser currentUser = task.getResult().getUser();
						if (task.isSuccessful()){
							Log.d(TAG, "MergelinkWithCredential-Email:success");
							updateSession(currentUser.getEmail(),currentUser.getDisplayName());
							updateSessionID(currentUser.getEmail());
							checkSession();
						}
						// Merge prevUser and currentUser accounts and data
						// ...
					}
				}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				Log.w(TAG, "MergelinkWithCredential:failure"+e.getMessage());
				Toast.makeText(SignInActivity.this, "Email menggunakan metode login lain.",
						Toast.LENGTH_LONG).show();

			}
		});
		}catch (Exception e){
			Log.w(TAG, "MergelinkWithCredential-Email:failure"+e.getMessage());
		}
	}
	//masuk akun via api.chatak.xyz + firebase
	private void doLogin (final String email, final String password){
		progressDialog.show();
		final String sEmail=email;
		final String sEnPass=EncryptionAES.encrypt(password);
		//masuk melalui api.chatak.xyx/user
		if (!cekFormSignIn(email,password)) {
			UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
			Call<List<com.unpas.chatak.model.UserModel>> call
					= userApiInterface.getUser_email("admin123",sEmail);
			call.enqueue(new Callback<List<com.unpas.chatak.model.UserModel>>() {
				@Override
				public void onResponse(Call<List<com.unpas.chatak.model.UserModel>> call, Response<List<com.unpas.chatak.model.UserModel>> response) {
					if (response.isSuccessful()) {
						if (!response.body().isEmpty()){
							for(com.unpas.chatak.model.UserModel userModel :response.body()){
								//cek user terdaftar ?
								Log.d(TAG,"id:"+userModel.getUser_id());
								String ID = String.valueOf(userModel.getUser_id());
								loginEmailFirebase(ID,sEmail,sEnPass);
							}
						}else{
							Log.d("Load user_data","no_user_data");
							alertSignInFailed();
						}
					}else{
						Log.e("Load user_data","failed response.body()"+response.errorBody().toString());
					}
				}
				@Override
				public void onFailure(Call<List<com.unpas.chatak.model.UserModel>> call, Throwable t) {
					Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
					Log.e("load user_data",t.getMessage());
				}
			});
		}
	}
	/*end of Handle Login Google, Facebook, api.chatak.xyz*/

	/*an other support login method*/
	private void loginEmailFirebase(final String id, final String email, final String password) {
		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							progressDialog.dismiss();
							FirebaseUser user = mAuth.getCurrentUser();
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "signInWithEmail:success");
							updateSession(user.getEmail(), user.getDisplayName());
							updateSessionID(user.getEmail());
							checkSession();

						} else {
							progressDialog.dismiss();
							// If sign in fails, display asignInWithEmail:success message to the user.
							Log.w(TAG, "signInWithEmail:failure", task.getException());
							alertSignInFailed();
						}
					}
				});

	}
	private void updateSession(String email,String nama){
		sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA,nama);
		sharedPrefManager.saveSPString(sharedPrefManager.SP_EMAIL,email);
		sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN,true);
	}
	private boolean checkAccessTokenFacebook(){
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
		return isLoggedIn;
	}
	private void checkSession(){
//		cek apakah sesi sudah ditambah
		if (Chatak.sharedPrefManager.getSPSudahLogin()){
			startActivity(new Intent(SignInActivity.this, MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
			finish();
		}
	}
	private void alertSignInFailed(){
		final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(SignInActivity.this);
//		dlgAlert.setCancelable(false);
		dlgAlert.setTitle("Gagal masuk akun");
		dlgAlert.setMessage("Kata sandi salah atau email tidak terdaftar.");
		dlgAlert.create().show();
		etPass.setText("");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		user.clearData();
		checkSession();
        navView.setSelectedItemId(R.id.navigation_home);
	}
	private void isNewUser(FirebaseUser FUser, String currentEmail,String loginMethod){
		if (loginMethod==GOOGLE){
			if (FUser.getPhoneNumber()==null){
				progressDialog.dismiss();
				Intent i = new Intent(SignInActivity.this, LinkAccountActivity.class);
				Log.i(TAG,"GOOGLE_is new  user ? true");
				//set data current login method for filter sign up
				//set data google email for link to firebase
				Log.v(TAG,currentEmail);
				i.putExtra("login", loginMethod);
				i.putExtra("email", currentEmail);

				startActivity(i);
			}else{
				progressDialog.dismiss();
				Log.i(TAG,"GOOGLE_is new user ? false");
				updateSession(user.getEmail(),FUser.getDisplayName());
				updateSessionID(FUser.getEmail());
				checkSession();
			}
		}else if(loginMethod==FACEBOOK){
			if (FUser.getPhoneNumber()==null){
				progressDialog.dismiss();
				Intent i = new Intent(SignInActivity.this, LinkAccountActivity.class);
				Log.i(TAG,"FACEBOOK new  user ? true");
				//set data current login method for filter sign up
				//set data facebook email for link to firebase
				i.putExtra("login", loginMethod);
				i.putExtra("email", currentEmail);
				startActivity(i);
			}else{
				progressDialog.dismiss();
				Log.i(TAG,"FACEBOOK_is new user ? false");
				updateSession(user.getEmail(),FUser.getDisplayName());
				updateSessionID(FUser.getEmail());
				checkSession();
			}

		}else{
			Log.i(TAG,"isNewUser:error");
		}

	}
	private void updateSessionID(String email){
		UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
		Call<List<com.unpas.chatak.model.UserModel>> call = userApiInterface.getUser_email("admin123",email);
		call.enqueue(new Callback<List<com.unpas.chatak.model.UserModel>>() {
			@Override
			public void onResponse(Call<List<com.unpas.chatak.model.UserModel>> call, Response<List<com.unpas.chatak.model.UserModel>> response) {
				if (response.isSuccessful()) {
					if (!response.body().isEmpty()){
						for(com.unpas.chatak.model.UserModel userModel :response.body()){
							//cek user terdaftar ?
							Log.d(TAG,"id:"+userModel.getUser_id());
							String ID = String.valueOf(userModel.getUser_id());
							sharedPrefManager.saveSPString(SharedPrefManager.SP_ID,ID);

						}
					}else{
						Log.d("Load user_data","no_user_data");
						alertSignInFailed();
					}
				}else{
					Log.e("Load user_data","failed response.body()"+response.errorBody().toString());
				}
			}
			@Override
			public void onFailure(Call<List<com.unpas.chatak.model.UserModel>> call, Throwable t) {
				Log.e("load user_data",t.getMessage());
			}
		});
	}
}