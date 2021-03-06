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
 * BackBodyModel extends the BodyModel class. A BackBodyModel object represents the patient from the
 * back.
 *
 * @author Jason Lee
 * @see BodyModel
 */
public class BackBodyModel extends BodyModel {
    private Photograph bodyFrame;
    private User user;
    private ArrayList<Integer> bodyPartsId;

    private ArrayList<Integer> backBodyPartsId;

    public BackBodyModel(User user, int partId){
        super(user,partId);
    }
}
