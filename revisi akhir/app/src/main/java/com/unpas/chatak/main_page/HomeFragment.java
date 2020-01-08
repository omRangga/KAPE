package com.unpas.chatak.main_page;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unpas.chatak.adapter.ApiAdapter;
import com.unpas.chatak.R;
import com.unpas.chatak.adapter.SliderAdapter;
import com.unpas.chatak.model.ApiModel;
import com.unpas.chatak.model.SliderModel;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.rest.BarangInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //listBarang
    public HomeFragment() {
        // Required empty public constructor
    }

    /////banner Slider
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAYTIME = 3000;
    final private long PERIOD_TIME = 3000;
    ////banner slider

    ////Horizontal Product Layout
    private Fragment fragment;
    private TextView horizontallayoutTitle;
    private RecyclerView horizontalRecyclerView, horizontalRecyclerView2, horizontalRecyclerView3;
    private ApiAdapter adapter1, adapter2, adapter3;
    private Button btnDoc, btnMedpro, btnSou;
    private MainActivity ma;
    private FragmentManager fragmentManager;
    private SwipeRefreshLayout refreshLayout;
    ////Horizontal Product Layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();//mengambil referensi ke ActionBar dengan memodifikasi atau mengakses properti ActionBar saat program berjalan
        actionBar.show();//menampilkan action bar
        actionBar.setDisplayUseLogoEnabled(true);//Menyembunyikan logo toolbar
        actionBar.setLogo(getResources().getDrawable(R.drawable.chatak));
        actionBar.setDisplayShowTitleEnabled(false);//menampilkan text toolbar

        ////banner slider
        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);

        sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.drawable.favicon, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.favicon, "#00000f"));
        sliderModelList.add(new SliderModel(R.drawable.favicon, "#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.favicon, "#DF2986"));

        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(35);


        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == ViewPager.SCROLL_STATE_IDLE) {
                    pageLooper();
                }
            }
        };

        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startBannerSlideShow();
        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSliderSHow();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startBannerSlideShow();
                }
                return false;
            }
        });
        ////banner slider

   //menambahkan view model
        ApiClient model = ViewModelProviders.of(this).get(ApiClient.class);
        model.getDokumenCache().observe(this, new Observer<List<ApiModel>>() {
            @Override
            public void onChanged(@Nullable List<ApiModel> daftarBarang) {
                dokumenList(daftarBarang);
                mediaPromosiList(daftarBarang);
                souvenirList(daftarBarang);
            }
        });
        //view model
        loadDataApi();

        //Swipe Refresh
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiClient apiClient = ViewModelProviders.of(HomeFragment.this).get(ApiClient.class);
                apiClient.getDokumenCache().observe(HomeFragment.this, new Observer<List<ApiModel>>() {
                    @Override
                    public void onChanged(@Nullable List<ApiModel> apiModels) {
                        dokumenList(apiModels);
                        mediaPromosiList(apiModels);
                        souvenirList(apiModels);
                    }
                });
                loadDataApi();
                refreshLayout.setRefreshing(false);
            }
        });


        fragmentManager = getFragmentManager();

        ///Horizontal Dokumen layout
        horizontallayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recycler);
        btnDoc = view.findViewById(R.id.horizontal_scroll_view_all_button_dokumen);
//        loadDataApiDokumen();
        ///Horizontal dokumen Layout

        ///Horizontal media promosi Layout
        horizontalRecyclerView2 = view.findViewById(R.id.horizontal_scroll_layout_recycler2);
        btnMedpro = view.findViewById(R.id.horizontal_scroll_view_all_button_media_promosi);
//        loadDataApiMediaPromosi();
        ///Horizontal media promosi layout

        ///Horizontal Souvenir Layout
        horizontalRecyclerView3 = view.findViewById(R.id.horizontal_scroll_layout_recycler3);
        btnSou = view.findViewById(R.id.horizontal_scroll_view_all_button_souvenir);
//        loadDataApiSouvenir();
        //Horizontal Souvenir Layout

        return view;
    }

    ////banner slider
    private void pageLooper() {
        if (currentPage == sliderModelList.size() - 2) {
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }
        if (currentPage == 1) {
            currentPage = sliderModelList.size() - 3;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }

    }

    private void startBannerSlideShow() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()) {
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAYTIME, PERIOD_TIME);
    }

    private void stopBannerSliderSHow() {
        timer.cancel();

    }
    ////banner slider


    // API List Media Promosi
    private void dokumenList(List<ApiModel> listDokumen) {
                adapter1 = new ApiAdapter(getActivity(), listDokumen.subList(0,6));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                horizontalRecyclerView.setLayoutManager(layoutManager);
                horizontalRecyclerView.setAdapter(adapter1);
            }

    // API List Media Promosi
    private void mediaPromosiList(List<ApiModel> listMediaPromosi) {
        adapter2 = new ApiAdapter(getActivity(), listMediaPromosi.subList(15,21));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView2.setLayoutManager(layoutManager);
        horizontalRecyclerView2.setAdapter(adapter2);
    }

    // API List Souvenir
    private void souvenirList(List<ApiModel> listSouvenir) {
        adapter3 = new ApiAdapter(getActivity(), listSouvenir.subList(39,45));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView3.setLayoutManager(layoutManager);
        horizontalRecyclerView3.setAdapter(adapter3);
    }

    public static void loadDataApi() {
        BarangInterface barangInterface = ApiClient.getClient().create(BarangInterface.class);
        Call<List<ApiModel>> callMediaPromosi = barangInterface.getBarang();
        callMediaPromosi.enqueue(new Callback<List<ApiModel>>() {
            @Override
            public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

                ApiClient.DOKUMEN.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<ApiModel>> call, Throwable t) {

            }
        });
    }
}
