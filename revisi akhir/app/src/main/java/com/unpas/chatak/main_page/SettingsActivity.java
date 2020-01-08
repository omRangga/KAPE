package com.unpas.chatak.main_page;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.unpas.chatak.R;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbarSettings;
    Switch swDaily, swPromo;
    private SharedPreferences sharedPreferences;
    private static final String STATS = "status";
    public static final int DAILY_CODE = 100;
    public static final String TAG = "SettingsActivity";
    public static final String ID_NOTIF = "notification";
    public static final String NOTIF_CHANNEL = "notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbarSettings = findViewById(R.id.toolbar_settings);
        swDaily = findViewById(R.id.switch_daily_notif);
        swPromo = findViewById(R.id.switch_fcm_notif);
        setSupportActionBar(toolbarSettings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.settings));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        }
        showNotifDaily();
        showNotifPromo();
    }

    private void showNotifPromo() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        swPromo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swPromo.isChecked() == true) {
                    Toast.makeText(SettingsActivity.this, getString(R.string.notif_promo_enabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "IS Checked");
                    FirebaseMessaging.getInstance().subscribeToTopic("promo");
                    sharedPreferences.edit().putBoolean("checkedPromo", swPromo.isChecked()).apply();
                } else if (swPromo.isChecked() == false) {
                    Toast.makeText(SettingsActivity.this, getString(R.string.notif_promo_disabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "NOT Checked");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("promo");
                    sharedPreferences.edit().putBoolean("checkedPromo", swPromo.isChecked()).apply();
                }
            }
        });
        swPromo.setChecked(false);
        FirebaseMessaging.getInstance().unsubscribeFromTopic("promo");
        swPromo.setChecked(sharedPreferences.getBoolean("checkedPromo", false));
    }

    private void showNotifDaily() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swDaily.isChecked() == true) {
                    Toast.makeText(SettingsActivity.this, getResources().getString(R.string.daily_stats_enabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "IS Checked");
                    playReminderDaily();
                    sharedPreferences.edit().putBoolean("checked", swDaily.isChecked()).apply();
                } else if (swDaily.isChecked() == false) {
                    Toast.makeText(SettingsActivity.this, getResources().getString(R.string.daily_stats_disabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "NOT Checked");
                    stopReminder();
                    sharedPreferences.edit().putBoolean("checked", swDaily.isChecked()).apply();
                }
            }
        });
        swDaily.setChecked(false);
        stopReminder();
        swDaily.setChecked(sharedPreferences.getBoolean("checked", false));
    }

    private void playReminderDaily() {
        Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Intent intent = new Intent(this, DailyReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, DAILY_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    private void stopReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(this, DailyReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, DAILY_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent.cancel();
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
