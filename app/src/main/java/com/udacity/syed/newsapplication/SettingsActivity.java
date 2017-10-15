package com.udacity.syed.newsapplication;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;

import com.udacity.syed.newsapplication.data.NewsContract;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String[] dValues = new String[]{"default"};
        Set<String> defaultSet = new HashSet<>();
        defaultSet.addAll(Arrays.asList(dValues));
        Set<String> changedValues = sharedPreferences.getStringSet(key, defaultSet);
        Iterator iterator = changedValues.iterator();
        String condition = NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "='" + key + "'";
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(NewsContract.SourceColumns.COLUMN_COLUMN_STATUS, NewsContract.SourceColumns.SOURCE_NOT_SELECTED);
        long rows2 = getContentResolver().update(NewsContract.SourceColumns.CONTENT_URI, contentValues2, condition, null);

        while (iterator.hasNext()) {
            String selection = NewsContract.SourceColumns.COLUMN_SOURCE_ID + "='" + iterator.next() + "'";
            ContentValues contentValues = new ContentValues();
            contentValues.put(NewsContract.SourceColumns.COLUMN_COLUMN_STATUS, NewsContract.SourceColumns.SOURCE_SELECTED);
            long rows = getContentResolver().update(NewsContract.SourceColumns.CONTENT_URI, contentValues, selection, null);
        }
        String condition1 = NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "='" + key + "' AND ";
        String condition2 = NewsContract.SourceColumns.COLUMN_COLUMN_STATUS + "  =  ' " + NewsContract.SourceColumns.SOURCE_SELECTED + "'";
        Cursor cursor = getContentResolver().query(NewsContract.SourceColumns.CONTENT_URI, null, condition1 + condition2, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int count = cursor.getCount();
        if (count != 0) {
            String removeCategory = NewsContract.CategoryColumns.COLUMN_NAME + "='" + key + "'";
            ContentValues removeValues = new ContentValues();
            removeValues.put(NewsContract.CategoryColumns.COLUMN_STATUS, NewsContract.CategoryColumns.CATEGORY_SELECTED);
            getContentResolver().update(NewsContract.CategoryColumns.CONTENT_URI, removeValues, removeCategory, null);
        } else {
            String removeCategory = NewsContract.CategoryColumns.COLUMN_NAME + "='" + key + "'";
            ContentValues removeValues = new ContentValues();
            removeValues.put(NewsContract.CategoryColumns.COLUMN_STATUS, NewsContract.CategoryColumns.CATEGORY_NOT_SELECTED);
            getContentResolver().update(NewsContract.CategoryColumns.CONTENT_URI, removeValues, removeCategory, null);
        }

    }
}
