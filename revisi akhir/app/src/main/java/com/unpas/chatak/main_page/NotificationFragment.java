package com.unpas.chatak.main_page;


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

import com.unpas.chatak.R;
import com.unpas.chatak.adapter.NotificationAdapter;
import com.unpas.chatak.model.database.promo.NotificationPojo;

import java.util.ArrayList;
import java.util.List;

import static com.unpas.chatak.main_page.MainActivity.db;
import static com.unpas.chatak.main_page.MainActivity.navView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private RecyclerView rv;
    private NotificationAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();//mengambil referensi ke ActionBar dengan memodifikasi atau mengakses properti ActionBar saat program berjalan
        actionBar.show();//menampilkan action bar
        actionBar.setDisplayUseLogoEnabled(false);//Menyembunyikan logo toolbar
        actionBar.setDisplayShowTitleEnabled(true);//menampilkan text toolbar
        actionBar.setTitle(R.string.notification_title);//set Text toolbar
        //show data to adapter
        layoutManager = new LinearLayoutManager(getActivity());
        rv = view.findViewById(R.id.rv_notification);
        rv.setLayoutManager(layoutManager);
        db.promoDao().getData().observe(this, new Observer<List<NotificationPojo>>() {
            @Override
            public void onChanged(@Nullable List<NotificationPojo> notificationPojos) {
                adapter = new NotificationAdapter(getContext(), notificationPojos);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        //onBackPressed
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
        });


        return view;
    }
}
