package com.unpas.chatak;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unpas.chatak.R;
import com.unpas.chatak.adapter.CartAdapter;
import com.unpas.chatak.main_page.MainActivity;
import com.unpas.chatak.main_page.PembayaranActivity;
import com.unpas.chatak.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

import static com.unpas.chatak.main_page.MainActivity.navView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {


    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemsRecyclerView;
    Button checkout;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();//mengambil referensi ke ActionBar dengan memodifikasi atau mengakses properti ActionBar saat program berjalan
        actionBar.show();//menampilkan action bar
        actionBar.setDisplayUseLogoEnabled(false);//Menyembunyikan logo toolbar
        actionBar.setDisplayShowTitleEnabled(true);//menampilkan text toolbar
        actionBar.setTitle(getResources().getString(R.string.title_keranjang));//set Text toolbar


        cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(1, "Rp 1000", "Rp 1000", "Rp 0", "-", "Rp 1000"));
        cartItemModelList.add(new CartItemModel(0, R.drawable.favicon, "Laporan", "Rp 200", "Rp 500"));
        cartItemModelList.add(new CartItemModel(0, R.drawable.favicon, "Laporan", "Rp 300", "Rp 500"));
        cartItemModelList.add(new CartItemModel(0, R.drawable.favicon, "Laporan", "Rp 500", "Rp 1000"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        //onBackPressed
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    navView.setSelectedItemId(R.id.navigation_home);
                    return true;
                }
                return false;
            }
        });

        checkout = view.findViewById(R.id.cart_continue_btn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pembayaran = new Intent(getActivity(), PembayaranActivity.class);
                startActivity(pembayaran);
            }
        });
        return view;

    }

}
