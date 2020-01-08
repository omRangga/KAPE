package com.unpas.chatak.model.database.promo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PromoDao {

    //set provider DAO
    @Query("SELECT * FROM promo ORDER BY ID")
    LiveData<List<NotificationPojo>> getData();

    @Insert
    long insertData(NotificationPojo promo);

    @Query("DELETE FROM promo WHERE "+ NotificationPojo.COLUMN_ID +" = :id")
    int deleteById(long id);
}
