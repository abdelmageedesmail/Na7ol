package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderTypeResponse {


    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("return")
    private List<ReturnsEntity> returns;
    @Expose
    @SerializedName("sub_message")
    private String sub_message;
    @Expose
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReturnsEntity> getReturns() {
        return returns;
    }

    public void setReturns(List<ReturnsEntity> returns) {
        this.returns = returns;
    }

    public String getSub_message() {
        return sub_message;
    }

    public void setSub_message(String sub_message) {
        this.sub_message = sub_message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ReturnsEntity {
        @Expose
        @SerializedName("type_id")
        private String type_id;
        @Expose
        @SerializedName("type_title")
        private String type_title;

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_title() {
            return type_title;
        }

        public void setType_title(String type_title) {
            this.type_title = type_title;
        }
    }
}
