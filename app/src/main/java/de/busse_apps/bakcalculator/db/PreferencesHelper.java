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
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.busse_apps.bakcalculator.R;
import de.busse_apps.bakcalculator.db.PreferencesContract.BodyEntry;
import de.busse_apps.bakcalculator.db.PreferencesContract.DrinkEntry;

public class PreferencesHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Preferences.db";

    public static final long CUSTOM_ENTRY_ID = -27;

    private static final String TYPE_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INT = " INTEGER";

    private static final String SQL_CREATE_BODY_ENTRIES =
            "CREATE TABLE " + BodyEntry.TABLE_NAME + " (" +
            BodyEntry._ID + TYPE_PRIMARY_KEY + "," +
            BodyEntry.COLUMN_NAME_BODY_NAME + TYPE_TEXT + "," +
            BodyEntry.COLUMN_NAME_SEX + TYPE_INT + "," +
            BodyEntry.COLUMN_NAME_WEIGHT + TYPE_INT + "," +
            BodyEntry.COLUMN_NAME_HEIGHT + TYPE_INT + "," +
            BodyEntry.COLUMN_NAME_AGE + TYPE_INT +
            " )";
    private static final String SQL_DELETE_BODY_ENTRIES =
            "DROP TABLE IF EXTISTS " + BodyEntry.TABLE_NAME;

    private static final String SQL_CREATE_DRINK_ENTRIES =
            "CREATE TABLE " + DrinkEntry.TABLE_NAME + " (" +
            DrinkEntry._ID + TYPE_PRIMARY_KEY + "," +
            DrinkEntry.COLUMN_NAME_DRINK_NAME + TYPE_TEXT + "," +
            DrinkEntry.COLUMN_NAME_VOLUME + TYPE_INT + "," +
            DrinkEntry.COLUMN_NAME_PERCENT + TYPE_INT +
            " )";
    private static final String SQL_DELETE_DRINK_ENTRIES =
            "DROP TABLE IF EXISTS " + DrinkEntry.TABLE_NAME;

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
        db.execSQL(SQL_DELETE_BODY_ENTRIES);
        db.execSQL(SQL_DELETE_DRINK_ENTRIES);
        onCreate(db);
    }

    public long addBody(SQLiteDatabase db, String name, int sex, int weight, int height, int age) {
        ContentValues values = new ContentValues();
        values.put(BodyEntry.COLUMN_NAME_BODY_NAME, name);
        values.put(BodyEntry.COLUMN_NAME_SEX, sex);
        values.put(BodyEntry.COLUMN_NAME_WEIGHT, weight);
        values.put(BodyEntry.COLUMN_NAME_HEIGHT, height);
        values.put(BodyEntry.COLUMN_NAME_AGE, age);

        return db.insert(BodyEntry.TABLE_NAME, null, values);
    }

    public Cursor getBodyList(SQLiteDatabase db) {
        Cursor result = db.query(BodyEntry.TABLE_NAME,
                BodyEntry.PROJECTION_BODY_LIST,
                null,
                null,
                null,
                null,
                BodyEntry.COLUMN_NAME_BODY_NAME + " DESC");

        MatrixCursor custom = new MatrixCursor(BodyEntry.PROJECTION_BODY_LIST);
        custom.addRow(new Object[] {CUSTOM_ENTRY_ID, mContext.getString(R.string.input_spinner_add_new)});

        return new MergeCursor(new Cursor[] {result, custom});
    }

    public void getBodyById(SQLiteDatabase db, long id) {

    }

    public long addDrink(SQLiteDatabase db, String name, int volume, int percent) {
        ContentValues values = new ContentValues();
        values.put(DrinkEntry.COLUMN_NAME_DRINK_NAME, name);
        values.put(DrinkEntry.COLUMN_NAME_VOLUME, volume);
        values.put(DrinkEntry.COLUMN_NAME_PERCENT, percent);

        return db.insert(DrinkEntry.TABLE_NAME, null, values);
    }

    public Cursor getDrinkList(SQLiteDatabase db) {
        Cursor result = db.query(DrinkEntry.TABLE_NAME,
                DrinkEntry.PROJECTION_DRINK_LIST,
                null,
                null,
                null,
                null,
                DrinkEntry.COLUMN_NAME_DRINK_NAME + " DESC");

        MatrixCursor custom = new MatrixCursor(DrinkEntry.PROJECTION_DRINK_LIST);
        custom.addRow(new Object[] {CUSTOM_ENTRY_ID, mContext.getString(R.string.input_spinner_add_new)});

        return new MergeCursor(new Cursor[] {result, custom});
    }

    public void getDrinkById(SQLiteDatabase db, long id) {

    }

}
