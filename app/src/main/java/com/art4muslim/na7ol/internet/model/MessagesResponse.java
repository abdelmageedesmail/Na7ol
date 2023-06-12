package com.art4muslim.na7ol.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessagesResponse {

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
        @SerializedName("imageUrl")
        private String imageUrl;
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
        @SerializedName("msg_user_id")
        private String msg_user_id;
        @Expose
        @SerializedName("msg_thumb_id")
        private int msg_thumb_id;
        @Expose
        @SerializedName("msg_location")
        private String msg_location;
        @Expose
        @SerializedName("msg_text")
        private String msg_text;
        @Expose
        @SerializedName("ID")
        private String ID;

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

        public String getMsg_user_id() {
            return msg_user_id;
        }

        public void setMsg_user_id(String msg_user_id) {
            this.msg_user_id = msg_user_id;
        }

        public int getMsg_thumb_id() {
            return msg_thumb_id;
        }

        public void setMsg_thumb_id(int msg_thumb_id) {
            this.msg_thumb_id = msg_thumb_id;
        }

        public String getMsg_location() {
            return msg_location;
        }

        public void setMsg_location(String msg_location) {
            this.msg_location = msg_location;
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
