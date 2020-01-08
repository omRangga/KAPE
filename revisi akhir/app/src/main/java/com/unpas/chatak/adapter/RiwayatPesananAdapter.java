package com.unpas.chatak.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unpas.chatak.R;
import com.unpas.chatak.model.database.order_history.OrderHistoryPojo;

import java.util.List;

public class RiwayatPesananAdapter extends RecyclerView.Adapter<RiwayatPesananAdapter.RiwayatViewHolder> {

    private List<OrderHistoryPojo> historyOrderList;

    public RiwayatPesananAdapter(List<OrderHistoryPojo> historyOrderList) {
        this.historyOrderList = historyOrderList;
    }


    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_history, viewGroup, false);

        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RiwayatViewHolder holder, int position) {
        OrderHistoryPojo historyList = historyOrderList.get(position);
        holder.historyTitle.setText(historyList.getTitle());
    }

    @Override
    public int getItemCount() {
        return historyOrderList.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        ImageView historyImage;
        TextView historyTitle;
        public RiwayatViewHolder(View itemView) {
            super(itemView);
            historyImage = itemView.findViewById(R.id.order_history_image);
            historyTitle = itemView.findViewById(R.id.order_history_title);
        }
    }
}
