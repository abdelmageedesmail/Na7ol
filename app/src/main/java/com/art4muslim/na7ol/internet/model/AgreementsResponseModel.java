package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AgreementsResponseModel {

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
        @SerializedName("serv_car_license")
        private String serv_car_license;
        @Expose
        @SerializedName("serv_person_license")
        private String serv_person_license;
        @Expose
        @SerializedName("serv_plan_weight")
        private String serv_plan_weight;
        @Expose
        @SerializedName("serv_day")
        private String serv_day;
        @Expose
        @SerializedName("other_images")
        private String other_images;
        @Expose
        @SerializedName("servWeight")
        private String servWeight;
        @Expose
        @SerializedName("to_country_name")
        private String to_country_name;
        @Expose
        @SerializedName("to_city_name")
        private String to_city_name;
        @Expose
        @SerializedName("from_city_name")
        private String from_city_name;
        @Expose
        @SerializedName("from_country_name")
        private String from_country_name;
        @Expose
        @SerializedName("cartype_name")
        private String cartype_name;
        @Expose
        @SerializedName("user_img")
        private String user_img;
        @Expose
        @SerializedName("user_phone")
        private String user_phone;
        @Expose
        @SerializedName("user_name")
        private String user_name;
        @Expose
        @SerializedName("user_rating")
        private int user_rating;
        @Expose
        @SerializedName("rates_count")
        private String rates_count;
        @Expose
        @SerializedName("driver_img")
        private String driver_img;
        @Expose
        @SerializedName("driver_phone")
        private String driver_phone;
        @Expose
        @SerializedName("driver_name")
        private String driver_name;
        @Expose
        @SerializedName("driver_rates_count")
        private int driver_rates_count;
        @Expose
        @SerializedName("driver_rating")
        private int driver_rating;
        @Expose
        @SerializedName("serv_created_at")
        private String serv_created_at;
        @Expose
        @SerializedName("serv_time")
        private String serv_time;
        @Expose
        @SerializedName("serv_status")
        private String serv_status;
        @Expose
        @SerializedName("serv_contract")
        private String serv_contract;
        @Expose
        @SerializedName("serv_receiver_mobile")
        private String serv_receiver_mobile;
        @Expose
        @SerializedName("serv_delivery_type_id")
        private String serv_delivery_type_id;
        @Expose
        @SerializedName("serv_receiver_name")
        private String serv_receiver_name;
        @Expose
        @SerializedName("serv_car_license_id")
        private String serv_car_license_id;
        @Expose
        @SerializedName("serv_person_license_id")
        private String serv_person_license_id;
        @Expose
        @SerializedName("serv_order_type_id")
        private String serv_order_type_id;
        @Expose
        @SerializedName("serv_travel_type")
        private String serv_travel_type;
        @Expose
        @SerializedName("serv_parent_id")
        private String serv_parent_id;
        @Expose
        @SerializedName("serv_type")
        private String serv_type;
        @Expose
        @SerializedName("serv_cost")
        private String serv_cost;
        @Expose
        @SerializedName("serv_date")
        private String serv_date;
        @Expose
        @SerializedName("serv_weight")
        private String serv_weight;
        @Expose
        @SerializedName("serv_to_time")
        private String serv_to_time;
        @Expose
        @SerializedName("serv_to_country_id")
        private String serv_to_country_id;
        @Expose
        @SerializedName("serv_to_city_id")
        private String serv_to_city_id;
        @Expose
        @SerializedName("serv_from_time")
        private String serv_from_time;
        @Expose
        @SerializedName("serv_from_city_id")
        private String serv_from_city_id;
        @Expose
        @SerializedName("serv_from_country_id")
        private String serv_from_country_id;
        @Expose
        @SerializedName("serv_cartype_id")
        private String serv_cartype_id;
        @Expose
        @SerializedName("serv_user_id")
        private String serv_user_id;
        @Expose
        @SerializedName("serv_driver_id")
        private String serv_driver_id;
        @Expose
        @SerializedName("serv_id")
        private String serv_id;
        @Expose
        @SerializedName("created_since")
        private String created_since;
        @Expose
        @SerializedName("imageUrl")
        private String imageUrl;
        @Expose
        @SerializedName("to_user_image")
        private String to_user_image;
        @Expose
        @SerializedName("to_user_name")
        private String to_user_name;
        @Expose
        @SerializedName("from_user_image")
        private String from_user_image;
        @Expose
        @SerializedName("from_user_name")
        private String from_user_name;
        @Expose
        @SerializedName("order_types")
        private String order_types;
        @Expose
        @SerializedName("agr_created_at")
        private String agr_created_at;
        @Expose
        @SerializedName("agr_code")
        private String agr_code;
        @Expose
        @SerializedName("agr_type")
        private String agr_type;
        @Expose
        @SerializedName("agr_auth_code")
        private String agr_auth_code;
        @Expose
        @SerializedName("agr_weight")
        private String agr_weight;
        @Expose
        @SerializedName("agr_details")
        private String agr_details;
        @Expose
        @SerializedName("agr_thumb_id")
        private String agr_thumb_id;
        @Expose
        @SerializedName("agr_status")
        private String agr_status;
        @Expose
        @SerializedName("agr_receiver_mobile")
        private String agr_receiver_mobile;
        @Expose
        @SerializedName("agr_receiver_name")
        private String agr_receiver_name;
        @Expose
        @SerializedName("agr_package_details")
        private String agr_package_details;
        @Expose
        @SerializedName("agr_to_id")
        private String agr_to_id;
        @Expose
        @SerializedName("agr_from_id")
        private String agr_from_id;
        @Expose
        @SerializedName("agr_delivery_type_id")
        private String agr_delivery_type_id;
        @Expose
        @SerializedName("agr_delivery_type_name")
        private String agr_delivery_type_name;
        @Expose
        @SerializedName("agr_cost_status")
        private String agr_cost_status;
        @Expose
        @SerializedName("agr_cost_added_at")
        private String agr_cost_added_at;
        @Expose
        @SerializedName("agr_cost")
        private String agr_cost;
        @Expose
        @SerializedName("agr_order_type_id")
        private String agr_order_type_id;
        @Expose
        @SerializedName("agr_serv_id")
        private String agr_serv_id;
        @Expose
        @SerializedName("agr_id")
        private String agr_id;
        @Expose
        @SerializedName("serv_weight_name")
        private String serv_weight_name;
        @Expose
        @SerializedName("cartype_icon")
        private String cartype_icon;

        public String getCartype_icon() {
            return cartype_icon;
        }

        public void setCartype_icon(String cartype_icon) {
            this.cartype_icon = cartype_icon;
        }

        public String getServ_weight_name() {
            return serv_weight_name;
        }

        public void setServ_weight_name(String serv_weight_name) {
            this.serv_weight_name = serv_weight_name;
        }

        public String getAgr_delivery_type_name() {
            return agr_delivery_type_name;
        }

        public void setAgr_delivery_type_name(String agr_delivery_type_name) {
            this.agr_delivery_type_name = agr_delivery_type_name;
        }

        public String getServ_car_license() {
            return serv_car_license;
        }

        public void setServ_car_license(String serv_car_license) {
            this.serv_car_license = serv_car_license;
        }

        public String getServ_person_license() {
            return serv_person_license;
        }

        public void setServ_person_license(String serv_person_license) {
            this.serv_person_license = serv_person_license;
        }

        public String getServ_plan_weight() {
            return serv_plan_weight;
        }

        public void setServ_plan_weight(String serv_plan_weight) {
            this.serv_plan_weight = serv_plan_weight;
        }

        public String getServ_day() {
            return serv_day;
        }

        public void setServ_day(String serv_day) {
            this.serv_day = serv_day;
        }

        public String getOther_images() {
            return other_images;
        }

        public void setOther_images(String other_images) {
            this.other_images = other_images;
        }

        public String getServWeight() {
            return servWeight;
        }

        public void setServWeight(String servWeight) {
            this.servWeight = servWeight;
        }

        public String getTo_country_name() {
            return to_country_name;
        }

        public void setTo_country_name(String to_country_name) {
            this.to_country_name = to_country_name;
        }

        public String getTo_city_name() {
            return to_city_name;
        }

        public void setTo_city_name(String to_city_name) {
            this.to_city_name = to_city_name;
        }

        public String getFrom_city_name() {
            return from_city_name;
        }

        public void setFrom_city_name(String from_city_name) {
            this.from_city_name = from_city_name;
        }

        public String getFrom_country_name() {
            return from_country_name;
        }

        public void setFrom_country_name(String from_country_name) {
            this.from_country_name = from_country_name;
        }

        public String getCartype_name() {
            return cartype_name;
        }

        public void setCartype_name(String cartype_name) {
            this.cartype_name = cartype_name;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(int user_rating) {
            this.user_rating = user_rating;
        }

        public String getRates_count() {
            return rates_count;
        }

        public void setRates_count(String rates_count) {
            this.rates_count = rates_count;
        }

        public String getDriver_img() {
            return driver_img;
        }

        public void setDriver_img(String driver_img) {
            this.driver_img = driver_img;
        }

        public String getDriver_phone() {
            return driver_phone;
        }

        public void setDriver_phone(String driver_phone) {
            this.driver_phone = driver_phone;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public int getDriver_rates_count() {
            return driver_rates_count;
        }

        public void setDriver_rates_count(int driver_rates_count) {
            this.driver_rates_count = driver_rates_count;
        }

        public int getDriver_rating() {
            return driver_rating;
        }

        public void setDriver_rating(int driver_rating) {
            this.driver_rating = driver_rating;
        }

        public String getServ_created_at() {
            return serv_created_at;
        }

        public void setServ_created_at(String serv_created_at) {
            this.serv_created_at = serv_created_at;
        }

        public String getServ_time() {
            return serv_time;
        }

        public void setServ_time(String serv_time) {
            this.serv_time = serv_time;
        }

        public String getServ_status() {
            return serv_status;
        }

        public void setServ_status(String serv_status) {
            this.serv_status = serv_status;
        }

        public String getServ_contract() {
            return serv_contract;
        }

        public void setServ_contract(String serv_contract) {
            this.serv_contract = serv_contract;
        }

        public String getServ_receiver_mobile() {
            return serv_receiver_mobile;
        }

        public void setServ_receiver_mobile(String serv_receiver_mobile) {
            this.serv_receiver_mobile = serv_receiver_mobile;
        }

        public String getServ_delivery_type_id() {
            return serv_delivery_type_id;
        }

        public void setServ_delivery_type_id(String serv_delivery_type_id) {
            this.serv_delivery_type_id = serv_delivery_type_id;
        }

        public String getServ_receiver_name() {
            return serv_receiver_name;
        }

        public void setServ_receiver_name(String serv_receiver_name) {
            this.serv_receiver_name = serv_receiver_name;
        }

        public String getServ_car_license_id() {
            return serv_car_license_id;
        }

        public void setServ_car_license_id(String serv_car_license_id) {
            this.serv_car_license_id = serv_car_license_id;
        }

        public String getServ_person_license_id() {
            return serv_person_license_id;
        }

        public void setServ_person_license_id(String serv_person_license_id) {
            this.serv_person_license_id = serv_person_license_id;
        }

        public String getServ_order_type_id() {
            return serv_order_type_id;
        }

        public void setServ_order_type_id(String serv_order_type_id) {
            this.serv_order_type_id = serv_order_type_id;
        }

        public String getServ_travel_type() {
            return serv_travel_type;
        }

        public void setServ_travel_type(String serv_travel_type) {
            this.serv_travel_type = serv_travel_type;
        }

        public String getServ_parent_id() {
            return serv_parent_id;
        }

        public void setServ_parent_id(String serv_parent_id) {
            this.serv_parent_id = serv_parent_id;
        }

        public String getServ_type() {
            return serv_type;
        }

        public void setServ_type(String serv_type) {
            this.serv_type = serv_type;
        }

        public String getServ_cost() {
            return serv_cost;
        }

        public void setServ_cost(String serv_cost) {
            this.serv_cost = serv_cost;
        }

        public String getServ_date() {
            return serv_date;
        }

        public void setServ_date(String serv_date) {
            this.serv_date = serv_date;
        }

        public String getServ_weight() {
            return serv_weight;
        }

        public void setServ_weight(String serv_weight) {
            this.serv_weight = serv_weight;
        }

        public String getServ_to_time() {
            return serv_to_time;
        }

        public void setServ_to_time(String serv_to_time) {
            this.serv_to_time = serv_to_time;
        }

        public String getServ_to_country_id() {
            return serv_to_country_id;
        }

        public void setServ_to_country_id(String serv_to_country_id) {
            this.serv_to_country_id = serv_to_country_id;
        }

        public String getServ_to_city_id() {
            return serv_to_city_id;
        }

        public void setServ_to_city_id(String serv_to_city_id) {
            this.serv_to_city_id = serv_to_city_id;
        }

        public String getServ_from_time() {
            return serv_from_time;
        }

        public void setServ_from_time(String serv_from_time) {
            this.serv_from_time = serv_from_time;
        }

        public String getServ_from_city_id() {
            return serv_from_city_id;
        }

        public void setServ_from_city_id(String serv_from_city_id) {
            this.serv_from_city_id = serv_from_city_id;
        }

        public String getServ_from_country_id() {
            return serv_from_country_id;
        }

        public void setServ_from_country_id(String serv_from_country_id) {
            this.serv_from_country_id = serv_from_country_id;
        }

        public String getServ_cartype_id() {
            return serv_cartype_id;
        }

        public void setServ_cartype_id(String serv_cartype_id) {
            this.serv_cartype_id = serv_cartype_id;
        }

        public String getServ_user_id() {
            return serv_user_id;
        }

        public void setServ_user_id(String serv_user_id) {
            this.serv_user_id = serv_user_id;
        }

        public String getServ_driver_id() {
            return serv_driver_id;
        }

        public void setServ_driver_id(String serv_driver_id) {
            this.serv_driver_id = serv_driver_id;
        }

        public String getServ_id() {
            return serv_id;
        }

        public void setServ_id(String serv_id) {
            this.serv_id = serv_id;
        }

        public String getCreated_since() {
            return created_since;
        }

        public void setCreated_since(String created_since) {
            this.created_since = created_since;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTo_user_image() {
            return to_user_image;
        }

        public void setTo_user_image(String to_user_image) {
            this.to_user_image = to_user_image;
        }

        public String getTo_user_name() {
            return to_user_name;
        }

        public void setTo_user_name(String to_user_name) {
            this.to_user_name = to_user_name;
        }

        public String getFrom_user_image() {
            return from_user_image;
        }

        public void setFrom_user_image(String from_user_image) {
            this.from_user_image = from_user_image;
        }

        public String getFrom_user_name() {
            return from_user_name;
        }

        public void setFrom_user_name(String from_user_name) {
            this.from_user_name = from_user_name;
        }

        public String getOrder_types() {
            return order_types;
        }

        public void setOrder_types(String order_types) {
            this.order_types = order_types;
        }

        public String getAgr_created_at() {
            return agr_created_at;
        }

        public void setAgr_created_at(String agr_created_at) {
            this.agr_created_at = agr_created_at;
        }

        public String getAgr_code() {
            return agr_code;
        }

        public void setAgr_code(String agr_code) {
            this.agr_code = agr_code;
        }

        public String getAgr_type() {
            return agr_type;
        }

        public void setAgr_type(String agr_type) {
            this.agr_type = agr_type;
        }

        public String getAgr_auth_code() {
            return agr_auth_code;
        }

        public void setAgr_auth_code(String agr_auth_code) {
            this.agr_auth_code = agr_auth_code;
        }

        public String getAgr_weight() {
            return agr_weight;
        }

        public void setAgr_weight(String agr_weight) {
            this.agr_weight = agr_weight;
        }

        public String getAgr_details() {
            return agr_details;
        }

        public void setAgr_details(String agr_details) {
            this.agr_details = agr_details;
        }

        public String getAgr_thumb_id() {
            return agr_thumb_id;
        }

        public void setAgr_thumb_id(String agr_thumb_id) {
            this.agr_thumb_id = agr_thumb_id;
        }

        public String getAgr_status() {
            return agr_status;
        }

        public void setAgr_status(String agr_status) {
            this.agr_status = agr_status;
        }

        public String getAgr_receiver_mobile() {
            return agr_receiver_mobile;
        }

        public void setAgr_receiver_mobile(String agr_receiver_mobile) {
            this.agr_receiver_mobile = agr_receiver_mobile;
        }

        public String getAgr_receiver_name() {
            return agr_receiver_name;
        }

        public void setAgr_receiver_name(String agr_receiver_name) {
            this.agr_receiver_name = agr_receiver_name;
        }

        public String getAgr_package_details() {
            return agr_package_details;
        }

        public void setAgr_package_details(String agr_package_details) {
            this.agr_package_details = agr_package_details;
        }

        public String getAgr_to_id() {
            return agr_to_id;
        }

        public void setAgr_to_id(String agr_to_id) {
            this.agr_to_id = agr_to_id;
        }

        public String getAgr_from_id() {
            return agr_from_id;
        }

        public void setAgr_from_id(String agr_from_id) {
            this.agr_from_id = agr_from_id;
        }

        public String getAgr_delivery_type_id() {
            return agr_delivery_type_id;
        }

        public void setAgr_delivery_type_id(String agr_delivery_type_id) {
            this.agr_delivery_type_id = agr_delivery_type_id;
        }

        public String getAgr_cost_status() {
            return agr_cost_status;
        }

        public void setAgr_cost_status(String agr_cost_status) {
            this.agr_cost_status = agr_cost_status;
        }

        public String getAgr_cost_added_at() {
            return agr_cost_added_at;
        }

        public void setAgr_cost_added_at(String agr_cost_added_at) {
            this.agr_cost_added_at = agr_cost_added_at;
        }

        public String getAgr_cost() {
            return agr_cost;
        }

        public void setAgr_cost(String agr_cost) {
            this.agr_cost = agr_cost;
        }

        public String getAgr_order_type_id() {
            return agr_order_type_id;
        }

        public void setAgr_order_type_id(String agr_order_type_id) {
            this.agr_order_type_id = agr_order_type_id;
        }

        public String getAgr_serv_id() {
            return agr_serv_id;
        }

        public void setAgr_serv_id(String agr_serv_id) {
            this.agr_serv_id = agr_serv_id;
        }

        public String getAgr_id() {
            return agr_id;
        }

        public void setAgr_id(String agr_id) {
            this.agr_id = agr_id;
        }
    }
}
