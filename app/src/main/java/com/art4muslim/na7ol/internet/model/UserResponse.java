package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("return")
    private ReturnsEntity returns;
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

    public ReturnsEntity getReturns() {
        return returns;
    }

    public void setReturns(ReturnsEntity returns) {
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
        @SerializedName("imageUrl")
        private String imageUrl;
        @Expose
        @SerializedName("avg_rating")
        private String avg_rating;
        @Expose
        @SerializedName("rates_count")
        private String rates_count;
        @Expose
        @SerializedName("adv_thumb_id")
        private int adv_thumb_id;
        @Expose
        @SerializedName("adv_national_thumb_id")
        private String adv_national_thumb_id;
        @Expose
        @SerializedName("adv_national_id")
        private String adv_national_id;
        @Expose
        @SerializedName("adv_app_commision")
        private String adv_app_commision;
        @Expose
        @SerializedName("adv_activate_code")
        private String adv_activate_code;
        @Expose
        @SerializedName("adv_city_id")
        private String adv_city_id;
        @Expose
        @SerializedName("adv_country_code")
        private String adv_country_code;
        @Expose
        @SerializedName("adv_not_weight")
        private String adv_not_weight;
        @Expose
        @SerializedName("adv_not_order_type")
        private String adv_not_order_type;
        @Expose
        @SerializedName("adv_not_city_to")
        private String adv_not_city_to;
        @Expose
        @SerializedName("adv_not_city_from")
        private String adv_not_city_from;
        @Expose
        @SerializedName("adv_not_car_type")
        private String adv_not_car_type;
        @Expose
        @SerializedName("adv_not_orders")
        private String adv_not_orders;
        @Expose
        @SerializedName("adv_not_trips")
        private String adv_not_trips;
        @Expose
        @SerializedName("adv_country_id")
        private String adv_country_id;
        @Expose
        @SerializedName("adv_driver_status")
        private String adv_driver_status;
        @Expose
        @SerializedName("adv_status")
        private String adv_status;
        @Expose
        @SerializedName("adv_mobile")
        private String adv_mobile;
        @Expose
        @SerializedName("adv_email")
        private String adv_email;
        @Expose
        @SerializedName("adv_name")
        private String adv_name;
        @Expose
        @SerializedName("adv_balance")
        private String adv_balance;
        @Expose
        @SerializedName("adv_user_type")
        private String adv_user_type;
        @Expose
        @SerializedName("adv_id")
        private String adv_id;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getAvg_rating() {
            return avg_rating;
        }

        public void setAvg_rating(String avg_rating) {
            this.avg_rating = avg_rating;
        }

        public String getRates_count() {
            return rates_count;
        }

        public void setRates_count(String rates_count) {
            this.rates_count = rates_count;
        }

        public int getAdv_thumb_id() {
            return adv_thumb_id;
        }

        public void setAdv_thumb_id(int adv_thumb_id) {
            this.adv_thumb_id = adv_thumb_id;
        }

        public String getAdv_national_thumb_id() {
            return adv_national_thumb_id;
        }

        public void setAdv_national_thumb_id(String adv_national_thumb_id) {
            this.adv_national_thumb_id = adv_national_thumb_id;
        }

        public String getAdv_national_id() {
            return adv_national_id;
        }

        public void setAdv_national_id(String adv_national_id) {
            this.adv_national_id = adv_national_id;
        }

        public String getAdv_app_commision() {
            return adv_app_commision;
        }

        public void setAdv_app_commision(String adv_app_commision) {
            this.adv_app_commision = adv_app_commision;
        }

        public String getAdv_activate_code() {
            return adv_activate_code;
        }

        public void setAdv_activate_code(String adv_activate_code) {
            this.adv_activate_code = adv_activate_code;
        }

        public String getAdv_city_id() {
            return adv_city_id;
        }

        public void setAdv_city_id(String adv_city_id) {
            this.adv_city_id = adv_city_id;
        }

        public String getAdv_country_code() {
            return adv_country_code;
        }

        public void setAdv_country_code(String adv_country_code) {
            this.adv_country_code = adv_country_code;
        }

        public String getAdv_not_weight() {
            return adv_not_weight;
        }

        public void setAdv_not_weight(String adv_not_weight) {
            this.adv_not_weight = adv_not_weight;
        }

        public String getAdv_not_order_type() {
            return adv_not_order_type;
        }

        public void setAdv_not_order_type(String adv_not_order_type) {
            this.adv_not_order_type = adv_not_order_type;
        }

        public String getAdv_not_city_to() {
            return adv_not_city_to;
        }

        public void setAdv_not_city_to(String adv_not_city_to) {
            this.adv_not_city_to = adv_not_city_to;
        }

        public String getAdv_not_city_from() {
            return adv_not_city_from;
        }

        public void setAdv_not_city_from(String adv_not_city_from) {
            this.adv_not_city_from = adv_not_city_from;
        }

        public String getAdv_not_car_type() {
            return adv_not_car_type;
        }

        public void setAdv_not_car_type(String adv_not_car_type) {
            this.adv_not_car_type = adv_not_car_type;
        }

        public String getAdv_not_orders() {
            return adv_not_orders;
        }

        public void setAdv_not_orders(String adv_not_orders) {
            this.adv_not_orders = adv_not_orders;
        }

        public String getAdv_not_trips() {
            return adv_not_trips;
        }

        public void setAdv_not_trips(String adv_not_trips) {
            this.adv_not_trips = adv_not_trips;
        }

        public String getAdv_country_id() {
            return adv_country_id;
        }

        public void setAdv_country_id(String adv_country_id) {
            this.adv_country_id = adv_country_id;
        }

        public String getAdv_driver_status() {
            return adv_driver_status;
        }

        public void setAdv_driver_status(String adv_driver_status) {
            this.adv_driver_status = adv_driver_status;
        }

        public String getAdv_status() {
            return adv_status;
        }

        public void setAdv_status(String adv_status) {
            this.adv_status = adv_status;
        }

        public String getAdv_mobile() {
            return adv_mobile;
        }

        public void setAdv_mobile(String adv_mobile) {
            this.adv_mobile = adv_mobile;
        }

        public String getAdv_email() {
            return adv_email;
        }

        public void setAdv_email(String adv_email) {
            this.adv_email = adv_email;
        }

        public String getAdv_name() {
            return adv_name;
        }

        public void setAdv_name(String adv_name) {
            this.adv_name = adv_name;
        }

        public String getAdv_balance() {
            return adv_balance;
        }

        public void setAdv_balance(String adv_balance) {
            this.adv_balance = adv_balance;
        }

        public String getAdv_user_type() {
            return adv_user_type;
        }

        public void setAdv_user_type(String adv_user_type) {
            this.adv_user_type = adv_user_type;
        }

        public String getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(String adv_id) {
            this.adv_id = adv_id;
        }
    }
}
