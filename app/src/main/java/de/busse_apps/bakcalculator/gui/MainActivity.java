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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import de.busse_apps.bakcalculator.R;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final String SPLASH_FRAGMENT_TAG = "de.busse_apps.bakcalculator.gui.SplashFragment";
    public static final String INPUT_FRAGMENT_TAG = "de.busse_apps.bakcalculator.gui.InputFragment";

    private FragmentManager mFragmentManager;

    private DrawerLayout mDrawerLayout;
    private NavigationDrawerFragment mDrawerFragment;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new MyBackStackListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mDrawerFragment = (NavigationDrawerFragment) mFragmentManager.findFragmentById(R.id.main_fragment_drawer);
        //mDrawerFragment = new NavigationDrawerFragment();
        mDrawerFragment.setUp(R.id.main_fragment_drawer, mDrawerLayout);

        mTitle = getTitle();

        if (savedInstanceState == null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();

            SplashFragment mSplashFragment = new SplashFragment();
            mSplashFragment.setArguments(getIntent().getExtras());
            ft.add(R.id.main_fragment_container, mSplashFragment, SPLASH_FRAGMENT_TAG).commit();

            mDrawerFragment.setHomeAsUp(false);
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

    @Override
    public void onBackPressed() {
        if (mDrawerFragment.isDrawerOpen()) {
            mDrawerFragment.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if ((mFragmentManager.getBackStackEntryCount() > 0) &&
                !(mDrawerFragment.isDrawerOpen())) {
            mFragmentManager.popBackStack();
        }
        return false;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Replace Main Fragments
    }

    protected void openInputFragment() {
        InputFragment mInputFragment = new InputFragment();
        addFragment(mInputFragment, INPUT_FRAGMENT_TAG, null, true);
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
    }

    /**
     * FragmentManager.OnBackStackChangedListener for handling HomeAsUp Button
     */
    private class MyBackStackListener implements FragmentManager.OnBackStackChangedListener {
        @Override
        public void onBackStackChanged() {
            boolean mCanBack = mFragmentManager.getBackStackEntryCount() > 0;
            mDrawerFragment.setHomeAsUp(mCanBack);
        }
    }

}
