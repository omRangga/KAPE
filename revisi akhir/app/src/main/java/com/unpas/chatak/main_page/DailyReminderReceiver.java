package com.unpas.chatak.main_page;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.unpas.chatak.R;
import com.unpas.chatak.model.database.promo.NotificationPojo;

import static com.unpas.chatak.main_page.MainActivity.db;


public class DailyReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notifIntent = new Intent(context, MainActivity.class);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, SettingsActivity.DAILY_CODE,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,SettingsActivity.ID_NOTIF)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.favicon)
                        .setContentTitle(context.getResources().getString(R.string.daily_reminder))
                        .setContentText(context.getResources().getString(R.string.desc_daily_reminder))
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setAutoCancel(true);
        NotificationPojo data = new NotificationPojo(context.getResources().getString(R.string.desc_daily_reminder));
        db.promoDao().insertData(data);
        //handle API lvl > 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(SettingsActivity.ID_NOTIF, SettingsActivity.NOTIF_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            notificationBuilder.setChannelId(SettingsActivity.ID_NOTIF);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        mNotificationManager.notify(SettingsActivity.DAILY_CODE, notificationBuilder.build());

    }
}
