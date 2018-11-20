package com.example.symptologger;

import java.util.ArrayList;

/*
 *  Copyright 2018 Remi Arshad, Noni Hua, Jason Lee, Patrick Tamm, Kaiwen Zhang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * Controller for list of care providers
 */
public class CareProviderList {
    private ArrayList<CareProvider> careProviders;

    /**
     * The constructor. Sets a new ArrayList
     */

    public CareProviderList() {
        this.careProviders = new ArrayList<>();
    }

    /**
     * Adds a new care provider to the list.
     * @param careProvider the new care provider
     */

    public void addCareProviders(CareProvider careProvider) {
        this.careProviders.add(careProvider);
    }

    /**
     * Removes a care provider
     * @param careProvider the care provider to be removed
     */

    public void removeCareProviders(CareProvider careProvider) {
        this.careProviders.remove(careProvider);
    }

    /**
     * Returns the number of care providers in the list.
     * @return number of care providers
     */

    public int getCareProvidersCount() {
        return this.careProviders.size();
    }

    /**
     * Gets the care providers
     * @return careProviders
     */

    public ArrayList<CareProvider> getCareProviders() {
        return this.careProviders;
    }
}
