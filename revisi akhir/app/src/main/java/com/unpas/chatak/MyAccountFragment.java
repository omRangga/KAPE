package com.unpas.chatak;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.unpas.chatak.adapter.RiwayatPesananAdapter;
import com.unpas.chatak.main_page.MainActivity;
import com.unpas.chatak.model.database.order_history.OrderHistoryPojo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.unpas.chatak.main_page.MainActivity.db;
import static com.unpas.chatak.main_page.MainActivity.navView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

    private RecyclerView rvHistory;
    private RiwayatPesananAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button sign_out_btn;
    private FirebaseUser fuser;
    private FirebaseAuth mAuth;
    TextView full_name,email,phone;
    CircleImageView photo_profile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();//mengambil referensi ke ActionBar dengan memodifikasi atau mengakses properti ActionBar saat program berjalan
        actionBar.show();//menampilkan action bar
        actionBar.setDisplayUseLogoEnabled(false);//Menyembunyikan logo toolbar
        actionBar.setDisplayShowTitleEnabled(true);//menampilkan text toolbar
        actionBar.setTitle("Profile");//set Text toolbar

        full_name = view.findViewById(R.id.username);
        email = view.findViewById(R.id.user_email);
        phone = view.findViewById(R.id.user_phone);
        photo_profile = view.findViewById(R.id.profile_image);
        fuser =FirebaseAuth.getInstance().getCurrentUser();
        if (fuser !=null){
            full_name.setText(fuser.getDisplayName());
            email.setText(fuser.getEmail());
            phone.setText(fuser.getPhoneNumber());
            Picasso.with(getApplicationContext()).load(fuser.getPhotoUrl()).into(photo_profile);
        }

        //set history order
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvHistory = view.findViewById(R.id.order_history);
        rvHistory.setLayoutManager(layoutManager);
        db.historyOrderDao().getHistory().observe(this, new Observer<List<OrderHistoryPojo>>() {
            @Override
            public void onChanged(@Nullable List<OrderHistoryPojo> orderHistoryPojos) {
                adapter = new RiwayatPesananAdapter(orderHistoryPojos);
                rvHistory.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        //onBackpressed
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    navView.setSelectedItemId(R.id.navigation_home);
                    return true;
                }
                return false;
            }
        } );

        sign_out_btn=view.findViewById(R.id.sign_out_btn);
        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ((MainActivity)getActivity()).doLogOut();
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        full_name.setText(fuser.getDisplayName());
        email.setText(fuser.getEmail());
        phone.setText(fuser.getPhoneNumber());
        Picasso.with(getApplicationContext()).load(fuser.getPhotoUrl()).into(photo_profile);
    }
}
