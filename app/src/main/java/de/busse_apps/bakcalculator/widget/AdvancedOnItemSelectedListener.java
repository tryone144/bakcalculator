package de.busse_apps.bakcalculator.widget;

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

import android.view.View;
import android.widget.AdapterView;

public class AdvancedOnItemSelectedListener  implements AdapterView.OnItemSelectedListener {

    private int pLastPosition;
    private AdapterView.OnItemSelectedListener pListener;

    public AdvancedOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        pLastPosition = 0;
        pListener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (pLastPosition != pos) {
            pListener.onItemSelected(parent, view, pos, id);
        }
        pLastPosition = pos;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        pListener.onNothingSelected(parent);
    }
}
