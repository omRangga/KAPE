package com.unpas.chatak.main_page;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unpas.chatak.adapter.ApiAdapter;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.model.ApiModel;
import com.unpas.chatak.R;
import com.unpas.chatak.rest.BarangInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unpas.chatak.main_page.MainActivity.navView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DokumenFragment extends Fragment{
    RecyclerView mRecyclerView;
    ApiAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dokumen, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();//mengambil referensi ke ActionBar dengan memodifikasi atau mengakses properti ActionBar saat program berjalan
        actionBar.show();//menampilkan action bar
        actionBar.setDisplayUseLogoEnabled(false);//Menyembunyikan logo toolbar
        actionBar.setDisplayShowTitleEnabled(true);//menampilkan text toolbar
        actionBar.setTitle(getResources().getString(R.string.title_dokumen));//set Text toolbar

        mRecyclerView= view.findViewById(R.id.recyclerViewDokumen);
        //menambahkan view model
        ApiClient model = ViewModelProviders.of(this).get(ApiClient.class);
        model.getDokumenCache().observe(this, new Observer<List<ApiModel>>() {
            @Override
            public void onChanged(@Nullable List<ApiModel> daftarBarang) {
                dokumenList(daftarBarang);
            }
        });
        //view model
        HomeFragment.loadDataApi();

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
        } );

        return view;
    }

    private void dokumenList(List<ApiModel> listDokumen) {
        adapter = new ApiAdapter(getActivity(), listDokumen.subList(0,14));
        layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }
}

