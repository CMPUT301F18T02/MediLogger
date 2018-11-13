package com.example.symptologger;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatientTest {
    @Test
    public void testCreateValidUserID() {
        Patient patient = new Patient("testCarePatient", "First", "Last",
                "test@test.com", "123456789");
        String expected = "validName";
        patient.createUserID(expected);

        assertEquals(expected, patient.getId());
    }

    @Test
    public void testCreateInvalidUserID() {
        Patient patient = new Patient("testCarePatient", "First", "Last",
                "test@test.com", "123456789");
        boolean thrown = false;
        String expected = "short";

        try {
            patient.createUserID(expected);
        } catch(RuntimeException e) {
            thrown = true;
        }

        assertTrue(thrown);

    }

    @Test
    public void testGetConcerns() {
        Patient patient = new Patient("testCarePatient", "First", "Last",
                "test@test.com", "123456789");
        Concern concern1 = new Concern();
        Concern concern2 = new Concern();

        patient.addConcern(concern1);
        patient.addConcern(concern2);

        ArrayList<Concern> expected = new ArrayList<>();
        expected.add(concern1);
        expected.add(concern2);

        assertEquals(expected, patient.getConcerns());

    }

    @Test
    public void testAddConcern() {
        Patient patient = new Patient("testCarePatient", "First", "Last",
                "test@test.com", "123456789");
        Concern concern = new Concern();

        assertFalse(patient.getConcerns().contains(concern));

        patient.addConcern(concern);
        assertTrue(patient.getConcerns().contains(concern));
    }

    @Test
    public void testUpdateConcern() {
        Patient patient = new Patient("testCarePatient", "First", "Last",
                "test@test.com", "123456789");
        Concern concern = new Concern();
        patient.addConcern(concern);

        Concern concernUpdated = new Concern();
        concernUpdated.setDate(new Date());
        patient.updateConcern(concernUpdated);

        assertFalse(patient.getConcerns().contains(concern));
    }

    @Test
    public void testDeleteConcern() {
        Patient patient = new Patient("testCarePatient", "First", "Last",
                "test@test.com", "123456789");
        Concern concern = new Concern();

        assertFalse(patient.getConcerns().contains(concern));

        patient.addConcern(concern);
        assertTrue(patient.getConcerns().contains(concern));
    }
}
