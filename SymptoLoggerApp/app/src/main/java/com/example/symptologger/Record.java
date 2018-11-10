package com.example.symptologger;

import java.util.Date;

class Record {
    //private String comment;
    private Date date;

    public Record(){
        //this.comment = "";
        this.date = new Date();
    }

    public Record(String comment, Date date) {
      //  this.comment = comment;
        this.date = date;
    }

    //public String getComment() {
      //  return this.comment;
    //}

    //public void setComment(String comment) {
      //  this.comment = comment;
    //}

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addPhoto(){
        //When photo functionality established
    }

    public void addGeoLocation(){
        //When location services established and enabled
    }

    public void addCareProviderComment(){
        //When CareProviderComment is ready
    }

    public void addPatientComment(){
        //When patient comment is ready
    }
}
