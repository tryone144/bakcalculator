package de.busse_apps.bakcalculator.db;

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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class PreferencesProvider extends ContentProvider {

    public static final String AUTHORITY = "de.busse_apps.bakcalculator.db.prefs";

    private static final int BODYS = 1;
    private static final int BODY_ID = 11;

    private static final int DRINKS = 2;
    private static final int DRINK_ID = 21;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, PreferencesContract.BodyEntry.BASE_PATH, BODYS);
        sUriMatcher.addURI(AUTHORITY, PreferencesContract.BodyEntry.BASE_PATH + "/#", BODY_ID);

        sUriMatcher.addURI(AUTHORITY, PreferencesContract.DrinkEntry.BASE_PATH, DRINKS);
        sUriMatcher.addURI(AUTHORITY, PreferencesContract.DrinkEntry.BASE_PATH + "/#", DRINK_ID);
    }

    private PreferencesHelper mPreferencesHelper;

    @Override
    public boolean onCreate() {
        mPreferencesHelper = new PreferencesHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mPreferencesHelper.getReadableDatabase();
        String tableName = null;

        switch (sUriMatcher.match(uri)) {
            case BODYS:
                tableName = PreferencesContract.BodyEntry.TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "_ID ASC";
                }
                break;
            case BODY_ID:
                tableName = PreferencesContract.BodyEntry.TABLE_NAME;
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;
            case DRINKS:
                tableName = PreferencesContract.DrinkEntry.TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "_ID ASC";
                }
                break;
            case DRINK_ID:
                tableName = PreferencesContract.DrinkEntry.TABLE_NAME;
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Can't process URI: " + uri.toString());
        }

        return db.query(tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
        //return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
        //return 0;
    }

}
