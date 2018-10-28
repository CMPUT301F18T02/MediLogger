package com.example.symptologger;

import java.util.Date;

class Concern {
    private String title;
    private Date date;
    private String description;

    Concern(){
        this.title = "No title given";
        this.date = new Date();
        this.description = "No description given";
    }

    Concern(String title, Date date, String description){
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
