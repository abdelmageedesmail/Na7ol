package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.SerializedName;

public class AppDataSocial {

    @SerializedName("return")
    Return returns;

    public Return getReturns() {
        return returns;
    }

    public void setReturns(Return returns) {
        this.returns = returns;
    }

    public class Return {
        @SerializedName("app_face")
        String app_face;
        @SerializedName("app_twitter")
        String app_twitter;
        @SerializedName("app_youtube")
        String app_youtube;
        @SerializedName("app_ins")
        String app_ins;
        @SerializedName("app_linkd")
        String app_linkd;
        @SerializedName("app_gplus")
        String app_gplus;
        @SerializedName("app_whatsapp")
        String app_whatsapp;
        @SerializedName("about_app")
        String about_app;
        @SerializedName("app_terms")
        String app_terms;

        public String getApp_whatsapp() {
            return app_whatsapp;
        }

        public void setApp_whatsapp(String app_whatsapp) {
            this.app_whatsapp = app_whatsapp;
        }

        public String getAbout_app() {
            return about_app;
        }

        public void setAbout_app(String about_app) {
            this.about_app = about_app;
        }

        public String getApp_terms() {
            return app_terms;
        }

        public void setApp_terms(String app_terms) {
            this.app_terms = app_terms;
        }

        public String getApp_face() {
            return app_face;
        }

        public void setApp_face(String app_face) {
            this.app_face = app_face;
        }

        public String getApp_twitter() {
            return app_twitter;
        }

        public void setApp_twitter(String app_twitter) {
            this.app_twitter = app_twitter;
        }

        public String getApp_youtube() {
            return app_youtube;
        }

        public void setApp_youtube(String app_youtube) {
            this.app_youtube = app_youtube;
        }

        public String getApp_ins() {
            return app_ins;
        }

        public void setApp_ins(String app_ins) {
            this.app_ins = app_ins;
        }

        public String getApp_linkd() {
            return app_linkd;
        }

        public void setApp_linkd(String app_linkd) {
            this.app_linkd = app_linkd;
        }

        public String getApp_gplus() {
            return app_gplus;
        }

        public void setApp_gplus(String app_gplus) {
            this.app_gplus = app_gplus;
        }
    }
}
