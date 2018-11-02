package com.example.symptologger;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CareProviderListTest {
    @Test
    public void testAddCareProvider() {
        CareProviderList careProviderList = new CareProviderList();
        CareProvider cp = new CareProvider();

        assertFalse(careProviderList.getCareProvider().contains(cp));

        careProviderList.addCareProviders(cp);
        assertTrue(careProviderList.getCareProviders().contains(cp));
    }

    @Test
    public void testRemoveCareProvider() {
        CareProviderList careProviderList = new CareProviderList();
        CareProvider cp = new CareProvider();

        careProviderList.addCareProviders(cp);
        assertTrue(careProviderList.getCareProviders().contains(cp));

        careProviderList.removeCareProviders(cp);
        assertFalse(careProviderList.getCareProvider().contains(cp));
    }

    @Test
    public void testGetCareProviderList() {
        CareProviderList careProviderList = new CareProviderList();
        CareProvider cp1 = new CareProvider();
        careProviderList.addCareProviders(cp1);
        CareProvider cp2 = new CareProvider();
        careProviderList.addCareProviders(cp2);

        ArrayList<CareProvider> expected = new ArrayList<>();
        expected.add(cp1);
        expected.add(cp2);

        assertEquals(expected, careProviderList.getCareProviders());
    }

    @Test
    public void testGetCareProvidersCount() {
        CareProviderList careProviderList = new CareProviderList();
        CareProvider cp1 = new CareProvider();
        careProviderList.addCareProviders(cp1);
        CareProvider cp2 = new CareProvider();
        careProviderList.addCareProviders(cp2);

        int expected = 2;
        assertEquals(expected, careProviderList.getCareProvidersCount());
    }
}
