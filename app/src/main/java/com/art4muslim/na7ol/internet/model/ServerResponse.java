package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    @SerializedName("status")
    private
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
