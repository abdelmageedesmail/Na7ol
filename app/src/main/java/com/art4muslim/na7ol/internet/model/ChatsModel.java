package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatsModel {

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
        @SerializedName("created_since")
        private String created_since;
        @Expose
        @SerializedName("msg_created_at")
        private String msg_created_at;
        @Expose
        @SerializedName("msg_user_image")
        private String msg_user_image;
        @Expose
        @SerializedName("msg_user_name")
        private String msg_user_name;
        @Expose
        @SerializedName("msg_read")
        private int msg_read;
        @Expose
        @SerializedName("msg_user_id")
        private String msg_user_id;
        @SerializedName("msg_service_id")
        private
        String msg_service_id;
        @Expose
        @SerializedName("msg_text")
        private String msg_text;
        @Expose
        @SerializedName("ID")
        private String ID;
        @Expose
        @SerializedName("msg_is_administrative")
        private String msg_is_administrative;

        public String getMsg_is_administrative() {
            return msg_is_administrative;
        }

        public void setMsg_is_administrative(String msg_is_administrative) {
            this.msg_is_administrative = msg_is_administrative;
        }

        public String getMsg_service_id() {
            return msg_service_id;
        }

        public void setMsg_service_id(String msg_service_id) {
            this.msg_service_id = msg_service_id;
        }

        public String getCreated_since() {
            return created_since;
        }

        public void setCreated_since(String created_since) {
            this.created_since = created_since;
        }

        public String getMsg_created_at() {
            return msg_created_at;
        }

        public void setMsg_created_at(String msg_created_at) {
            this.msg_created_at = msg_created_at;
        }

        public String getMsg_user_image() {
            return msg_user_image;
        }

        public void setMsg_user_image(String msg_user_image) {
            this.msg_user_image = msg_user_image;
        }

        public String getMsg_user_name() {
            return msg_user_name;
        }

        public void setMsg_user_name(String msg_user_name) {
            this.msg_user_name = msg_user_name;
        }

        public int getMsg_read() {
            return msg_read;
        }

        public void setMsg_read(int msg_read) {
            this.msg_read = msg_read;
        }

        public String getMsg_user_id() {
            return msg_user_id;
        }

        public void setMsg_user_id(String msg_user_id) {
            this.msg_user_id = msg_user_id;
        }

        public String getMsg_text() {
            return msg_text;
        }

        public void setMsg_text(String msg_text) {
            this.msg_text = msg_text;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
    }
}
