package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationModel {

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
        @SerializedName("message_sender")
        private String message_sender;
        @Expose
        @SerializedName("notify_agreement_owner")
        private String notify_agreement_owner;
        @Expose
        @SerializedName("notify_service_to_country")
        private String notify_service_to_country;
        @Expose
        @SerializedName("notify_service_from_country")
        private String notify_service_from_country;
        @Expose
        @SerializedName("notify_service_to")
        private String notify_service_to;
        @Expose
        @SerializedName("notify_service_from")
        private String notify_service_from;
        @Expose
        @SerializedName("notify_service_date")
        private String notify_service_date;
        @Expose
        @SerializedName("notify_service_owner_image")
        private String notify_service_owner_image;
        @Expose
        @SerializedName("notify_service_owner_name")
        private String notify_service_owner_name;
        @Expose
        @SerializedName("notify_service_status")
        private String notify_service_status;
        @Expose
        @SerializedName("notify_message_to")
        private String notify_message_to;
        @Expose
        @SerializedName("notify_message_from")
        private String notify_message_from;
        @Expose
        @SerializedName("notify_message_service_type")
        private String notify_message_service_type;
        @Expose
        @SerializedName("notify_message_service_id")
        private String notify_message_service_id;
        @Expose
        @SerializedName("created_since")
        private String created_since;
        @Expose
        @SerializedName("notify_updated_at")
        private String notify_updated_at;
        @Expose
        @SerializedName("notify_created_at")
        private String notify_created_at;
        @Expose
        @SerializedName("notify_pushed_firebase")
        private String notify_pushed_firebase;
        @Expose
        @SerializedName("notify_read")
        private String notify_read;
        @Expose
        @SerializedName("notify_comment_id")
        private String notify_comment_id;
        @Expose
        @SerializedName("notify_text")
        private String notify_text;
        @Expose
        @SerializedName("notify_msg_id")
        private String notify_msg_id;
        @Expose
        @SerializedName("notify_service_id")
        private String notify_service_id;
        @Expose
        @SerializedName("notify_type")
        private String notify_type;
        @Expose
        @SerializedName("notify_sender_chat_id")
        private String notify_sender_chat_id;
        @Expose
        @SerializedName("notify_advertiser_id")
        private String notify_advertiser_id;
        @Expose
        @SerializedName("notify_id")
        private String notify_id;
        @Expose
        @SerializedName("notify_message_is_adminstrative")
        private String notify_message_is_adminstrative;

        public String getNotify_message_is_adminstrative() {
            return notify_message_is_adminstrative;
        }

        public void setNotify_message_is_adminstrative(String notify_message_is_adminstrative) {
            this.notify_message_is_adminstrative = notify_message_is_adminstrative;
        }

        public String getMessage_sender() {
            return message_sender;
        }

        public void setMessage_sender(String message_sender) {
            this.message_sender = message_sender;
        }

        public String getNotify_agreement_owner() {
            return notify_agreement_owner;
        }

        public void setNotify_agreement_owner(String notify_agreement_owner) {
            this.notify_agreement_owner = notify_agreement_owner;
        }

        public String getNotify_service_to_country() {
            return notify_service_to_country;
        }

        public void setNotify_service_to_country(String notify_service_to_country) {
            this.notify_service_to_country = notify_service_to_country;
        }

        public String getNotify_service_from_country() {
            return notify_service_from_country;
        }

        public void setNotify_service_from_country(String notify_service_from_country) {
            this.notify_service_from_country = notify_service_from_country;
        }

        public String getNotify_service_to() {
            return notify_service_to;
        }

        public void setNotify_service_to(String notify_service_to) {
            this.notify_service_to = notify_service_to;
        }

        public String getNotify_service_from() {
            return notify_service_from;
        }

        public void setNotify_service_from(String notify_service_from) {
            this.notify_service_from = notify_service_from;
        }

        public String getNotify_service_date() {
            return notify_service_date;
        }

        public void setNotify_service_date(String notify_service_date) {
            this.notify_service_date = notify_service_date;
        }

        public String getNotify_service_owner_image() {
            return notify_service_owner_image;
        }

        public void setNotify_service_owner_image(String notify_service_owner_image) {
            this.notify_service_owner_image = notify_service_owner_image;
        }

        public String getNotify_service_owner_name() {
            return notify_service_owner_name;
        }

        public void setNotify_service_owner_name(String notify_service_owner_name) {
            this.notify_service_owner_name = notify_service_owner_name;
        }

        public String getNotify_service_status() {
            return notify_service_status;
        }

        public void setNotify_service_status(String notify_service_status) {
            this.notify_service_status = notify_service_status;
        }

        public String getNotify_message_to() {
            return notify_message_to;
        }

        public void setNotify_message_to(String notify_message_to) {
            this.notify_message_to = notify_message_to;
        }

        public String getNotify_message_from() {
            return notify_message_from;
        }

        public void setNotify_message_from(String notify_message_from) {
            this.notify_message_from = notify_message_from;
        }

        public String getNotify_message_service_type() {
            return notify_message_service_type;
        }

        public void setNotify_message_service_type(String notify_message_service_type) {
            this.notify_message_service_type = notify_message_service_type;
        }

        public String getNotify_message_service_id() {
            return notify_message_service_id;
        }

        public void setNotify_message_service_id(String notify_message_service_id) {
            this.notify_message_service_id = notify_message_service_id;
        }

        public String getCreated_since() {
            return created_since;
        }

        public void setCreated_since(String created_since) {
            this.created_since = created_since;
        }

        public String getNotify_updated_at() {
            return notify_updated_at;
        }

        public void setNotify_updated_at(String notify_updated_at) {
            this.notify_updated_at = notify_updated_at;
        }

        public String getNotify_created_at() {
            return notify_created_at;
        }

        public void setNotify_created_at(String notify_created_at) {
            this.notify_created_at = notify_created_at;
        }

        public String getNotify_pushed_firebase() {
            return notify_pushed_firebase;
        }

        public void setNotify_pushed_firebase(String notify_pushed_firebase) {
            this.notify_pushed_firebase = notify_pushed_firebase;
        }

        public String getNotify_read() {
            return notify_read;
        }

        public void setNotify_read(String notify_read) {
            this.notify_read = notify_read;
        }

        public String getNotify_comment_id() {
            return notify_comment_id;
        }

        public void setNotify_comment_id(String notify_comment_id) {
            this.notify_comment_id = notify_comment_id;
        }

        public String getNotify_text() {
            return notify_text;
        }

        public void setNotify_text(String notify_text) {
            this.notify_text = notify_text;
        }

        public String getNotify_msg_id() {
            return notify_msg_id;
        }

        public void setNotify_msg_id(String notify_msg_id) {
            this.notify_msg_id = notify_msg_id;
        }

        public String getNotify_service_id() {
            return notify_service_id;
        }

        public void setNotify_service_id(String notify_service_id) {
            this.notify_service_id = notify_service_id;
        }

        public String getNotify_type() {
            return notify_type;
        }

        public void setNotify_type(String notify_type) {
            this.notify_type = notify_type;
        }

        public String getNotify_sender_chat_id() {
            return notify_sender_chat_id;
        }

        public void setNotify_sender_chat_id(String notify_sender_chat_id) {
            this.notify_sender_chat_id = notify_sender_chat_id;
        }

        public String getNotify_advertiser_id() {
            return notify_advertiser_id;
        }

        public void setNotify_advertiser_id(String notify_advertiser_id) {
            this.notify_advertiser_id = notify_advertiser_id;
        }

        public String getNotify_id() {
            return notify_id;
        }

        public void setNotify_id(String notify_id) {
            this.notify_id = notify_id;
        }
    }
}
