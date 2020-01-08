package com.unpas.chatak;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.unpas.chatak.main_page.MainActivity;
import com.unpas.chatak.model.UserModel;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.rest.UserApiInterface;
import com.unpas.chatak.transaksi_page.EncryptionAES;
import com.unpas.chatak.transaksi_page.SignInActivity;
import com.unpas.chatak.transaksi_page.SignUpActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unpas.chatak.Chatak.sharedPrefManager;


public class LinkAccountActivity extends AppCompatActivity  implements View.OnClickListener {
    private final String TAG="LinkAct_test",FACEBOOK="facebook",GOOGLE="google";
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private EditText etPhone;
    private String loginMethod;
    private TextView tvInfoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_account);
        //firebase inisialisasi
        mAuth = FirebaseAuth.getInstance();
        //initialize btn
        findViewById(R.id.btnSendCodeVerification_linkAct).setOnClickListener(this);
        findViewById(R.id.btnResendCodeVerf_linkAct).setOnClickListener(this);
        findViewById(R.id.btnCheckCodeVerf_linkAct).setOnClickListener(this);
        findViewById(R.id.btnSignUp_linkAct).setOnClickListener(this);
        findViewById(R.id.textViewSyaratAturanKebijakan_linkAct).setOnClickListener(this);
        findViewById(R.id.textViewSyaratAturanPengguna_linkAct).setOnClickListener(this);
        findViewById(R.id.imageButtonBackSignIn).setOnClickListener(this);
        //hide some edit text and button
        setVisibilityForm(View.GONE);
        findViewById(R.id.llBtnSendVerification_linkAct).setVisibility(View.GONE);
        findViewById(R.id.editTextVerificationCode_linkAct).setVisibility(View.GONE);
        //inisialisation edit text
        etPhone=findViewById(R.id.editTextNoHandphone_linkAct);
        tvInfoLogin=findViewById(R.id.tvInfoLogin);
        //OTP
        phoneAuthCallbacks();
        //filter metode login
        filterLoginMethod();
        if(loginMethod=="facebook") {
            //set text view is user logged successfuly
            tvInfoLogin.setText("Berhasil ditautkan dengan facebook");
            Log.e(TAG+"FB",getIntent().getStringExtra("email"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
       alertCancelRegister();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSendCodeVerification_linkAct:
                try {
                    if (checkFormNoHp()){
                        startPhoneNumberVerification(etPhone.getText().toString().trim());
                        Log.d(TAG, "mVerificationInProgress : " + mVerificationInProgress);
                        break;
                    }
                }catch (Exception e){
                    Log.e(TAG,"error send code : "+e.getMessage());
                }
            break;
            case R.id.btnResendCodeVerf_linkAct:
                if (checkFormNoHp()){
                    resendVerificationCode(etPhone.getText().toString().trim(),mResendToken);
                    Log.d(TAG, "mVerificationInProgress : " + mVerificationInProgress);
                }
            break;
            case R.id.btnCheckCodeVerf_linkAct:
                EditText etVerfCode = findViewById(R.id.editTextVerificationCode_linkAct);
                verifyPhoneNumberWithCode(mVerificationId,etVerfCode.getText().toString());
            break;
            case R.id.btnSignUp_linkAct:
                if (checkFormSignUp()){
                    final EditText etEmail=  findViewById(R.id.editTextSEmail_linkAct);
                    final EditText etPass=  findViewById(R.id.editTextSPassword_linkAct);
                    getEmailCredentials(etEmail.getText().toString().trim(),etPass.getText().toString());
                }
            break;
            case R.id.textViewSyaratAturanKebijakan_linkAct:

                Intent webviewintent = new Intent(getApplicationContext(), KetentuanPenggunaActivity.class);
                webviewintent.putExtra("kebijakan_privasi", "http://www.pakar.xyz/privasi/");
                startActivity(webviewintent);
            break;
            case R.id.textViewSyaratAturanPengguna_linkAct:
                webviewintent = new Intent(getApplicationContext(), KetentuanPenggunaActivity.class);
                webviewintent.putExtra("aturan_pengguna", "http://www.pakar.xyz/privasi/");
                startActivity(webviewintent);
            break;
            case R.id.imageButtonBackSignIn:
                onBackPressed();
                break;
        }
    }

    private void setVisibilityForm(int x){
        findViewById(R.id.editTextNamaLengkap_linkAct).setVisibility(x);
        findViewById(R.id.editTextSPassword_linkAct).setVisibility(x);
        findViewById(R.id.editTextSConfirmPassword_linkAct).setVisibility(x);
        findViewById(R.id.cbSetuju_linkAct).setVisibility(x);
        findViewById(R.id.txtAturan_linkAct).setVisibility(x);
        findViewById(R.id.btnSignUp_linkAct).setVisibility(x);
    }
    private void filterLoginMethod() {
        loginMethod=getIntent().getStringExtra("login");
        String email=getIntent().getStringExtra("email");
        EditText etEmail = findViewById(R.id.editTextSEmail_linkAct);
        etEmail.setText(email);
        Log.d(TAG,"ekstra "+loginMethod+" | "+email);
    }
    private void newSignInFirebaseUser(final AuthCredential credential){
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "newSignInFirebaseUser:success");
                        } else {
                            Log.w(TAG, "newSignInFirebaseUser:failure", task.getException());
                            Toast.makeText(LinkAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"SignIn-Firebase failure : "+e.getMessage());
                mergeExistFirebaseUser(credential);
            }
        });
    }

    private void mergeExistFirebaseUser(AuthCredential credential){
        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
        //create new user firebase
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i(TAG,"mergeExistFirebaseUser-Success");
                        FirebaseUser currentUser = task.getResult().getUser();
                        // Merge prevUser and currentUser accounts and data
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"mergeExistFirebaseUser-failure : "+e.getMessage());
            }
        });
    }
    private boolean isNewUser(FirebaseUser user){
        if (user.getDisplayName()==null||user.getEmail()==null){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkFormNoHp(){
        EditText etNoHp = findViewById(R.id.editTextNoHandphone_linkAct);
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
        final EditText etNoHp = findViewById(R.id.editTextNoHandphone_linkAct);
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
                linkcredential(credential);
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
                findViewById(R.id.btnSendCodeVerification_linkAct).setVisibility(View.GONE);
                findViewById(R.id.editTextVerificationCode_linkAct).setVisibility(View.VISIBLE);
                findViewById(R.id.llBtnSendVerification_linkAct).setVisibility(View.VISIBLE);
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
    private void linkcredential(AuthCredential credential){
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential-phone:success");
                            TextView txtInfoReg = findViewById(R.id.txtInfoReg_linkAct);
                            txtInfoReg.setText("Otentikasi nomor telepon berhasil, silahkan isi data pribadi");
                            findViewById(R.id.editTextNoHandphone_linkAct).setEnabled(false);
                            findViewById(R.id.llBtnSendVerification_linkAct).setVisibility(View.GONE);
                            findViewById(R.id.editTextVerificationCode_linkAct).setVisibility(View.GONE);
                            setVisibilityForm(View.VISIBLE);


                        } else {
                            Log.w(TAG, "linkWithCredential-phone:failure", task.getException());
                            Toast.makeText(LinkAccountActivity.this, "Otentikasi gagal, nomor telepon sudah digunakan.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"linkAccountFailure:"+e.getMessage());
            }
        });
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]

        linkcredential(credential);
    }
    public void getEmailCredentials(String email,String password) {
        EncryptionAES encryptionAES = new EncryptionAES();
        final String user_email = email;
        final String encryptPassword = encryptionAES.encrypt(password);
        // [START auth_email_cred]
        final AuthCredential credential = EmailAuthProvider.getCredential(user_email, encryptPassword);
        // [END auth_email_cred]
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential-Email:success");
                            EditText fullName = findViewById(R.id.editTextNamaLengkap_linkAct);
                            EditText phoneNumber = findViewById(R.id.editTextNoHandphone_linkAct);
                            //get current fullname and update to api-database + firebase
                            updateProfile(fullName.getText().toString(),encryptPassword,phoneNumber.getText().toString());
                        } else {
                            mergeLinkAccountData(credential);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"linkAccountFailure:"+e.getMessage());
            }
        });
    }
    private void mergeLinkAccountData(AuthCredential credential){
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
                        }else{
                            Log.w(TAG, "linkWithCredential-Email:failure", task.getException());
                            Toast.makeText(LinkAccountActivity.this, "Gagal mendaftarkan",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // Merge prevUser and currentUser accounts and data
                        // ...
                    }
                });
    }
    private void updateSession(String email,String nama){
        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA,nama);
        sharedPrefManager.saveSPString(sharedPrefManager.SP_EMAIL,email);
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN,true);
    }
    private void checkSession(){
//		cek apakah sesi sudah ditambah
        if (Chatak.sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LinkAccountActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
    private void updateSessionID(String email){
        UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
        Call<List<UserModel>> call = userApiInterface.getUser_email("admin123",email);
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
    //cek form sign up
    public boolean checkFormSignUp() {
        View focusView = null;
        boolean tambahData = true;
        EditText etEmail = findViewById(R.id.editTextSEmail_linkAct);
        EditText etNama = findViewById(R.id.editTextNamaLengkap_linkAct);
        EditText etPass = findViewById(R.id.editTextSPassword_linkAct);
        EditText etConfPass = findViewById(R.id.editTextSConfirmPassword_linkAct);
        CheckBox cbSetuju = findViewById(R.id.cbSetuju_linkAct);

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
    //pengecekan format email harus terdapat karakter @
    private static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void updateProfile(final String fullName, final String password, final String phoneNumber){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.i(TAG,"current user : "+user.getEmail());
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            newAccountApiChatak(user.getEmail(),"third_party_handled","third_party_handled","third_party_handled");
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
    public void newAccountApiChatak(final String email, final String password, final String nama, final String nohp) {
        /* set title dialogue alert */
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LinkAccountActivity.this);
        dlgAlert.setTitle("Daftar akun dengan Google");
        //sign up sementara
        UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
        try {
            Call<ResponseBody> call = userApiInterface.postNew_User("admin123", email, password, nama, nohp);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {

                        Log.d(TAG, "SignUp_Api : " + "add new user Successful via api");
                        dlgAlert.setMessage("Sukses mendaftarkan akun.");
                        dlgAlert.setCancelable(false);
                        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set current firebase auth as not logged
                                mAuth.signOut();
                                LoginManager.getInstance().logOut();
                                //close this activity
                                finish();
                            }
                        });

                    } else {
                        Log.d(TAG, "SignUp_Api : " + "add new user Failed "+response.message());
                        dlgAlert.setMessage("Gagal mendaftarkan akun.").create().show();
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
    private void deleteUserFirebase (){
        FirebaseUser user = mAuth.getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            LinkAccountActivity.super.onBackPressed();
                        }
                    }
                });
    }
    private void alertCancelRegister(){
        AlertDialog.Builder dlgAlert= new AlertDialog.Builder(LinkAccountActivity.this);
        dlgAlert.setTitle("Batal daftar akun");
        dlgAlert.setMessage("Ingin membatalkan ?, data yang sudah di isi akan dihapus secara permanen.");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteUserFirebase();

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dlgAlert.create().show();
    }

}
