package com.unpas.chatak.transaksi_page;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.unpas.chatak.KetentuanPenggunaActivity;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.rest.UserApiInterface;
import com.unpas.chatak.R;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "SignUpActivity_test";
	private EditText etNama, etNoHp, etEmail, etPass, etConfPass, etVerificationCode;
	private CheckBox cbSetuju;
	AlertDialog.Builder dlgAlert;
	private FirebaseAuth mAuth;
	private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
	private boolean mVerificationInProgress = false, isPhoneNumberBeingRegistered =false;
	private String mVerificationId,sEmail,sNoHp,sNama,sEnPass;
	private PhoneAuthProvider.ForceResendingToken mResendToken;
	private TextView txtBtnPhoneVerification, txtNama, txtEmail,txtConfPass,txtPass, txtAturan;
	private PhoneAuthCredential credential;
	private Button btnSignUp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		mAuth = FirebaseAuth.getInstance();
		//tombol signUp
		//onclick listener tombol
		findViewById(R.id.btnSignUp).setOnClickListener(this);
		findViewById(R.id.textViewSyaratAturanPengguna).setOnClickListener(this);
		findViewById(R.id.textViewSyaratAturanKebijakan).setOnClickListener(this);
		cbSetuju = findViewById(R.id.cbSetuju);
		findViewById(R.id.imageButtonBackSignIn).setOnClickListener(this);
		findViewById(R.id.btnSendCodeVerification).setOnClickListener(this);
		findViewById(R.id.btnResendCodeVerf).setOnClickListener(this);
		findViewById(R.id.btnCheckCodeVerf).setOnClickListener(this);
		btnSignUp=findViewById(R.id.btnSignUp);

		txtNama=findViewById(R.id.editTextNamaLengkap);
		txtEmail=findViewById(R.id.editTextSEmail);
		txtPass=findViewById(R.id.editTextSConfirmPassword);
		txtConfPass=findViewById(R.id.editTextSConfirmPassword);


		//kolom inputan
		etNama = findViewById(R.id.editTextNamaLengkap);
		etNoHp = findViewById(R.id.editTextNoHandphone);
		etEmail = findViewById(R.id.editTextSEmail);
		etPass = findViewById(R.id.editTextSPassword);
		etConfPass = findViewById(R.id.editTextSConfirmPassword);
		etVerificationCode = findViewById(R.id.editTextVerificationCode);
		etVerificationCode.setVisibility(View.GONE);
		//

		dlgAlert = new AlertDialog.Builder(SignUpActivity.this);
