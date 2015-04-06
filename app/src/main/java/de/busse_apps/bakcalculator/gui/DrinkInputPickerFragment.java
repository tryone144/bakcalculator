package de.busse_apps.bakcalculator.gui;

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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewGroupCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import de.busse_apps.bakcalculator.R;

public class DrinkInputPickerFragment extends DialogFragment {

    //public static final String SIS_SEX = "de.busse_apps.bakcalculator.BodyFragment.sex";

    //private int mSex;

    private ArrayAdapter<CharSequence> mAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_drink_input, null);

        //ViewGroup parent = (ViewGroup) view.findViewById(R.id.drink_input_container_name);
        //inflater.inflate(R.layout.fragment_input_dialog_name, parent, true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.drink_input_header_new);
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_button_save, new OnButtonSaveClickListener());
        builder.setNegativeButton(R.string.dialog_button_cancel, new OnButtonCancelClickListener());

        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //mSex = savedInstanceState.getInt(SIS_SEX, 0);
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putInt(SIS_SEX, mSex);

        super.onSaveInstanceState(outState);
    }

    /**
     * DialogInterface.OnClickListener for Save Button
     */
    private class OnButtonSaveClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {}
    }
    /**
     * DialogInterface.OnClickListener for Cancel Button
     */
    private class OnButtonCancelClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {}
    }
}
