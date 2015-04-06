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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import de.busse_apps.bakcalculator.R;

public class BodyInputPickerFragment extends DialogFragment {

    public static final int MODE_CUSTOM = 0;
    public static final int MODE_EDIT = 1;
    public static final int MODE_NEW = 2;

    private int mSex;

    private Spinner mSpinnerSex;
    private ArrayAdapter<CharSequence> mAdapter;

    private ResultListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment parent = getParentFragment();
        try {
            mListener = (ResultListener) parent;
        } catch (ClassCastException e) {
            throw new ClassCastException(parent.toString() + " must implement BodyInputPickerFragment.ResultListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_body_input, null);

        //ViewGroup parent = (ViewGroup) view.findViewById(R.id.drink_input_container_name);
        //inflater.inflate(R.layout.fragment_input_dialog_name, parent, true);

        mAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.body_input_spinner_sex, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerSex = (Spinner) view.findViewById(R.id.body_input_spinner_sex);
        mSpinnerSex.setAdapter(mAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.body_input_header_new);
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_button_save, new OnButtonSaveClickListener());
        builder.setNegativeButton(R.string.dialog_button_cancel, new OnButtonCancelClickListener());

        return builder.create();
    }

    /**
     * DialogInterface.OnClickListener for Save Button
     */
    private class OnButtonSaveClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mListener.onBodyInputPickerButtonSave(MODE_CUSTOM, "POWERLINE", 0, 0, 0, 0);
        }
    }
    /**
     * DialogInterface.OnClickListener for Cancel Button
     */
    private class OnButtonCancelClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mListener.onBodyInputPickerButtonCancel(MODE_CUSTOM);
        }
    }

    /**
     * Callbacks interface that all parent fragments using this dialog must implement.
     */
    public interface ResultListener {
        public void onBodyInputPickerButtonSave(int mode, String name, int sex, int weight, int height, int age);
        public void onBodyInputPickerButtonCancel(int mode);
    }

}
