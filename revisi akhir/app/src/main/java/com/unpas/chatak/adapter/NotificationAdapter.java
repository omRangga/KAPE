package com.unpas.chatak.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unpas.chatak.R;
import com.unpas.chatak.main_page.MainActivity;
import com.unpas.chatak.main_page.NotificationFragment;
import com.unpas.chatak.model.database.promo.NotificationPojo;

import java.util.List;

import static com.unpas.chatak.main_page.MainActivity.db;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    int id;
    private List<NotificationPojo> notificationList;
    private Context con;

    public NotificationAdapter(Context con, List<NotificationPojo> notificationList) {
        this.notificationList = notificationList;
        this.con = con;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
        final NotificationPojo itemNotif = notificationList.get(position);
        id = itemNotif.getId();
        holder.descNotif.setText(itemNotif.getPromo());
        holder.notifDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.promoDao().deleteById(id);
                Toast.makeText(con.getApplicationContext(), "Notifikasi dihapus", Toast.LENGTH_SHORT).show();
                notifyItemRemoved(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView descNotif;
        ImageButton notifDelete;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            descNotif = itemView.findViewById(R.id.notification_title);
            notifDelete = itemView.findViewById(R.id.notification_delete_btn);

        }
    }
}
