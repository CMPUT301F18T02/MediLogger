package com.example.symptologger;

import android.icu.text.AlphabeticIndex;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public class RecordTest {

    @Test
    public void testGetComment(){
        String newComment = "I am a record comment";

        Record testRecord = new Record(newComment, new Date());

        String test = testRecord.getComment();

        assertEquals(newComment,test);
    }

    @Test
    public void testSetComment(){
        String newComment = "Testing set comment";

        Record record = new Record();

        assertNotEquals(newComment, record.getComment());

        record.setComment(newComment);

        assertEquals(newComment,record.getComment());
    }

    @Test
    public void testGetDate(){
        Date date = new Date();
        Record record = new Record("",date);
        assertEquals(date,record.getDate());
    }

    @Test
    public void testSetDate(){
        Record myRecord = new Record("",new Date());

        Date myDate = new Date();

        myRecord.setDate(myDate);

        Date testDate = myRecord.getDate();

        assertEquals(testDate,myDate);
    }



}