//		OTP
		phoneAuthCallbacks();
		//signup button not clickable before user succeded phone number auth
		btnSignUp.setClickable(false);
		setVisibleForm(View.GONE);
		findViewById(R.id.llBtnSendVerification).setVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSignUp:
				FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
				//cek form sign up apakah data sesuai dengan format yang dinginkan
				if (checkFormSignUp()&&user!=null) {
					sEmail=etEmail.getText().toString();
					sNoHp=etNoHp.getText().toString();
					sNama=etNama.getText().toString();
					sEnPass=EncryptionAES.encrypt(etPass.getText().toString());
					linkCredential(getCredentialEmailAuth(sEmail,sEnPass));
					isPhoneNumberRegistered();
				}
				break;
			//jika memilih halaman menampilkan halaman aturan pengguna
			case R.id.textViewSyaratAturanPengguna:
				Intent webviewintent = new Intent(getApplicationContext(), KetentuanPenggunaActivity.class);
				webviewintent.putExtra("aturan_pengguna", "http://www.pakar.xyz/privasi/");
				startActivity(webviewintent);
				break;
			//jika memilih halaman menampilkan halaman aturankebijakan
			case R.id.textViewSyaratAturanKebijakan:
				webviewintent = new Intent(getApplicationContext(), KetentuanPenggunaActivity.class);
				webviewintent.putExtra("kebijakan_privasi", "http://www.pakar.xyz/privasi/");
				startActivity(webviewintent);
				break;
			case R.id.imageButtonBackSignIn:
				onBackPressed();
				break;
			case R.id.btnSendCodeVerification:
				if (checkFormNoHp()){
						startPhoneNumberVerification(etNoHp.getText().toString());
						Log.d(TAG, "mVerificationInProgress : " + mVerificationInProgress);
						break;
				}
				break;
			case R.id.btnResendCodeVerf:
				resendVerificationCode(etNoHp.getText().toString(),mResendToken);
				break;
			case R.id.btnCheckCodeVerf:
				verifyPhoneNumberWithCode(mVerificationId, etVerificationCode.getText().toString());
				break;
		}
	}

	//cek form sign up
	public boolean checkFormSignUp() {
		View focusView = null;
		boolean tambahData = true;
		String sNama = etNama.getText().toString();
		String sEmail = etEmail.getText().toString();
		String sPassword = etPass.getText().toString();
		String sConfPass = etConfPass.getText().toString();
		//atur error default
		etNama.setError(null);
		etEmail.setError(null);
		etPass.setError(null);
		etConfPass.setError(null);
		//ketentuan minimal digit formulir
		int minNama = 4, minNoHp = 11, minEmail = 4, minPass = 6;
		String minimal = "Minimal ", digit = " digit", huruf = " huruf";
		//jika konfirmasi pass kosong
		if ((sConfPass.matches(""))) {
			etConfPass.setError("tidak boleh kosong");
			focusView = etConfPass;
			tambahData = false;
		} else {
			//  jika konf tidak sama
			if (!(sConfPass.equals(sPassword))) {
				etConfPass.setError("Password tidak sama");
				focusView = etConfPass;
				tambahData = false;
			} else {
				//jika check box tidak di cekklis
				if (!(cbSetuju.isChecked())) {
					cbSetuju.setError("Harus menyetujui");
					Toast.makeText(this, "Anda harus menyetujui Aturan pengguna dan " +
							"kebijakan privasi dari Chatak", Toast.LENGTH_LONG).show();
					focusView = cbSetuju;
					tambahData = false;
				}
			}
		}
		//jika kolom pass kosong
		if (sPassword.matches("")) {
			etPass.setError("Tidak boleh kosong");
			focusView = etPass;
			tambahData = false;
		} else {
			//  jika minimal karakter password tidak sesuai
			if (!(sPassword.length() >= minPass)) {
				etPass.setError(minimal + minPass + huruf);
				focusView = etPass;
				tambahData = false;
			}
		}
		//jika minimal karakter  email tidak sesuai
		if (!isEmailValid(sEmail)) {
			etEmail.setError("Format email tidak sesuai");
			focusView = etEmail;
			tambahData = false;
		}
		if (etEmail.getText().toString().matches("")) {
			etEmail.setError("Email tidak boleh kosong");
			focusView = etEmail;
			tambahData = false;
		}

		//jika minimal karakter nama tidak sesuai
		if (!(sNama.length() >= minNama)) {
			etNama.setError(minimal + minNama + huruf);
			focusView = etNama;
			tambahData = false;
		}
		//cek apakah tambah data tidak akan diproses ?
		if (!tambahData) {
			focusView.requestFocus();
		}
		return tambahData;
	}

	public boolean checkFormNoHp(){
		View focusView = null;
		boolean tambahData = true;
		int minNama = 3, minNoHp = 11, minEmail = 4, minPass = 6;
		String minimal = "Minimal ", digit = " digit", huruf = " huruf";
		String sNoHp = etNoHp.getText().toString();
		etNoHp.setError(null);
		//jika nomor hp minimal karakter tidak sesuai
		if (!(sNoHp.length() >= minNoHp)) {
			etNoHp.setError(minimal + minNoHp + digit);
			focusView = etNoHp;
			tambahData = false;

		}
		//cek apakah tambah data tidak akan diproses ?
		if (!tambahData) {
			focusView.requestFocus();
		}
		return tambahData;
	}

	//pengecekan format email harus terdapat karakter @
	public static boolean isEmailValid(String email) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public void newAccountApiChatak(final String email, final String password, final String nama, final String nohp) {
		/* set title dialogue alert */
		dlgAlert.setTitle("Daftar akun");
		//sign up sementara
		UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
		try {
			Call<ResponseBody> call = userApiInterface.postNew_User("admin123", email, password, nama, nohp);
			call.enqueue(new Callback<ResponseBody>() {
				@Override
				public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
					if (response.isSuccessful()) {
						Log.d(TAG, "SignUp_Api : " + "add new user Successful via api");
						dlgAlert.setMessage("Sukses mendaftarkan akun. Silahkan Sign in untuk melanjutkan.");
						dlgAlert.setCancelable(false);
						dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								clearForm();
								//set registration as complete
								isPhoneNumberBeingRegistered=false;
								//set current firebase auth as not logged
								mAuth.signOut();
								//close this activity
								finishActivity();
							}
						});
					}
					else {
						Log.d(TAG, "SignUp_Api : " + "add new user Failed"+response.message());
						dlgAlert.setMessage("Gagal mendaftarkan akun. email sudah digunakan").create().show();
					}
					dlgAlert.create().show();
				}

				@Override
				public void onFailure(Call<ResponseBody> call, Throwable t) {
					Log.e(TAG, "SignUp_Api " + t.getMessage());

					/*end set pop up pesan tambah user*/
					dlgAlert.setMessage("Gagal mendaftarkan akun. koneksi bermasalah").create().show();
				}

			});
		} catch (Exception e) {
			Log.e(TAG, "SignUp_API" + e.getMessage());
		}
	}

	private void finishActivity() {
		this.finish();
	}

	private void clearForm() {
		etNama.setText("");
		etEmail.setText("");
		etNoHp.setText("");
		etPass.setText("");
		etConfPass.setText("");
	}

	private void startPhoneNumberVerification(String phoneNumber) {
		// [START start_phone_auth]
		PhoneAuthProvider.getInstance().verifyPhoneNumber(
				phoneNumber,        // Phone number to verify
				60,                 // Timeout duration
				TimeUnit.SECONDS,   // Unit of timeout
				this,               // Activity (for callback binding)
				mCallbacks);        // OnVerificationStateChangedCallbacks
		// [END start_phone_auth]
		mVerificationInProgress = true;
	}

	private void phoneAuthCallbacks() {
		// Initialize phone auth callbacks
		// [START phone_auth_callbacks]
		mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			@Override
			public void onVerificationCompleted(PhoneAuthCredential credential) {
				// This callback will be invoked in two situations:
				// 1 - Instant verification. In some cases the phone number can be instantly
				//     verified without needing to send or enter a verification code.
				// 2 - Auto-retrieval. On some devices Google Play services can automatically
				//     detect the incoming verification SMS and perform verification without
				//     user action.
				Log.d(TAG, "onVerificationCompleted : " + credential);
				// [START_EXCLUDE silent]
				mVerificationInProgress = false;
				// [END_EXCLUDE]
				// [START_EXCLUDE silent]
				// Update the UI and attempt sign in with the phone credential
//				updateUI(STATE_VERIFY_SUCCESS, credential);
				// [END_EXCLUDE]
				signInWithPhoneAuthCredential(credential);
			}

			@Override
			public void onVerificationFailed(FirebaseException e) {
				// This callback is invoked in an invalid request for verification is made,
				// for instance if the the phone number format is not valid.
				Log.w(TAG, "onVerificationFailed", e);
				// [START_EXCLUDE silent]
				mVerificationInProgress = false;
				// [END_EXCLUDE]

				if (e instanceof FirebaseAuthInvalidCredentialsException) {
					// Invalid request
					// [START_EXCLUDE]
					etNoHp.setError("Nomor Telepon tidak valid");
					// [END_EXCLUDE]
				} else if (e instanceof FirebaseTooManyRequestsException) {
					// The SMS quota for the project has been exceeded
					// [START_EXCLUDE]
					Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
							Snackbar.LENGTH_SHORT).show();
					// [END_EXCLUDE]
				}

				// Show a message and update the UI
				// [START_EXCLUDE]
//				updateUI(STATE_VERIFY_FAILED);
				// [END_EXCLUDE]
			}

			@Override
			public void onCodeSent(@NonNull String verificationId,
								   @NonNull PhoneAuthProvider.ForceResendingToken token) {
				// The SMS verification code has been sent to the provided phone number, we
				// now need to ask the user to enter the code and then construct a credential
				// by combining the code with a verification ID.
				Log.d(TAG, "onCodeSent : " + verificationId);
				Snackbar.make(findViewById(android.R.id.content), "Mengirim kode verifikasi, silakan cek kotak SMS Anda.",
						Snackbar.LENGTH_LONG).show();
				etVerificationCode.setVisibility(View.VISIBLE);
				findViewById(R.id.btnSendCodeVerification).setVisibility(View.GONE);
				findViewById(R.id.llBtnSendVerification).setVisibility(View.VISIBLE);
				// Save verification ID and resending token so we can use them later
				mVerificationId = verificationId;
				mResendToken = token;

				// [START_EXCLUDE]
				// Update UI
//				updateUI(STATE_CODE_SENT);
				// [END_EXCLUDE]
			}
		};
		// [END phone_auth_callbacks]
	}

	private void verifyPhoneNumberWithCode(String verificationId, String code) {
		// [START verify_with_code]
		credential = PhoneAuthProvider.getCredential(verificationId, code);
		// [END verify_with_code]

		signInWithPhoneAuthCredential(credential);
	}

	private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "signInWithCredential:success");
							etNoHp.setEnabled(false);
							etVerificationCode.setVisibility(View.GONE);
							findViewById(R.id.btnSendCodeVerification).setVisibility(View.GONE);
							btnSignUp.setClickable(true);
							if (isPhoneNumberRegistered()){
							    etEmail.setEnabled(false);
							    etPass.setEnabled(false);
							    etConfPass.setEnabled(false);
							    etNama.setEnabled(false);
							    etNoHp.setEnabled(true);
							    btnSignUp.setClickable(false);
							    findViewById(R.id.btnSendCodeVerification).setVisibility(View.VISIBLE);
                                dialoguePhoneUsed();
                            }else{
								setVisibleForm(View.VISIBLE);
								TextView txtViewReg=findViewById(R.id.txtInfoReg);
								txtViewReg.setText("Vefirikasi nomor telepon berhasil. Silahkan isi data diri.");
								isPhoneNumberBeingRegistered =true;
								findViewById(R.id.llBtnSendVerification).setVisibility(View.GONE);
							}

						} else {
							// Sign in failed, display a message and update the UI
							Log.w(TAG, "signInWithCredential:failure", task.getException());
							if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
								// The verification code entered was invalid
							}
						}
					}
				});

	}

	public boolean isPhoneNumberRegistered() {
		// [START get_user_profile]
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user != null) {
			// Check if user's email is verified
			boolean emailVerified = user.isEmailVerified();
			// The user's ID, unique to the Firebase project. Do NOT use this value to
			// authenticate with your backend server, if you have one. Use
			// FirebaseUser.getIdToken() instead.
			String uid = user.getUid();
			Log.d(TAG, "nama : "+user.getDisplayName());
			Log.d(TAG, "email : "+user.getEmail());
			Log.d(TAG, "nomo hp: "+user.getPhoneNumber());
		}
		if (user.getEmail()!=null&&user.getDisplayName()!=null){
			Log.d(TAG,"isPhoneNumberUsed : "+true);
		    return true;
        }else {
			Log.d(TAG,"isPhoneNumberUsed : "+false);
		    return false;
        }
		// [END get_user_profile]
	}

	private AuthCredential getCredentialEmailAuth(String email, String password){
		AuthCredential credential = EmailAuthProvider.getCredential(email, password);
		return credential;
	}

	private void linkCredential(AuthCredential credential){
		try {
		mAuth.getCurrentUser().linkWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "linkWithCredential:success");
							FirebaseUser user = task.getResult().getUser();
							UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
									.setDisplayName(sNama)
									.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
									.build();

							user.updateProfile(profileUpdates)
									.addOnCompleteListener(new OnCompleteListener<Void>() {
										@Override
										public void onComplete(@NonNull Task<Void> task) {
											if (task.isSuccessful()) {
												Log.d(TAG, "User profile updated.");
											}else{
												Log.d(TAG, "User profile failed : "+task.getException().getMessage());
											}
										}
									});
							newAccountApiChatak(sEmail
									,"third_parties_handled"
									,"third_parties_handled"
									,"third_parties_handled"
							);
//							updateUI(user);
						} else {
							Log.w(TAG, "linkWithCredential:failure", task.getException());
							Toast.makeText(SignUpActivity.this, "Gagal mendaftarkan. nomor telepon / email sudah dugunakan.",
									Toast.LENGTH_SHORT).show();
//							updateUI(null);
						}
					}
				});
		}catch (Exception e ){
			Log.e(TAG,"error current user : "+e.getMessage());
		}
	}

	private void dialoguePhoneUsed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Nomor telepon sudah digunakan, gunakan nomor lain")
                .setTitle("Otentikasi nomor telepon")
                .setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               etNoHp.setText("");
            }
        });
        builder.create().show();
    }

    private void setVisibleForm(int x ){
		etNama.setVisibility(x);
		etEmail.setVisibility(x);
		etPass.setVisibility(x);
		etConfPass.setVisibility(x);
		cbSetuju.setVisibility(x);
		findViewById(R.id.txtAturan).setVisibility(x);
		btnSignUp.setVisibility(x);


	}

	private void isUserBackPressedWhileBeingRegistered(){
		if (isPhoneNumberBeingRegistered){
			//verify user want to cancel the registration
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Anda sedang mendaftar akun, yakin ingin membatalkan ?")
					.setTitle("Pendaftaran akun baru")
					.setCancelable(false);
			builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// unlink the phone number was being registered
					unlinkPhoneReg();
				}
			});
			builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					// keep stay on SignUp Activity
				}
			});
			builder.create().show();
