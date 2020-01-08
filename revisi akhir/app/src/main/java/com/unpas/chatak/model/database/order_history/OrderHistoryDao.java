package com.unpas.chatak.model.database.order_history;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unpas.chatak.model.database.order_history.OrderHistoryPojo;

import java.util.List;

@Dao
public interface OrderHistoryDao {
    //set provider DAO
    @Query("SELECT * FROM order_history ORDER BY ID")
    LiveData<List<OrderHistoryPojo>> getHistory();

    @Insert
    long insertHistory(OrderHistoryPojo promo);

}
