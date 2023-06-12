package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse {

    @Expose
    @SerializedName("return")
    private List<Return> returns;

    public List<Return> getReturns() {
        return returns;
    }

    public void setReturns(List<Return> returns) {
        this.returns= returns;
    }


    public static class Return {
        @Expose
        @SerializedName("country_id")
        private int country_id;
        @Expose
        @SerializedName("country_name")
        private String country_name;
        @Expose
        @SerializedName("country_code")
        private String country_code;


        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }
    }
}
