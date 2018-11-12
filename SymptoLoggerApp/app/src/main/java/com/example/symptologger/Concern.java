package com.example.symptologger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Concern {
    private String title;
    private Date date;
    private String description;
    private RecordList myRecords;
    private int recordCount;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Concern(){
        this.title = "No title given";
        this.date = new Date();
        this.description = "No description given";
        this.myRecords = new RecordList();
        this.recordCount = 0;
    }

    Concern(String title, Date date, String description){
        this.title = title;
        this.date = date;
        this.description = description;
        this.myRecords = new RecordList();
        this.recordCount = 0;
    }

    Concern(String title, String description){
        this.title = title;
        this.date = new Date();
        this.description = description;
        this.myRecords = new RecordList();
        this.recordCount = 0;
    }

    public String toString(){
        try {
            return getTitle()+"\n"+getDescription()+"\n"+getDate();
        } catch (ParseException e) {
            e.printStackTrace();
            return getTitle()+"\n"+getDescription()+"\n"+sdf.format(this.date);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public Date getDate() throws ParseException {
        return sdf.parse(this.date.toString());
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

    //The below may or may not be needed: have to wait to see how adding photos/geolocations will be handled.

    //A general "record" until we have the specifics of photo, geo-location and comments ironed out.
    public void addRecord(Record record) {
        this.myRecords.addRecord(record);
    }

    public boolean recordListContains(Record record) {
        return this.myRecords.containsRecord(record);
    }

    public void removeRecord(Record record) {
        this.myRecords.deleteRecord(record);
    }

    public int findRecordCount() {
        return this.myRecords.findCount();
    }
}
