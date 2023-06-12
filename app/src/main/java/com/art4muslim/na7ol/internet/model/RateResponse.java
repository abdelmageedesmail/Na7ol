package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateResponse {

    @Expose
    @SerializedName("total_items")
    private int total_items;
    @Expose
    @SerializedName("total_pages")
    private int total_pages;
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

    public int getTotal_items() {
        return total_items;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

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
        @SerializedName("created_since")
        private String created_since;
        @Expose
        @SerializedName("user_name")
        private String user_name;
        @Expose
        @SerializedName("rate_created_at")
        private String rate_created_at;
        @Expose
        @SerializedName("rate_agree_Id")
        private String rate_agree_Id;
        @Expose
        @SerializedName("rate_review")
        private String rate_review;
        @Expose
        @SerializedName("rate_number")
        private String rate_number;
        @Expose
        @SerializedName("rate_advertiser_id")
        private String rate_advertiser_id;
        @Expose
        @SerializedName("rate_user_id")
        private String rate_user_id;
        @Expose
        @SerializedName("rate_id")
        private String rate_id;

        public String getCreated_since() {
            return created_since;
        }

        public void setCreated_since(String created_since) {
            this.created_since = created_since;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getRate_created_at() {
            return rate_created_at;
        }

        public void setRate_created_at(String rate_created_at) {
            this.rate_created_at = rate_created_at;
        }

        public String getRate_agree_Id() {
            return rate_agree_Id;
        }

        public void setRate_agree_Id(String rate_agree_Id) {
            this.rate_agree_Id = rate_agree_Id;
        }

        public String getRate_review() {
            return rate_review;
        }

        public void setRate_review(String rate_review) {
            this.rate_review = rate_review;
        }

        public String getRate_number() {
            return rate_number;
        }

        public void setRate_number(String rate_number) {
            this.rate_number = rate_number;
        }

        public String getRate_advertiser_id() {
            return rate_advertiser_id;
        }

        public void setRate_advertiser_id(String rate_advertiser_id) {
            this.rate_advertiser_id = rate_advertiser_id;
        }

        public String getRate_user_id() {
            return rate_user_id;
        }

        public void setRate_user_id(String rate_user_id) {
            this.rate_user_id = rate_user_id;
        }

        public String getRate_id() {
            return rate_id;
        }

        public void setRate_id(String rate_id) {
            this.rate_id = rate_id;
        }
    }
}
