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

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import de.busse_apps.bakcalculator.R;

public class PreferencesHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Preferences.db";

    private static final String TYPE_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INT = " INTEGER";

    private static final String SQL_CREATE_BODY_ENTRIES =
            "CREATE TABLE " + PreferencesContract.BodyEntry.TABLE_NAME + " (" +
            PreferencesContract.BodyEntry._ID + TYPE_PRIMARY_KEY + "," +
            PreferencesContract.BodyEntry.COLUMN_NAME_BODY_NAME + TYPE_TEXT + "," +
            PreferencesContract.BodyEntry.COLUMN_NAME_SEX + TYPE_INT + "," +
            PreferencesContract.BodyEntry.COLUMN_NAME_WEIGHT + TYPE_INT + "," +
            PreferencesContract.BodyEntry.COLUMN_NAME_HEIGHT + TYPE_INT + "," +
            PreferencesContract.BodyEntry.COLUMN_NAME_AGE + TYPE_INT +
            " )";
    private static final String SQL_DELETE_BODY_ENTRIES =
            "DROP TABLE IF EXTISTS " + PreferencesContract.BodyEntry.TABLE_NAME;

    private static final String SQL_CREATE_DRINK_ENTRIES =
            "CREATE TABLE " + PreferencesContract.DrinkEntry.TABLE_NAME + " (" +
            PreferencesContract.DrinkEntry._ID + TYPE_PRIMARY_KEY + "," +
            PreferencesContract.DrinkEntry.COLUMN_NAME_DRINK_NAME + TYPE_TEXT + "," +
            PreferencesContract.DrinkEntry.COLUMN_NAME_VOLUME + TYPE_INT + "," +
            PreferencesContract.DrinkEntry.COLUMN_NAME_PERCENT + TYPE_INT +
            " )";
    private static final String SQL_DELETE_DRINK_ENTRIES =
            "DROP TABLE IF EXISTS " + PreferencesContract.DrinkEntry.TABLE_NAME;

    private Context mContext;

    public PreferencesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BODY_ENTRIES);
        db.execSQL(SQL_CREATE_DRINK_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(PreferencesHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " +
                newVersion + ", which will destroy all old data");
        db.execSQL(SQL_DELETE_BODY_ENTRIES);
        db.execSQL(SQL_DELETE_DRINK_ENTRIES);
        onCreate(db);
    }

//    public long addBody(SQLiteDatabase db, String name, int sex, int weight, int height, int age) {
//        ContentValues values = new ContentValues();
//        values.put(PreferencesContract.BodyEntry.COLUMN_NAME_BODY_NAME, name);
//        values.put(PreferencesContract.BodyEntry.COLUMN_NAME_SEX, sex);
//        values.put(PreferencesContract.BodyEntry.COLUMN_NAME_WEIGHT, weight);
//        values.put(PreferencesContract.BodyEntry.COLUMN_NAME_HEIGHT, height);
//        values.put(PreferencesContract.BodyEntry.COLUMN_NAME_AGE, age);
//
//        return db.insert(PreferencesContract.BodyEntry.TABLE_NAME, null, values);
//    }

    public static CursorLoader getBodyList(Context ctx) {
        return new CursorLoader(ctx,
                PreferencesContract.BodyEntry.CONTENT_URI,
                PreferencesContract.BodyEntry.PROJECTION_BODY_LIST,
                null, null,
                PreferencesContract.BodyEntry.COLUMN_NAME_BODY_NAME + " DESC");
    }

    public static CursorLoader getBodyById(Context ctx, long id) {
        return new CursorLoader(ctx,
                Uri.withAppendedPath(PreferencesContract.BodyEntry.CONTENT_URI, String.valueOf(id)),
                PreferencesContract.BodyEntry.PROJECTION_BODY_LIST,
                null, null, null);
    }

    public static CursorLoader getDrinkList(Context ctx) {
        return new CursorLoader(ctx,
                PreferencesContract.DrinkEntry.CONTENT_URI,
                PreferencesContract.DrinkEntry.PROJECTION_DRINK_LIST,
                null, null,
                PreferencesContract.DrinkEntry.COLUMN_NAME_DRINK_NAME + " DESC");
    }

    public static CursorLoader getDrinkById(Context ctx, long id) {
        return new CursorLoader(ctx,
                Uri.withAppendedPath(PreferencesContract.DrinkEntry.CONTENT_URI, String.valueOf(id)),
                PreferencesContract.DrinkEntry.PROJECTION_DRINK_LIST,
                null, null, null);
    }

//    public long addDrink(SQLiteDatabase db, String name, int volume, int percent) {
//        ContentValues values = new ContentValues();
//        values.put(PreferencesContract.DrinkEntry.COLUMN_NAME_DRINK_NAME, name);
//        values.put(PreferencesContract.DrinkEntry.COLUMN_NAME_VOLUME, volume);
//        values.put(PreferencesContract.DrinkEntry.COLUMN_NAME_PERCENT, percent);
//
//        return db.insert(PreferencesContract.DrinkEntry.TABLE_NAME, null, values);
//    }

}
