package de.busse_apps.bakcalculator.gui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import de.busse_apps.bakcalculator.R;
import de.busse_apps.bakcalculator.db.PreferencesContract;
import de.busse_apps.bakcalculator.db.PreferencesHelper;
import de.busse_apps.bakcalculator.widget.AdvancedOnItemSelectedListener;

public class InputFragment extends Fragment {

    private static final String BODY_DIALOG_TAG = "de.busse_apps.bakcalculator.gui.BodyInputPickerFragment";
    private static final String DRINK_DIALOG_TAG = "de.busse_apps.bakcalculator.gui.DrinkInputPickerFragment";

    private BodyInputPickerFragment mBodyDialog;
    private DrinkInputPickerFragment mDrinkDialog;

    private Spinner mSpinnerBody;
    private Spinner mSpinnerDrink;

    private MainActivity mActivity;
    private FragmentManager mFragmentManager;

    private PreferencesHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabaseHelper = new PreferencesHelper(getActivity().getApplicationContext());
        mDatabase = mDatabaseHelper.getReadableDatabase();

        //mDatabaseHelper.addDrink(mDatabase, "POWEEEEEER", 1, 48);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        SimpleCursorAdapter mBodyAdapter = new SimpleCursorAdapter(view.getContext(),
                android.R.layout.simple_spinner_item, mDatabaseHelper.getBodyList(mDatabase), new String[] {PreferencesContract.BodyEntry.COLUMN_NAME_BODY_NAME}, new int[] {android.R.id.text1});
        mBodyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SimpleCursorAdapter mDrinkAdapter = new SimpleCursorAdapter(view.getContext(),
                android.R.layout.simple_spinner_item, mDatabaseHelper.getDrinkList(mDatabase), new String[] {PreferencesContract.DrinkEntry.COLUMN_NAME_DRINK_NAME}, new int[] {android.R.id.text1});
        mDrinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerBody = (Spinner) view.findViewById(R.id.input_spinner_body_details);
        mSpinnerBody.setAdapter(mBodyAdapter);
        mSpinnerBody.setOnItemSelectedListener(new AdvancedOnItemSelectedListener(new BodySelectListener()));

        mSpinnerDrink = (Spinner) view.findViewById(R.id.input_spinner_drink_details);
        mSpinnerDrink.setAdapter(mDrinkAdapter);
        mSpinnerDrink.setOnItemSelectedListener(new AdvancedOnItemSelectedListener(new DrinkSelectListener()));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();

        mFragmentManager = mActivity.getSupportFragmentManager();
        //mBodyFragment = (BodyInputPickerFragment) mFragmentManager.findFragmentByTag(BODY_FRAGMENT_TAG);


        //if (mBodyFragment == null) {
        //    Log.d("BAK", "Create New Body Dialog");
        //    mBodyFragment = new BodyInputPickerFragment();
        //} else {
        //    Log.d("BAK", "USE OLD DIALOG");
        //}
        //mDrinkFragment = new DrinkInputPickerFragment();

        //FragmentTransaction ft = mFragmentManager.beginTransaction();
        //ft.replace(R.id.input_fragment_body_details, mBodyFragment, BODY_FRAGMENT_TAG);
        //ft.replace(R.id.input_fragment_drink_details, mDrinkFragment, DRINK_FRAGMENT_TAG);
        //ft.commit();

        //mButtonCalculate = (Button) getView().findViewById(R.id.input_button_calculate);
        //mButtonCalculate.setOnClickListener(new onButtonCalculateClickListener());

        //mFieldLasttime = (EditText) getView().findViewById(R.id.input_field_lasttime);
        //mFieldMasseaten = (EditText) getView().findViewById(R.id.input_field_masseaten);
        //mFieldBodymass = (EditText) getView().findViewById(R.id.input_field_bodymass);
    }

    @Override
    public void onDestroy() {
        mDatabase.close();

        super.onDestroy();
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
    /**
     * AdapterView.OnItemSelectedListener for Body Details Spinner
     */
    private class BodySelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (id == PreferencesHelper.CUSTOM_ENTRY_ID) {
                new BodyInputPickerFragment().show(mFragmentManager, BODY_DIALOG_TAG);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    /**
     * AdapterView.OnItemSelectedListener for Drink Details Spinner
     */
    private class DrinkSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (id == PreferencesHelper.CUSTOM_ENTRY_ID) {
                new DrinkInputPickerFragment().show(mFragmentManager, DRINK_DIALOG_TAG);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}
