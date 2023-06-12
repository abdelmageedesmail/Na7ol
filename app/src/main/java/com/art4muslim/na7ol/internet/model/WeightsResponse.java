package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeightsResponse {

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
        @SerializedName("price_id")
        private String price_id;
        @Expose
        @SerializedName("price_price")
        private String price_price;
        @Expose
        @SerializedName("price_max_weight")
        private String price_max_weight;
        @Expose
        @SerializedName("price_min_weight")
        private String price_min_weight;

        public String getPrice_id() {
            return price_id;
        }

        public void setPrice_id(String price_id) {
            this.price_id = price_id;
        }

        public String getPrice_price() {
            return price_price;
        }

        public void setPrice_price(String price_price) {
            this.price_price = price_price;
        }

        public String getPrice_max_weight() {
            return price_max_weight;
        }

        public void setPrice_max_weight(String price_max_weight) {
            this.price_max_weight = price_max_weight;
        }

        public String getPrice_min_weight() {
            return price_min_weight;
        }

        public void setPrice_min_weight(String price_min_weight) {
            this.price_min_weight = price_min_weight;
        }
    }
}
