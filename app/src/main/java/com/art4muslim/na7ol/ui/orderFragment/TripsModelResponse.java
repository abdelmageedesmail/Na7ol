package com.art4muslim.na7ol.ui.orderFragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripsModelResponse {

    @Expose
    @SerializedName("total_items")
    private int total_items;
    @Expose
    @SerializedName("total_pages")
    private int total_pages;

    @Expose
    @SerializedName("return")
    private List<Returns> returns;

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


    public List<Returns> getReturns() {
        return returns;
    }

    public void setReturns(List<Returns> returns) {
        this.returns = returns;
    }

    public static class Returns {
        @Expose
        @SerializedName("created_since")
        private String created_since;
        @Expose
        @SerializedName("imageUrl")
        private String imageUrl;
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
        @SerializedName("order_types")
        private String order_types;
        @Expose
        @SerializedName("user_img")
        private String user_img;

        public String getServ_cartype_id() {
            return serv_cartype_id;
        }

        public void setServ_cartype_id(String serv_cartype_id) {
            this.serv_cartype_id = serv_cartype_id;
        }

        @Expose
        @SerializedName("serv_cartype_id")
        private String serv_cartype_id;

        public String getServ_cost() {
            return serv_cost;
        }

        public void setServ_cost(String serv_cost) {
            this.serv_cost = serv_cost;
        }

        @Expose
        @SerializedName("serv_cost")
        private String serv_cost;
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
        @SerializedName("serv_time")
        private String serv_time;

        public String getServ_from_city_id() {
            return serv_from_city_id;
        }

        public void setServ_from_city_id(String serv_from_city_id) {
            this.serv_from_city_id = serv_from_city_id;
        }

        @Expose
        @SerializedName("serv_from_city_id")
        private String serv_from_city_id;

        public String getServ_from_country_id() {
            return serv_from_country_id;
        }

        public void setServ_from_country_id(String serv_from_country_id) {
            this.serv_from_country_id = serv_from_country_id;
        }

        @Expose
        @SerializedName("serv_from_country_id")
        private String serv_from_country_id;
        @Expose
        @SerializedName("driver_img")
        private String driver_img;
        @Expose
        @SerializedName("driver_phone")
        private String driver_phone;
        @Expose
        @SerializedName("driver_name")
        private String driver_name;
        //        @Expose
//        @SerializedName("driver_rating")
//        private int driver_rating;
        @Expose
        @SerializedName("serv_car_license")
        private String serv_car_license;
        @Expose
        @SerializedName("serv_person_license")
        private String serv_person_license;
        @Expose
        @SerializedName("serv_day")
        private String serv_day;
        @Expose
        @SerializedName("serv_updated_at")
        private String serv_updated_at;
        @Expose
        @SerializedName("serv_created_at")
        private String serv_created_at;
        @Expose
        @SerializedName("serv_status")
        private String serv_status;
        @Expose
        @SerializedName("serv_car_license_id")
        private String serv_car_license_id;
        @Expose
        @SerializedName("serv_person_license_id")
        private String serv_person_license_id;
        @Expose
        @SerializedName("serv_receiver_name")
        private String serv_receiver_name;
        @Expose
        @SerializedName("serv_receiver_mobile")
        private String serv_receiver_mobile;
        @Expose
        @SerializedName("serv_type")
        private String serv_type;
        //        @Expose
//        @SerializedName("serv_cost")
//        private int serv_cost;
        @Expose
        @SerializedName("serv_date")
        private String serv_date;
        @Expose
        @SerializedName("serv_plan_weight")
        private String serv_plan_weight;
        @Expose
        @SerializedName("serv_to_hour")
        private String serv_to_hour;
        @Expose
        @SerializedName("serv_to_country_id")
        private String serv_to_country_id;
        @Expose
        @SerializedName("serv_to_city_id")
        private String serv_to_city_id;
        @Expose
        @SerializedName("serv_from_hour")
        private String serv_from_hour;
        //        @Expose
//        @SerializedName("serv_from_city_id")
//        private int serv_from_city_id;
//        @Expose
//        @SerializedName("serv_from_country_id")
//        private int serv_from_country_id;
//        @Expose
//        @SerializedName("serv_cartype_id")
//        private int serv_cartype_id;
        @Expose
        @SerializedName("serv_user_id")
        private String serv_user_id;
        @Expose
        @SerializedName("serv_driver_id")
        private String serv_driver_id;


        @Expose
        @SerializedName("cartype_icon")
        private String cartype_icon;

        public String getCartype_icon() {
            return cartype_icon;
        }

        public void setCartype_icon(String cartype_icon) {
            this.cartype_icon = cartype_icon;
        }

        public String getServ_driver_id() {
            return serv_driver_id;
        }

        public void setServ_driver_id(String serv_driver_id) {
            this.serv_driver_id = serv_driver_id;
        }

        @Expose
        @SerializedName("serv_id")
        private String serv_id;
        @Expose
        @SerializedName("user_rates_count")
        private int user_rates_count;
        @Expose
        @SerializedName("serv_weight")
        private String servWeight;
        @Expose
        @SerializedName("serv_weight_name")
        private String serv_weight_name;
        @Expose
        @SerializedName("serv_from_time")
        private String serv_from_time;
        @Expose
        @SerializedName("serv_to_time")
        private String serv_to_time;
        @Expose
        @SerializedName("serv_details")
        private String serv_details;
        @Expose
        @SerializedName("serv_order_type_id")
        private String serv_order_type_id;
        @Expose
        @SerializedName("order_serv_types")
        private String order_serv_types;


        public String getOrder_serv_types() {
            return order_serv_types;
        }

        public void setOrder_serv_types(String order_serv_types) {
            this.order_serv_types = order_serv_types;
        }

        public String getServ_weight_name() {
            return serv_weight_name;
        }

        public void setServ_weight_name(String serv_weight_name) {
            this.serv_weight_name = serv_weight_name;
        }

        public String getServ_order_type_id() {
            return serv_order_type_id;
        }

        public void setServ_order_type_id(String serv_order_type_id) {
            this.serv_order_type_id = serv_order_type_id;
        }

        public String getServ_details() {
            return serv_details;
        }

        public void setServ_details(String serv_details) {
            this.serv_details = serv_details;
        }

        public String getServ_receiver_name() {
            return serv_receiver_name;
        }

        public void setServ_receiver_name(String serv_receiver_name) {
            this.serv_receiver_name = serv_receiver_name;
        }

        public String getServ_receiver_mobile() {
            return serv_receiver_mobile;
        }

        public void setServ_receiver_mobile(String serv_receiver_mobile) {
            this.serv_receiver_mobile = serv_receiver_mobile;
        }

        public String getServ_time() {
            return serv_time;
        }

        public void setServ_time(String serv_time) {
            this.serv_time = serv_time;
        }

        public String getServ_from_time() {
            return serv_from_time;
        }

        public void setServ_from_time(String serv_from_time) {
            this.serv_from_time = serv_from_time;
        }

        public String getServ_to_time() {
            return serv_to_time;
        }

        public void setServ_to_time(String serv_to_time) {
            this.serv_to_time = serv_to_time;
        }

        public String getServWeight() {
            return servWeight;
        }

        public void setServWeight(String servWeight) {
            this.servWeight = servWeight;
        }

        public String getOrder_types() {
            return order_types;
        }

        public void setOrder_types(String order_types) {
            this.order_types = order_types;
        }

        public int getUser_rates_count() {
            return user_rates_count;
        }

        public void setUser_rates_count(int user_rates_count) {
            this.user_rates_count = user_rates_count;
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

//        public int getDriver_rating() {
//            return driver_rating;
//        }
//
//        public void setDriver_rating(int driver_rating) {
//            this.driver_rating = driver_rating;2
//        }

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

        public String getServ_day() {
            return serv_day;
        }

        public void setServ_day(String serv_day) {
            this.serv_day = serv_day;
        }

        public String getServ_updated_at() {
            return serv_updated_at;
        }

        public void setServ_updated_at(String serv_updated_at) {
            this.serv_updated_at = serv_updated_at;
        }

        public String getServ_created_at() {
            return serv_created_at;
        }

        public void setServ_created_at(String serv_created_at) {
            this.serv_created_at = serv_created_at;
        }

        public String getServ_status() {
            return serv_status;
        }

        public void setServ_status(String serv_status) {
            this.serv_status = serv_status;
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

        public String getServ_type() {
            return serv_type;
        }

        public void setServ_type(String serv_type) {
            this.serv_type = serv_type;
        }
//
//        public int getServ_cost() {
//            return serv_cost;
//        }
//
//        public void setServ_cost(int serv_cost) {
//            this.serv_cost = serv_cost;
//        }

        public String getServ_date() {
            return serv_date;
        }

        public void setServ_date(String serv_date) {
            this.serv_date = serv_date;
        }

        public String getServ_plan_weight() {
            return serv_plan_weight;
        }

        public void setServ_plan_weight(String serv_plan_weight) {
            this.serv_plan_weight = serv_plan_weight;
        }

        public String getServ_to_hour() {
            return serv_to_hour;
        }

        public void setServ_to_hour(String serv_to_hour) {
            this.serv_to_hour = serv_to_hour;
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

        public String getServ_from_hour() {
            return serv_from_hour;
        }

        public void setServ_from_hour(String serv_from_hour) {
            this.serv_from_hour = serv_from_hour;
        }

        //
//        public int getServ_from_city_id() {
//            return serv_from_city_id;
//        }
//
//        public void setServ_from_city_id(int serv_from_city_id) {
//            this.serv_from_city_id = serv_from_city_id;
//        }
//
//        public int getServ_from_country_id() {
//            return serv_from_country_id;
//        }
//
//        public void setServ_from_country_id(int serv_from_country_id) {
//            this.serv_from_country_id = serv_from_country_id;
//        }
//
//        public int getServ_cartype_id() {
//            return serv_cartype_id;
//        }
//
//        public void setServ_cartype_id(int serv_cartype_id) {
//            this.serv_cartype_id = serv_cartype_id;
//        }
//
        public String getServ_user_id() {
            return serv_user_id;
        }

        public void setServ_user_id(String serv_user_id) {
            this.serv_user_id = serv_user_id;
        }

//        public int getServ_driver_id() {
//            return serv_driver_id;
//        }
//
//        public void setServ_driver_id(int serv_driver_id) {
//            this.serv_driver_id = serv_driver_id;
//        }

        public String getServ_id() {
            return serv_id;
        }

        public void setServ_id(String serv_id) {
            this.serv_id = serv_id;
        }
    }
}
