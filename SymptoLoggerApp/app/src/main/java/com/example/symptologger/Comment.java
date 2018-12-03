package com.example.symptologger;

import java.util.Date;

/*  Copyright 2018 Remi Arshad, Noni Hua, Jason Lee, Patrick Tamm, Kaiwen Zhang
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
 * An interface for the comment classes. Provides the blue print for the methods these classes need to
 * implement.
 */
public interface Comment {
    void setDate(Date date);

    Date getDate();

    User getUser();

    //void setUser(User user);

    String getComment();

    void setComment(String comment);
}
