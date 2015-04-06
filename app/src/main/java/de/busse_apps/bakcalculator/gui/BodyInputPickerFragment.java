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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import de.busse_apps.bakcalculator.R;
import de.busse_apps.bakcalculator.widget.AdvancedOnItemSelectedListener;

public class BodyInputPickerFragment extends DialogFragment {

    public static final String SIS_SEX = "de.busse_apps.bakcalculator.BodyFragment.sex";

    private int mSex;

    private Spinner mSpinnerSex;
    private ArrayAdapter<CharSequence> mAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_body_input, null);

        mAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.body_input_spinner_sex, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerSex = (Spinner) view.findViewById(R.id.body_input_spinner_sex);
        mSpinnerSex.setAdapter(mAdapter);
        mSpinnerSex.setOnItemSelectedListener(new AdvancedOnItemSelectedListener(new SexSelectListener()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.body_input_header_new);
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_button_save, new OnButtonSaveClickListener());
        builder.setNegativeButton(R.string.dialog_button_cancel, new OnButtonCancelClickListener());

        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSex = savedInstanceState.getInt(SIS_SEX, 0);
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SIS_SEX, mSex);

        super.onSaveInstanceState(outState);
    }

    /**
     * AdapterView.OnItemSelectedListener for Sex Spinner
     */
    private class SexSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            mSex = pos;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
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
