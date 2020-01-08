package com.unpas.chatak.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.unpas.chatak.model.database.order_history.OrderHistoryDao;
import com.unpas.chatak.model.database.order_history.OrderHistoryPojo;
import com.unpas.chatak.model.database.promo.NotificationPojo;
import com.unpas.chatak.model.database.promo.PromoDao;


@Database(entities={NotificationPojo.class, OrderHistoryPojo.class},version = 2,  exportSchema = true)

public abstract class LocalDB extends RoomDatabase {
    public abstract PromoDao promoDao();
    public abstract OrderHistoryDao historyOrderDao();
}
