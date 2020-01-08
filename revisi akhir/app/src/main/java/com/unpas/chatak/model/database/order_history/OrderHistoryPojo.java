package com.unpas.chatak.model.database.order_history;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "order_history")
public class OrderHistoryPojo {

    public OrderHistoryPojo(String title){
        this.title = title;
    }

    public OrderHistoryPojo(){}

    @ColumnInfo(name = "id")@PrimaryKey(autoGenerate = true) @NonNull
    private int id;

    @ColumnInfo(name = "title")@NonNull
    private String title;

    @ColumnInfo(name = "photo")@Nullable
    private String photo;

    @NonNull
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
    public void setTitle(@NonNull String title) {
        this.title = title;
    }


    @Nullable
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(@Nullable String photo) {
        this.photo = photo;
    }
}
