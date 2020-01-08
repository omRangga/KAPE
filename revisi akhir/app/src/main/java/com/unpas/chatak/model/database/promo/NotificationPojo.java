package com.unpas.chatak.model.database.promo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "promo")
public class NotificationPojo {

    public static final String COLUMN_ID = "id";

    public NotificationPojo(String promo){
        this.promo = promo;
    }

    @ColumnInfo(name = "id")@PrimaryKey(autoGenerate = true) @NonNull
    private int id;

    @ColumnInfo(name = "promo")@NonNull
    private String promo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getPromo() {
        return promo;
    }

    public void setPromo(@NonNull String promo) {
        this.promo = promo;
    }
}
