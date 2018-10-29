package com.example.symptologger;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ConcernTest {

    @Test // The "@Test" came from https://developer.android.com/training/testing/unit-testing/local-unit-tests#java, 2018-10-27
    public void testGetTitle(){
        String test = "Problem";
        Concern testConcern = new Concern(test, new Date(), null);

        String testTitle = testConcern.getTitle();

        assertEquals(testTitle,test);
    }

    @Test
    public void testSetTitle(){

        Concern testConcern = new Concern();

        String title = "Problem1";
        testConcern.setTitle(title);
        String testTitle = testConcern.getTitle();

        assertEquals(title, testTitle);
    }


    @Test
    public void testGetDate(){

        Date date = new Date();
        String problem = "Problem";
        Concern newConcern = new Concern(problem, date, null);

        Date testDate = newConcern.getDate();

        assertEquals(date, testDate);
    }

    @Test
    public void testSetDate(){
        Concern myConcern = new Concern("Problem2",null, null);

        Date myDate = new Date();

        myConcern.setDate(myDate);

        Date testDate = myConcern.getDate();

        assertEquals(testDate,myDate);
    }

    @Test
    public void testGetDescription(){
        String description = "My description";
        Concern concern = new Concern("abc",new Date(), description);

        String d = concern.getDescription();

        assertEquals(description, d);
    }

    @Test
    public void testSetDescription(){
        String description = "A sample description";

        Concern concern = new Concern();

        String des = concern.getDescription();

        assertNotEquals(des,description);

        concern.setDescription(description);

        String newDes = concern.getDescription();

        assertEquals(description,newDes);
    }

    //To come later once Record and RecordList classes have been tested and developed.

    @Test
    public void testAddRecord(){

    }

    @Test
    public void testRemoveRecord(){

    }

    @Test
    public void testAddPhoto(){
        //When photo functionality is established
    }

    @Test
    public void testAddGeoLocation(){
        //When location services are established
    }
}