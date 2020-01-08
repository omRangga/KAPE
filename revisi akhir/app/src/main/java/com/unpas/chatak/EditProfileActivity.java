package com.unpas.chatak;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;
import com.unpas.chatak.main_page.MainActivity;
import com.unpas.chatak.model.PostModel;
import com.unpas.chatak.model.PutModel;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.rest.UserApiInterface;
import com.unpas.chatak.transaksi_page.EncryptionAES;
import com.unpas.chatak.transaksi_page.SignInActivity;

import java.io.File;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unpas.chatak.transaksi_page.SignInActivity.user;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {
    private int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private EditText editTextNamaLengkap,editTextEmail, editTextTelepon,editTextKodeVerifikasi, emailbaru,password ;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    CircleImageView civEditFotoProfile;
    final static String TAG = "EditProfAct_test", MSG_FAIL_UPLOAD="Koneksi bermasalah",TITLE_FAIL_UPLOAD="Gagal upload gambar";
    private ProgressDialog progressdialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //assign circle photo for edit
        civEditFotoProfile = findViewById(R.id.edit_profile_image);
        //assing toolbar text
        Toolbar toolBar = findViewById(R.id.toolbarEditProfile);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Edit Profile");
        //assign editext
        editTextNamaLengkap = findViewById(R.id.edit_nama_lengkap);
        editTextEmail = findViewById(R.id.edit_email_activity);
        editTextTelepon =findViewById(R.id.edit_phone);
        editTextKodeVerifikasi=findViewById(R.id.edit_kode_verifikasi);
        //assign on touch listener
        editTextTelepon.setOnTouchListener(this);
        //assign btn listener
        findViewById(R.id.simpan_edit_profile_btn).setOnClickListener(this);
        findViewById(R.id.kirim_kode_btn).setOnClickListener(this);
        findViewById(R.id.kirim_ulang_btn).setOnClickListener(this);
        findViewById(R.id.cek_kode_btn).setOnClickListener(this);
        findViewById(R.id.edit_foto_profile_btn).setOnClickListener(this);
        findViewById(R.id.edit_email_btn).setOnClickListener(this);
        findViewById(R.id.batal_kirim_kode_btn).setOnClickListener(this);
        findViewById(R.id.batal_simpan_edit_profile_btn).setOnClickListener(this);
        //set text nama lengkap, email dan telepon
        editTextNamaLengkap.setText(user.getFull_name());
        editTextEmail.setText(user.getEmail());
        editTextTelepon.setText(user.getNo_hp());
        //get current user
        mAuth = FirebaseAuth.getInstance();
        //hide edit phone button
        setVisibilityForm(View.GONE);
        //phone auth
        phoneAuthCallbacks();
        //progressbar
        progressDialogueConf();


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        //load current firebase user photo
        Picasso.with(getApplicationContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(civEditFotoProfile);
    }

    private void progressDialogueConf() {
        progressdialogue = new ProgressDialog(this);
        progressdialogue.setCancelable(false);
        progressdialogue.setIndeterminate(false);
        progressdialogue.setMessage("Mengupload");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simpan_edit_profile_btn:
                if (updateNameUser()&&updatePassword()){
                    Snackbar.make(findViewById(android.R.id.content), "Data sudah disimpan",Snackbar.LENGTH_LONG).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

                }
                break;
            case R.id.batal_simpan_edit_profile_btn:
                finish();
                break;
            case R.id.edit_foto_profile_btn:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), SELECT_PICTURE);
                break;
            case R.id.kirim_kode_btn:
                unlinkCurrentPhone();
                break;
            case R.id.kirim_ulang_btn:
                resendVerificationCode(editTextTelepon.getText().toString().trim(),mResendToken);
                break;
            case R.id.cek_kode_btn:
                verifyPhoneNumberWithCode(mVerificationId,editTextKodeVerifikasi.getText().toString());
                break;
            case R.id.edit_email_btn:
                dialogueUpdateEmail();
                break;
            case R.id.batal_kirim_kode_btn:
                findViewById(R.id.kirim_kode_btn).setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.edit_phone:
                findViewById(R.id.kirim_kode_btn).setVisibility(View.VISIBLE);
                findViewById(R.id.batal_kirim_kode_btn).setVisibility(View.VISIBLE);
            break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Log.i(TAG,"editphoto_URI: "+selectedImageUri);
                Log.i(TAG,"editphoto_path: "+selectedImagePath);
                try {
                    updateUserPhoto(selectedImagePath);
                }catch (Exception e){
                    Log.e(TAG,"editphoto_upload:"+e.getMessage());
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void refreshProfile(){
        FirebaseUser FUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setEmail(FUser.getEmail());
        user.setFull_name(FUser.getDisplayName());
    }
    public void getGoogleCredentials() {
        String googleIdToken = "";
        // [START auth_google_cred]
        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
        // [END auth_google_cred]
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
//                            updateUI(user);
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(EditProfileActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
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
                    Snackbar.make(findViewById(android.R.id.content), "Melebihi batas maksimal permintaan OTP.",
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
                setVisibilityForm(View.VISIBLE);
                findViewById(R.id.kirim_kode_btn).setVisibility(View.GONE);
                findViewById(R.id.batal_kirim_kode_btn).setVisibility(View.GONE);
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
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]

        linkcredential(credential);
    }
    private void unlinkCurrentPhone(){
            mAuth.getCurrentUser().unlink(PhoneAuthProvider.PROVIDER_ID).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()){
                        if (task.isSuccessful()){
                            EditText edit_phone= findViewById(R.id.edit_phone);
                            startPhoneNumberVerification(edit_phone.getText().toString().trim());
                            Log.d(TAG,"Unlink phone number Success  ");
                        }else{
                            EditText edit_phone= findViewById(R.id.edit_phone);
                            startPhoneNumberVerification(edit_phone.getText().toString().trim());
                            Log.e(TAG,"Unlink phone number error : " +task.getException().getMessage());

                        }
                    }
                }
            });
    }
    private void setVisibilityForm(int x){
        findViewById(R.id.kirim_kode_btn).setVisibility(x);
        findViewById(R.id.batal_kirim_kode_btn).setVisibility(x);
        findViewById(R.id.kirim_ulang_btn).setVisibility(x);
        findViewById(R.id.cek_kode_btn).setVisibility(x);
        findViewById(R.id.edit_kode_verifikasi).setVisibility(x);
    }
    private void linkcredential(AuthCredential credential){
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential-phone:success");
                            Snackbar.make(findViewById(android.R.id.content),"Nomor telepon berhasil diubah",Snackbar.LENGTH_LONG).show();
                            findViewById(R.id.cek_kode_btn).setVisibility(View.GONE);
                            findViewById(R.id.kirim_ulang_btn).setVisibility(View.GONE);
                            findViewById(R.id.kirim_kode_btn).setVisibility(View.GONE);
                            findViewById(R.id.kirim_ulang_btn).setVisibility(View.GONE);
                            editTextKodeVerifikasi.setVisibility(View.GONE);
                        } else {
                            Log.w(TAG, "linkWithCredential-phone:failure", task.getException());
                            Toast.makeText(EditProfileActivity.this, "Otentikasi gagal, nomor telepon sudah digunakan.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"linkAccountFailure:"+e.getMessage());
            }
        });
    }
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
    private void dialogueUpdateEmail(){
        progressdialogue.dismiss();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfileActivity.this);
        final LayoutInflater inflater = getLayoutInflater();
        alertDialog.setTitle("Ubah email");
        alertDialog.setMessage("masukan password");
        alertDialog.setCancelable(false);
        View vEditEmail = inflater.inflate(R.layout.edit_email,null);
        alertDialog.setView(vEditEmail);
        emailbaru = vEditEmail.findViewById(R.id.editTextEmailbaru);
        password = vEditEmail.findViewById(R.id.editTextPasswordSaatIni);
        alertDialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                EncryptionAES encryptionAES = new EncryptionAES();
                String sEnPassword=encryptionAES.encrypt(password.getText().toString());
                //reauthentikasi

                reauthenticationEmailFirebase(sEnPassword);
                //update firebase email


            }
        });
        alertDialog.setNegativeButton("batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialog.create().show();
    }
    private boolean updateNameUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final EditText edit_name = findViewById(R.id.edit_nama_lengkap);
        String new_name = edit_name.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(new_name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "name updated.");
                            }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "UpdateFailure:"+e.getMessage());
            }
        });
        return true;
    }
    private void reauthenticationEmailFirebase(final String sEnPassword){
        try {
            progressdialogue.show();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), sEnPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                            dialogue->authentic->update api->edit firebase
                            updateEmailAPI(sEnPassword,user);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"error_reauth1:"+e.getMessage());
                    Snackbar.make(findViewById(android.R.id.content),"Gagal merubah email",Snackbar.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Snackbar.make(findViewById(android.R.id.content),"Gagal merubah email",Snackbar.LENGTH_LONG).show();
            progressdialogue.dismiss();
            Log.e(TAG,"error_reauth2:"+e.getMessage());
        }
    }
    private void updateEmailFirebase(FirebaseUser user){
        Log.d(TAG, "User being reauthenticated.");
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(emailbaru.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressdialogue.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Berhasil mengubah alamt email",
                                    Snackbar.LENGTH_LONG).show();
                            editTextEmail.setText(mAuth.getCurrentUser().getEmail());
                        }
                    }
                });
    }
    private boolean updatePassword() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        EditText etPassword = findViewById(R.id.edit_password);
        EncryptionAES  encryptionAES = new EncryptionAES();
        String newPassword = encryptionAES.encrypt(etPassword.getText().toString());
        if (!etPassword.getText().toString().matches("")){
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                                new MainActivity().doLogOut();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "error password :"+e.getMessage());
                }
            });
            return true;
        }else{
            return true;
        }
    }

    private void updateEmailAPI(final String sEnPassword, final FirebaseUser fuser){
        try {
//        progressdialogue.show();
        final FirebaseUser FUser = mAuth.getCurrentUser();
        UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
        Call<PutModel> call
                = userApiInterface.putCurrentUser("admin123",Chatak.sharedPrefManager.SP_ID,emailbaru.getText().toString(),"",FUser.getDisplayName(),FUser.getPhoneNumber());
        call.enqueue(new Callback<PutModel>() {
            @Override
            public void onResponse(Call<PutModel> call, Response<PutModel> response) {
                if (response.isSuccessful()){
                    Log.e(TAG,response.message());
                    PutModel putModel = response.body();
                    if (putModel.getStatus().equals("success")){
                        Log.i(TAG,"EditEmailAPI: "+putModel.getStatus());
                        updateEmailFirebase(fuser);
                    }else {
                        Log.e(TAG,"Error_Failure | "+"status:"+putModel.getStatus());
                        progressdialogue.dismiss();
                        alertUploadFailed("Update email gagal","Koneksi bermasalah");
                    }

                }else{
                    progressdialogue.dismiss();
                    Snackbar.make(findViewById(android.R.id.content),"Gagal merubah email",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PutModel> call, Throwable t) {
                progressdialogue.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Gagal mengubah email",Snackbar.LENGTH_LONG).show();
                Log.e(TAG,"Error_Failure"+t.getMessage());
            }
        });
        }catch (Exception e){
            progressdialogue.dismiss();
            Snackbar.make(findViewById(android.R.id.content),"Gagal mengubah email",Snackbar.LENGTH_LONG).show();
            Log.e(TAG,"errorUpdateEmailAPI:"+e.getMessage());

        }
    }
    private void updateUserPhoto(final String filePath) {
        UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
        //Create a file object using file path
            progressdialogue.show();
            Bitmap picBitmap = BitmapFactory.decodeFile(filePath);
            final File file = new File(filePath);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part = MultipartBody.Part.createFormData("photo", file.getName(), fileReqBody);
            //get file name
            final String image_name=file.getName();
            //Create request body with text description and text media type

            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), mAuth.getCurrentUser().getUid()+file.getName());
            //
            Call<PostModel> call = userApiInterface.postPhoto_profile("admin123",part, description);
            call.enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(Call<PostModel> call, Response<PostModel> response) {

                    Log.i(TAG,"processing_upload");
                    if (response.isSuccessful())
                    {
                        try {
                            PostModel postModel = response.body();
                            if (postModel.getStatus().equals("success")){
                                updateUserPhotoFirebase(mAuth.getCurrentUser().getUid()+file.getName());
                            }else if(postModel.getStatus().equals("fail")){
                                alertUploadFailed(TITLE_FAIL_UPLOAD,MSG_FAIL_UPLOAD);
                            }
                        }catch (Exception e){
                            progressdialogue.dismiss();
                            alertUploadFailed(TITLE_FAIL_UPLOAD,MSG_FAIL_UPLOAD);
                            Log.e(TAG,"error:"+response.body());
                        }

                    }else {
                        progressdialogue.dismiss();
                        alertUploadFailed(TITLE_FAIL_UPLOAD,MSG_FAIL_UPLOAD);
                        Log.i(TAG,"upload_photo:failed");
                    }

                }
                @Override
                public void onFailure(Call<PostModel> call, Throwable t) {
                    progressdialogue.dismiss();
                    alertUploadFailed(TITLE_FAIL_UPLOAD,MSG_FAIL_UPLOAD);
                    Log.e(TAG,"upload_photo:failed "+t.getMessage());
                }
            });
    }
    private void updateUserPhotoFirebase(String file_name){
        final FirebaseUser FUser = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("http://chatak-api.000webhostapp.com/assets/profile_images/"+file_name))
                .build();

        FUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Picasso.with(getApplicationContext()).load(FUser.getPhotoUrl()).into(civEditFotoProfile);
                            progressdialogue.dismiss();
                            Log.i(TAG,"update_user_photo:success");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "update_user_photo_Failure:"+e.getMessage());
                progressdialogue.dismiss();
            }
        });
    }
    private void alertUploadFailed(String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfileActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }




}
