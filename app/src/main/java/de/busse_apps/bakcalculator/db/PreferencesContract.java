package de.busse_apps.bakcalculator.db;

/*
 * Copyright 2015 Bernd Busse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.provider.BaseColumns;

public final class PreferencesContract {

    public PreferencesContract() {}

    public static abstract class BodyEntry implements BaseColumns {
        public static final String TABLE_NAME = "body_details";

        public static final String COLUMN_NAME_BODY_NAME = "name";
        public static final String COLUMN_NAME_SEX = "sex";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_AGE = "age";

        public static final String[] PROJECTION_BODY_LIST= {
            _ID,
            COLUMN_NAME_BODY_NAME,
        };
    }

    public static abstract class DrinkEntry implements BaseColumns {
        public static final String TABLE_NAME = "drink_details";

        public static final String COLUMN_NAME_DRINK_NAME = "name";
        public static final String COLUMN_NAME_VOLUME = "volume";
        public static final String COLUMN_NAME_PERCENT = "percent";

        public static final String[] PROJECTION_DRINK_LIST= {
                _ID,
                COLUMN_NAME_DRINK_NAME,
        };
    }

}
