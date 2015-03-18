package de.busse_apps.bakcalculator.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import de.busse_apps.bakcalculator.R;

public class InputFragment extends Fragment {

    private MainActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();

        //mButtonCalculate = (Button) getView().findViewById(R.id.input_button_calculate);
        //mButtonCalculate.setOnClickListener(new onButtonCalculateClickListener());

        //mFieldLasttime = (EditText) getView().findViewById(R.id.input_field_lasttime);
        //mFieldMasseaten = (EditText) getView().findViewById(R.id.input_field_masseaten);
        //mFieldBodymass = (EditText) getView().findViewById(R.id.input_field_bodymass);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        Spinner spinnerSex = (Spinner) getActivity().findViewById(R.id.spinner1);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                getActivity(), R.array.spinner_sex, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSex.setAdapter(adapter);
//
//        super.onActivityCreated(savedInstanceState);
//    }

}