//
		}else {
			//imidiate back to preveous Activity if no phone number was being registered
			super.onBackPressed();
		}
	}

	private void unlinkPhoneReg(){
//		firebase.auth().currentUser.unlink(firebase.auth.PhoneAuthProvider.PROVIDER_ID);
		try {
			mAuth.getCurrentUser().unlink(PhoneAuthProvider.PROVIDER_ID).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isComplete()){
						if (task.isSuccessful()){
							SignUpActivity.super.onBackPressed();
							Log.d(TAG,"Unlink phone number Success  ");
						}else{
							finishActivity();
							Log.e(TAG,"Unlink phone number error : " +task.getException().getMessage());

						}
					}
				}
			});
		}catch (Exception e){
			Log.e(TAG,"mAuth unlink phone number error : "+e.getMessage());
		}
	}

	@Override
	public void onBackPressed() {
		if (isPhoneNumberBeingRegistered){
			isUserBackPressedWhileBeingRegistered();
		}else{
			super.onBackPressed();
		}

	}
	// [START resend_verification]
	private void resendVerificationCode(String phoneNumber,
										PhoneAuthProvider.ForceResendingToken token) {
		PhoneAuthProvider.getInstance().verifyPhoneNumber(
				phoneNumber,        // Phone number to verify
				60,                 // Timeout duration
				TimeUnit.SECONDS,   // Unit of timeout
				this,               // Activity (for callback binding)
				mCallbacks,         // OnVerificationStateChangedCallbacks
				token);             // ForceResendingToken from callbacks
	}
	// [END resend_verification]
	private void addEmailAPI(){

	}

}
