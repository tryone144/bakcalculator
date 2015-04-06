package de.busse_apps.bakcalculator.gui;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import de.busse_apps.bakcalculator.R;
import de.busse_apps.bakcalculator.db.PreferencesContract;
import de.busse_apps.bakcalculator.db.PreferencesHelper;
import de.busse_apps.bakcalculator.widget.AdvancedOnItemSelectedListener;

public class InputFragment extends Fragment implements BodyInputPickerFragment.ResultListener {

    private static final String BODY_DIALOG_TAG = "de.busse_apps.bakcalculator.gui.BodyInputPickerFragment";
    private static final String DRINK_DIALOG_TAG = "de.busse_apps.bakcalculator.gui.DrinkInputPickerFragment";

    private static final int BODY_LIST_LOADER_ID = 1;
    private static final int DRINK_LIST_LOADER_ID = 2;

    private static final long CUSTOM_ENTRY_ID = -27;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private PreferencesLoaderCallbacks mLoaderCallbacks;

    private SimpleCursorAdapter mBodyAdapter;
    private SimpleCursorAdapter mDrinkAdapter;

    private Spinner mSpinnerBody;
    private Spinner mSpinnerDrink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();

        mBodyAdapter = new SimpleCursorAdapter(mContext,
                android.R.layout.simple_spinner_item,
                null,
                new String[] {PreferencesContract.BodyEntry.COLUMN_NAME_BODY_NAME},
                new int[] {android.R.id.text1},
                0);
        mBodyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mDrinkAdapter = new SimpleCursorAdapter(mContext,
                android.R.layout.simple_spinner_item,
                null,
                new String[] {PreferencesContract.DrinkEntry.COLUMN_NAME_DRINK_NAME},
                new int[] {android.R.id.text1},
                0);
        mDrinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        LoaderManager mLoaderManager = getLoaderManager();
        mLoaderCallbacks = new PreferencesLoaderCallbacks();
        mLoaderManager.initLoader(BODY_LIST_LOADER_ID, null, mLoaderCallbacks);
        mLoaderManager.initLoader(DRINK_LIST_LOADER_ID, null, mLoaderCallbacks);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmentManager = getChildFragmentManager();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        mSpinnerBody = (Spinner) view.findViewById(R.id.input_spinner_body_details);
        mSpinnerBody.setAdapter(mBodyAdapter);
        mSpinnerBody.setOnItemSelectedListener(new AdvancedOnItemSelectedListener(new BodySelectListener()));

        mSpinnerDrink = (Spinner) view.findViewById(R.id.input_spinner_drink_details);
        mSpinnerDrink.setAdapter(mDrinkAdapter);
        mSpinnerDrink.setOnItemSelectedListener(new AdvancedOnItemSelectedListener(new DrinkSelectListener()));

        return view;
    }

    @Override
    public void onBodyInputPickerButtonSave(int mode, String name, int sex, int weight, int height, int age) {

    }

    @Override
    public void onBodyInputPickerButtonCancel(int mode) {
        switch (mode) {
            case BodyInputPickerFragment.MODE_CUSTOM:
                // Select last Spinner entry
                break;
        }
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
     * LoaderManager.LoaderCallbacks for Body Details Spinner
     */
    private class PreferencesLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            switch (id) {
                case BODY_LIST_LOADER_ID:
                    return PreferencesHelper.getBodyList(mContext);
                case DRINK_LIST_LOADER_ID:
                    return PreferencesHelper.getDrinkList(mContext);
                default:
                    return null;
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            MatrixCursor custom;
            MergeCursor result;

            switch (loader.getId()) {
                case BODY_LIST_LOADER_ID:
                    custom = new MatrixCursor(PreferencesContract.BodyEntry.PROJECTION_BODY_LIST);
                    custom.addRow(new Object[] {CUSTOM_ENTRY_ID, mContext.getString(R.string.input_spinner_custom)});

                    result = new MergeCursor(new Cursor[] {data, custom});
                    mBodyAdapter.swapCursor(result);
                    break;
                case DRINK_LIST_LOADER_ID:
                    custom = new MatrixCursor(PreferencesContract.DrinkEntry.PROJECTION_DRINK_LIST);
                    custom.addRow(new Object[] {CUSTOM_ENTRY_ID, mContext.getString(R.string.input_spinner_custom)});

                    result = new MergeCursor(new Cursor[] {data, custom});
                    mDrinkAdapter.swapCursor(result);
                    break;
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            switch (loader.getId()) {
                case BODY_LIST_LOADER_ID:
                    mBodyAdapter.swapCursor(null);
                case DRINK_LIST_LOADER_ID:
                    mDrinkAdapter.swapCursor(null);
            }
        }
    }

    /**
     * AdapterView.OnItemSelectedListener for Body Details Spinner
     */
    private class BodySelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (id == CUSTOM_ENTRY_ID) {
                new BodyInputPickerFragment().show(mFragmentManager, BODY_DIALOG_TAG);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    /**
     * AdapterView.OnItemSelectedListener for Drink Details Spinner
     */
    private class DrinkSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (id == CUSTOM_ENTRY_ID) {
                new DrinkInputPickerFragment().show(mFragmentManager, DRINK_DIALOG_TAG);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

}
