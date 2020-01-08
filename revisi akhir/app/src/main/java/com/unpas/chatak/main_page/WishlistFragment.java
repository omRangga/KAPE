package com.unpas.chatak.main_page;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.unpas.chatak.R;
import com.unpas.chatak.adapter.WishlistAdapter;
import com.unpas.chatak.model.WishlistModel;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.rest.WishlistApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unpas.chatak.main_page.MainActivity.navView;

public class WishlistFragment extends Fragment {

    private RecyclerView wishlistItemRecyclerView;
    WishlistAdapter apiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.wishlist_fragment,container,false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();//mengambil referensi ke ActionBar dengan memodifikasi atau mengakses properti ActionBar saat program berjalan
        actionBar.show();//Menampilkan toolbar
        actionBar.setDisplayUseLogoEnabled(false);//Menyembunyikan logo toolbar
        actionBar.setDisplayShowTitleEnabled(true);//menampilkan text toolbar
        actionBar.setTitle(getResources().getString(R.string.wishlist));//set Text title toolbar

        wishlistItemRecyclerView =view.findViewById(R.id.recyler_wishlist);


        //menambahkan view model
        WishlistApiInterface wishlistApiInterface = ApiClient.getClient().create(WishlistApiInterface.class);
        Call<List<WishlistModel>> call = wishlistApiInterface.getWishlist();
        call.enqueue(new Callback<List<WishlistModel>>() {
            @Override
            public void onResponse(Call<List<WishlistModel>> call, Response<List<WishlistModel>> response) {
                wishlistApi(response.body());
            }

            @Override
            public void onFailure(Call<List<WishlistModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
                Log.d("isisi", t.toString());
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


       public void wishlistApi(List<WishlistModel> listWishlist){
        apiAdapter = new WishlistAdapter(getContext(), listWishlist);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistItemRecyclerView.setLayoutManager(layoutManager);
        wishlistItemRecyclerView.setAdapter(apiAdapter);

    }
}
