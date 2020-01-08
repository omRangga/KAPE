package com.unpas.chatak.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unpas.chatak.model.CartItemModel;
import com.unpas.chatak.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
       switch (cartItemModelList.get(position).getType()){
           case 0:
               return CartItemModel.CART_ITEM;
           case 1:
               return CartItemModel.TOTAL_AMOUNT_;
               default:
                   return -1;
       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType){
            case CartItemModel.CART_ITEM:
                View cartItemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout,viewGroup,false);
                return new CartItemViewholder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT_:
                View cartTotalView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_order_layout,viewGroup,false);
                return new CartTotalAmountholder(cartTotalView);
                default:
                    return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int potition) {
        switch (cartItemModelList.get(potition).getType()){
            case CartItemModel.CART_ITEM:
                int resource=cartItemModelList.get(potition).getProductImage();
                String title=cartItemModelList.get(potition).getProductTitle();
                String productPrice=cartItemModelList.get(potition).getProductPrice();
                String cuttedPrice=cartItemModelList.get(potition).getCuttedPrice();

                ((CartItemViewholder)viewHolder).setItemDetails(resource,title,productPrice,cuttedPrice);
                break;
            case CartItemModel.TOTAL_AMOUNT_:
                String totalHargaItem=cartItemModelList.get(potition).getTotalHargaItem();
                String diskon=cartItemModelList.get(potition).getDiskon();
                String ongkosKirim=cartItemModelList.get(potition).getOngkosKirim();
                String pajak=cartItemModelList.get(potition).getTotalPajak();
                String jumlahTotal=cartItemModelList.get(potition).getJumlahTotal();


                ((CartTotalAmountholder)viewHolder).setTotalAmount(totalHargaItem,diskon,ongkosKirim,pajak,jumlahTotal);
                break;
                default:
                    return;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewholder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;


        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            productPrice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.cutted_price);
        }

        private void setItemDetails(int resource,String title,String productPriceText,String cuttedPriceText){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
        }
    }

    class CartTotalAmountholder extends RecyclerView.ViewHolder{

        private TextView totalHargaItem;
        private TextView diskon;
        private TextView ongkosKirim;
        private TextView totalPajak;
        private TextView jumlahTotal;


        public CartTotalAmountholder(@NonNull View itemView) {
            super(itemView);

            totalHargaItem=itemView.findViewById(R.id.jumlah_total);
            diskon=itemView.findViewById(R.id.diskon);
            ongkosKirim=itemView.findViewById(R.id.ongkos_kirim);
            totalPajak=itemView.findViewById(R.id.total_pajak);
            jumlahTotal=itemView.findViewById(R.id.total_price);
        }

        private void setTotalAmount(String totalHargaItemText,String diskonText,String ongkosKirimText,String totalPajakTextText,String jumlahTotalText){
            totalHargaItem.setText(totalHargaItemText);
            diskon.setText(diskonText);
            ongkosKirim.setText(ongkosKirimText);
            totalPajak.setText(totalPajakTextText);
            jumlahTotal.setText(jumlahTotalText);
        }
    }
}
