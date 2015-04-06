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

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import de.busse_apps.bakcalculator.R;

public class NavigationDrawerFragment extends Fragment {

    private static final String PREFS_DRAWER_LERNED = "de.busse_apps.bakcalculator.gui.NavigationDrawerFragment.drawerLerned";
    private static final String SIS_SELECTED_POSITION = "de.busse_apps.bakcalculator.gui.NavigationDrawerFragment.selectedPosition";
    private static final String SIS_HOME_AS_UP = "de.busse_apps.bakcalculator.gui.NavigationDrawerFragment.homeAsUp";

    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private View mFragmentContainerView;

    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;

    private boolean mFromSavedInstanceState;
    private int mSelectedPosition = 0;
    private boolean mHomeAsUp = false;
    private boolean mDrawerLerned;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mDrawerLerned = sp.getBoolean(PREFS_DRAWER_LERNED, false);

        if (savedInstanceState != null) {
            mSelectedPosition = savedInstanceState.getInt(SIS_SELECTED_POSITION, 0);
            mHomeAsUp = savedInstanceState.getBoolean(SIS_HOME_AS_UP, false);
            mFromSavedInstanceState = true;
        }

        selectItem(mSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[] {"Power1", "Powerline", "COOOOOOOOOL STUFF"});

        mListView.setOnItemClickListener(new OnSelectItemListener());
        mListView.setAdapter(mAdapter);
        mListView.setItemChecked(mSelectedPosition, true);

        return mListView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SIS_SELECTED_POSITION, mSelectedPosition);
        outState.putBoolean(SIS_HOME_AS_UP, mHomeAsUp);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new MyActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.string.drawer_open_desc,
                R.string.drawer_close_desc);

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mDrawerLerned && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if (!isDrawerOpen()) {
                    disableIndicator(mHomeAsUp);
                }
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void setHomeAsUp(boolean enabled) {
        mHomeAsUp = enabled;
        disableIndicator(mHomeAsUp);
    }

    private void disableIndicator(boolean enabled) {
        if (mDrawerToggle != null) {
            mDrawerToggle.setDrawerIndicatorEnabled(!enabled);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    private void selectItem(int position) {
        mSelectedPosition = position;
        if (mListView != null) {
            mListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * ActionBarDrawerToggle for handling Drawer State Changes
     */
    private class MyActionBarDrawerToggle extends ActionBarDrawerToggle {

        public MyActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);

            disableIndicator(mHomeAsUp);
        }
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);

            disableIndicator(false);
        }
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);

            if (!mDrawerLerned) {
                // The user manually opened the drawer; store this flag to prevent auto-showing
                // the navigation drawer automatically in the future.
                mDrawerLerned = true;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sp.edit().putBoolean(PREFS_DRAWER_LERNED, true).commit();
            }
            disableIndicator(false);
        }
    }

    private class OnSelectItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }

}
