package com.udacity.syed.newsapplication;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;

import com.udacity.syed.newsapplication.data.NewsContract;
import com.udacity.syed.newsapplication.data.NewsProvider;

public class SettingsActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener
{

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
        ContentValues contentValues = new ContentValues();
        int status= NewsContract.CategoryColumns.CATEGORY_NOT_SELECTED;
        if (sharedPreferences.getBoolean(key,false)){
            status=NewsContract.CategoryColumns.CATEGORY_SELECTED;
        }
        contentValues.put(NewsContract.CategoryColumns.COLUMN_STATUS,status);
        if(!sharedPreferences.getBoolean(key,false)) {
            String selection = NewsContract.CategoryColumns.COLUMN_NAME + "= '" + key + "'";
            int i = getContentResolver().update(NewsProvider.Categories.CONTENT_URI, contentValues, selection, null);
            String where = NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "= '" + key + "'";
            int j = getContentResolver().delete(NewsProvider.Sources.CONTENT_URI, where, null);
        }
        if(sharedPreferences.getBoolean(key,false)){

        }
    }
}
