package com.perception.model;

// Diary Class
public class Diary {

    String feel;
    String intense;
    String date;
    String time;
    String location;
    String happened;
    String negative;
    String advice;
    String id;
    String user_id;

    // String data for adding an entry to diary
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getIntense() {
        return intense;
    }

    public void setIntense(String intense) {
        this.intense = intense;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHappened() {
        return happened;
    }

    public void setHappened(String happened) {
        this.happened = happened;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
