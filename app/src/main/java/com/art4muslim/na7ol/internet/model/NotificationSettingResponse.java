package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationSettingResponse {


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
        @SerializedName("orderTypes")
        private List<String> orderTypes;
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
        @SerializedName("cityNameFrom")
        private String cityNameFrom;
        @Expose
        @SerializedName("cityNameTo")
        private String cityNameTo;

        public String getCityNameFrom() {
            return cityNameFrom;
        }

        public void setCityNameFrom(String cityNameFrom) {
            this.cityNameFrom = cityNameFrom;
        }

        public String getCityNameTo() {
            return cityNameTo;
        }

        public void setCityNameTo(String cityNameTo) {
            this.cityNameTo = cityNameTo;
        }

        public List<String> getOrderTypes() {
            return orderTypes;
        }

        public void setOrderTypes(List<String> orderTypes) {
            this.orderTypes = orderTypes;
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
    }
}
