package com.mobatia.nasmanila.fragments.notifications_new.model;

import java.io.Serializable;

public class PushNotificationModelNew implements Serializable {

    String message;
    String url;
    String date;
    String push_from ;
    String type ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPush_from() {
        return push_from;
    }

    public void setPush_from(String push_from) {
        this.push_from = push_from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    String headTitle;
//    String id;
//    String Alert_type;
}
