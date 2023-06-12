package com.art4muslim.na7ol.internet.model;

/**
 * Created by Abdelmageed on 8/27/2018.
 */

public class ChatModel {
    private String date;
    private String to;
    private String message;
    private String from;
    private String lat;
    private String lon;
    private String toName;
    private String file, toImage, fromImage;

    public String getToImage() {
        return toImage;
    }

    public void setToImage(String toImage) {
        this.toImage = toImage;
    }

    public String getFromImage() {
        return fromImage;
    }

    public void setFromImage(String fromImage) {
        this.fromImage = fromImage;
    }

    public ChatModel() {
    }

    public ChatModel(String date, String to, String message, String from, int type) {
        this.date = date;
        this.to = to;
        this.message = message;
        this.from = from;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}
