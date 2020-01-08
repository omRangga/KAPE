package com.unpas.chatak.main_page;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.unpas.chatak.Chatak;
import com.unpas.chatak.EditProfileActivity;
import com.unpas.chatak.KetentuanPenggunaActivity;
import com.unpas.chatak.MyAccountFragment;
import com.unpas.chatak.MyCartFragment;
import com.unpas.chatak.R;
import com.unpas.chatak.SharedPrefManager;
import com.unpas.chatak.model.UserModel;
import com.unpas.chatak.model.database.LocalDB;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.rest.UserApiInterface;
import com.unpas.chatak.transaksi_page.SignInActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.unpas.chatak.Chatak.loginManager;
import static com.unpas.chatak.Chatak.mGoogleSignInClient;
import static com.unpas.chatak.Chatak.sharedPrefManager;
import static com.unpas.chatak.transaksi_page.SignInActivity.user;


public class MainActivity extends AppCompatActivity {
    public static LocalDB db;
    final String TAG = "MainActivity";
    SharedPrefManager sharedPrefManager;
    ImageView imageView;
    boolean status = false;
    ImageButton imageButton;
    public static BottomNavigationView navView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FirebaseAuth mAuth;
    private boolean recreate;
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_myWishlist:
                    fragment = new WishlistFragment();

