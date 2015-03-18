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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import de.busse_apps.bakcalculator.R;

public class MainActivity extends ActionBarActivity {

    public static final String SPLASH_FRAGMENT_TAG = "de.busse_apps.bakcalculator.gui.SplashFragment";
    public static final String INPUT_FRAGMENT_TAG = "de.busse_apps.bakcalculator.gui.InputFragment";

    private FragmentManager mFragmentManager;
    private ActionBar mActionBar;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private boolean homeAsUpEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mActionBar = getSupportActionBar();
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.addOnBackStackChangedListener(new MyBackstackListener());

        mNavigationDrawerFragment = new NavigationDrawerFragment();
        //((NavigationDrawerFragment)mFragmentManager.findFragmentById(R.id.main_fragment_drawer)).setUp();
        //mNavigationDrawerFragment = (NavigationDrawerFragment) mFragmentManager.findFragmentById(R.id.main_fragment_drawer);
        //mTitle = getTitle();

        // Set up the drawer.
        //mNavigationDrawerFragment.setUp(
        //        R.id.main_navigation_drawer,
        //        (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState == null) {
            homeAsUpEnabled = false;
            FragmentTransaction ft = mFragmentManager.beginTransaction();

            SplashFragment mSplashFragment = new SplashFragment();
            mSplashFragment.setArguments(getIntent().getExtras());
            ft.add(R.id.main_fragment_container, mSplashFragment, SPLASH_FRAGMENT_TAG).commit();
        }
//        if (findViewById(R.id.main_fragment_container) != null) {
//            if (savedInstanceState == null) {
//                MainFragment mainFragment = new MainFragment();
//                mainFragment.setArguments(getIntent().getExtras());
//
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.main_fragment_container, mainFragment).commit();
//            }
//        }
    }

//    public void onClickButtonStart(View view) {
//        InputFragment inputFragment = new InputFragment();
//        Bundle args = new Bundle();
//        inputFragment.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.main_fragment_container, inputFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        }
        return false;
    }

    protected void openInputFragment() {
        InputFragment mInputFragment = new InputFragment();
        addFragment(mInputFragment, INPUT_FRAGMENT_TAG, null, true);
    }

    private void setHomeAsUpEnabled(boolean enabled) {
        homeAsUpEnabled = enabled;
        mActionBar.setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        mActionBar.setHomeButtonEnabled(homeAsUpEnabled);
    }

    private void addFragment(Fragment fragment, String tag, Bundle args, boolean toBackStack) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        fragment.setArguments(args);
        ft.replace(R.id.main_fragment_container, fragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (toBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commit();

        if (toBackStack) {
            setHomeAsUpEnabled(true);
        }
    }

    /**
     * FragmentManager.OnBackStackChangedListener for handling HomeAsUp Button
     */
    private class MyBackstackListener implements FragmentManager.OnBackStackChangedListener {
        @Override
        public void onBackStackChanged() {
            boolean canback = mFragmentManager.getBackStackEntryCount() > 0;
            setHomeAsUpEnabled(canback);
        }
    }
}
