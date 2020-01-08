package com.unpas.chatak.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unpas.chatak.model.WishlistModel;
import com.unpas.chatak.R;
import com.unpas.chatak.rest.ApiClient;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private Context context;
    List<WishlistModel> wishlistModelList;

    public WishlistAdapter(Context context, List<WishlistModel> wishlistModelList) {
        this.context = context;
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int position) {
        String resource= wishlistModelList.get(position).getWishlistImage();
        String title= wishlistModelList.get(position).getWishlistTitle();
        String description= wishlistModelList.get(position).getWishlistDescription();

        Picasso.with(viewHolder.itemView.getContext())
                .load(ApiClient.IMG_URL+resource)
                .resize(75, 75).into(viewHolder.whislistImage);
        viewHolder.setWhislistTitle(title);
        viewHolder.setWhislistDescription(description);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView whislistImage;
        private TextView whislistTitle;
        private TextView whislistDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            whislistImage=itemView.findViewById(R.id.wishlist_image);
            whislistTitle=itemView.findViewById(R.id.wishlist_title);
            whislistDescription=itemView.findViewById(R.id.wishlist_description);

        }


        private void setWhislistTitle(String title){

            whislistTitle.setText(title);
        }

        private void setWhislistDescription(String description){

            whislistDescription.setText(description);
        }
    }
}