                    break;
                case R.id.navigation_profile:
                    if (sharedPrefManager.getSPSudahLogin()) {
                        fragment = new MyAccountFragment();
                    } else {
                        navView.setSelectedItemId(R.id.navigation_home);
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));
                    }
                    break;
                default:
                    fragment = new HomeFragment();
            }
            transaction = fragmentManager.beginTransaction();
            //jika fragment tidak sama dengan null, maka pindah fragment
            if (fragment != null) {
                try {
                    transaction.replace(R.id.container, fragment).commit();
                }catch (Exception e){
                    Log.e(TAG+"back_button",e.getMessage());
                }
            }
            return true;
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        try {
            sharedPrefManager = new SharedPrefManager(this);
            //check if there is logger user
            FirebaseUser FUser = FirebaseAuth.getInstance().getCurrentUser();
            if (sharedPrefManager.getSPSudahLogin()) {
                user.setEmail(FUser.getEmail());
                user.setFull_name(FUser.getDisplayName());
                user.setNo_hp(FUser.getPhoneNumber());
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, FUser.getEmail());
                updateSessionID(FUser.getEmail());
            }
        }catch (Exception e){
            Log.e(TAG,"error "+e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = Room.databaseBuilder(this.getApplicationContext(),
        LocalDB.class, "chatak").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //backPressed
        imageView = findViewById(R.id.whislist_icon);
        imageButton = findViewById(R.id.edit_profile_btn);
        //Toolbar Custom
        aturToolbar();
        isConnected();
//        Firebase notification & in-app messaging
        runtimeEnableAutoInit();
        //debuging purpose
        //Firebase notification token
            retriveCurrentToken();
        //Firebase in-app messaging instance ID
        getFirebaseInstanceID();


    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        //menampilkan icon pada menu item
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        if (sharedPrefManager.getSPSudahLogin()){
            menu.findItem(R.id.itemLogout).setTitle("Logout");
        }else{
            menu.findItem(R.id.itemLogout).setTitle("Sign In");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //sub menu selected
            case R.id.itemSetting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.itemHelp:
                Intent webviewintent = new Intent(getApplicationContext(), KetentuanPenggunaActivity.class);
                webviewintent.putExtra("aturan_pengguna", "http://www.chatak.xyz/");
                startActivity(webviewintent);
                return true;
            case R.id.itemNotif:
                fragment = new NotificationFragment();
                break;
            case R.id.itemKeranjang:
                fragment = new MyCartFragment();
                break;

            case R.id.itemLogout:
                doLogOut();
                break;
            case R.id.itemAbout:
                webviewintent = new Intent(getApplicationContext(), KetentuanPenggunaActivity.class);
                webviewintent.putExtra("aturan_pengguna", "http://www.chatak.xyz/");
                startActivity(webviewintent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        //jika fragment tidak sama dengan null, maka pindah fragment
        if (!(fragment == null)) {
            transaction.replace(R.id.container, fragment).commit();
        }
        return true;
    }

    //atur toolbar custom
    private void aturToolbar() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//set title
        toolBar.setLogo(R.drawable.chatak);//set logo
        toolBar.setPopupTheme(R.style.Widget_AppCompat_Light_ActionBar);
    }

    //on click whislist
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ButtonOnClickWhislist(View v) {
        ImageView view = (ImageView) v;
        if (status == true) {
            view.getDrawable().setTint(getResources().getColor(R.color.merah_chatak));
            status = false;
        } else {
            view.getDrawable().setTint(getResources().getColor(R.color.abu_chatak));
            status = true;
        }

    }

    // menampilkan layout kode promo
    public void ButtonOnClickKodePromo(View v) {
        LinearLayout linearLayout = findViewById(R.id.kodePromoLayout);
        if (status == false) {
            linearLayout.setVisibility(View.VISIBLE);
            status = true;
        } else {
            linearLayout.setVisibility(View.GONE);
            status = false;
        }

    }

    //sembunyikan keyboard saat pengguna mengetuk tempat lain di layar
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    //button view all dokumen
    public void buttonViewAllDokumen(View v) {
        fragment = new DokumenFragment();
        transaction = fragmentManager.beginTransaction();
        if (!(fragment == null)) {
            transaction.replace(R.id.container, fragment).commit();

        }
    }

    //button view all media promosi
    public void buttonViewAllMediaPromosi(View v) {
        fragment = new MediaPromosiFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!(fragment == null)) {
            transaction.replace(R.id.container, fragment).commit();
        }
    }

    //button view all souvenir
    public void buttonViewAllSouvenir(View v) {
        fragment = new SouvenirFragment();
        transaction = fragmentManager.beginTransaction();
        if (!(fragment == null)) {
            transaction.replace(R.id.container, fragment).commit();
        }
    }

    public boolean isConnected() {
        try {
            final ConnectivityManager conn_manager = (ConnectivityManager)
                    this.getSystemService(Context.CONNECTIVITY_SERVICE);

            final NetworkInfo network_info = conn_manager.getActiveNetworkInfo();
            if (network_info != null && network_info.isConnected()) {
                return true;
            } else {
                new AlertDialog.Builder(this).setTitle("Tidak Ada Koneksi")
                        .setMessage("Silahkan Cek Koneksi Internet Anda")
                        .setPositiveButton("Cek", new DialogInterface.OnClickListener() {
                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        }).setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).show();
                return false;
            }
        } catch (Exception e) {
            return false;

        }
    }


    //button view all souvenir
    public void onClickEditProfile(View v) {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }
    public void deleteSession(){
        //hapus session
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, null);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, null);
        startActivity(new Intent(MainActivity.this, SignInActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    public void doLogOut(){
        try {
            if (sharedPrefManager.getSPSudahLogin()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Yakin ingin keluar ?")
                        .setTitle("Keluar akun");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //logout google
                        try {
                            if (mGoogleSignInClient!=null){
                                mGoogleSignInClient.signOut();
                            }
                        }catch (Exception e ){
                            Log.e(TAG,"mGoogleSignClient-failure : "+e.getMessage());
                        }
                        // User clicked OK button
                        navView.setSelectedItemId(R.id.navigation_home);
                        //sign out firebase auth
                        mAuth.getInstance().signOut();
                        //sign out facebook auth
                        LoginManager.getInstance().logOut();
                        deleteSession();
                        user.clearData();

                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.create().show();

            } else {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        } catch (Exception e) {
            Log.e("error_logout", e.getMessage());
        }
    }
    private void retriveCurrentToken(){

        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]
    }
    private void runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        // [END fcm_runtime_enable_auto_init]
    }
    private void getFirebaseInstanceID(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String FBIID = task.getResult().getId();
                Log.i(TAG,"Firebase_instance_ID "+FBIID);
            }
        });
    }
    private void updateSessionID(String email){
        UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
        Call<List<UserModel>> call = userApiInterface.getUser_email("admin123",email);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<com.unpas.chatak.model.UserModel>> call, Response<List<UserModel>> response) {
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
}
