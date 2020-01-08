package com.unpas.chatak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PutModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("0")
    @Expose
    private String code;

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
