package com.unpas.chatak.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.unpas.chatak.R;
import com.unpas.chatak.model.ApiModel;
import com.unpas.chatak.rest.ApiClient;
import com.unpas.chatak.transaksi_page.MasukPesanan;

import java.util.List;


public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.MyViewHolder>{

    private Context context;
    List<ApiModel> apiModelList;


    public ApiAdapter(Context context, List<ApiModel> apiModelList) {
        this.context = context;
        this.apiModelList = apiModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pesanan =  new Intent(view.getContext(), MasukPesanan.class);
                pesanan.putExtra(MasukPesanan.PESANAN_MASUK, apiModelList.get(mViewHolder.getAdapterPosition()).getTitle());
                context.startActivity(pesanan);
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (final MyViewHolder holder, final int position){
        holder.mTextViewTitle.setText(apiModelList.get(position).getTitle());
        holder.mTextViewDesc.setText(apiModelList.get(position).getDescription());
        Picasso.with(holder.itemView.getContext())
                .load(ApiClient.IMG_URL+apiModelList.get(position).getImage())
                .resize(75, 75).into(holder.imageView);
        holder.mTextViewTitle.setTag(apiModelList.get(position));
        holder.mTextViewDesc.setTag(apiModelList.get(position));
        holder.imageView.setTag(apiModelList.get(position).getImage());
        holder.btnWish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                boolean stats = false;
                if (stats){
                    holder.btnWish.getDrawable().setTint(context.getResources().getColor(R.color.abu_chatak));
                    Toast.makeText(context, "Dihapus dari Wishlist",Toast.LENGTH_SHORT).show();
                }else{
                    holder.btnWish.getDrawable().setTint(context.getResources().getColor(R.color.merah_chatak));
                    Toast.makeText(context, "Ditambahkan ke Wishlist",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount () {
        return apiModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTextViewTitle, mTextViewDesc;
        ImageView imageView, btnWish;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.h_s_product_image);
            mTextViewTitle = itemView.findViewById(R.id.h_s_product_title);
            mTextViewDesc = itemView.findViewById(R.id.h_s_product_description);
            btnWish = itemView.findViewById(R.id.whislist_icon);
        }
    }

}


