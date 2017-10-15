package com.udacity.syed.newsapplication;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;

import com.udacity.syed.newsapplication.data.NewsContract;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private SwitchPreferenceCompat businessSwitch;
    private SwitchPreferenceCompat enterSwitch;
    private SwitchPreferenceCompat gamingSwitch;
    private SwitchPreferenceCompat politicsSwitch;
    private SwitchPreferenceCompat sportsSwitch;
    private SwitchPreferenceCompat musicSwitch;
    private SwitchPreferenceCompat generalSwitch;
    private SwitchPreferenceCompat scienceSwitch;
    private SwitchPreferenceCompat techSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_category);

        final MultiSelectListPreference businessPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_business));
        final MultiSelectListPreference entertainmentPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_entertainment));
        final MultiSelectListPreference gamingPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_gaming));
        final MultiSelectListPreference politicsPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_politics));
        final MultiSelectListPreference sportsPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_sports));
        final MultiSelectListPreference musicPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_music));
        final MultiSelectListPreference sciencePreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_science));
        final MultiSelectListPreference technologyPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_technology));
        final MultiSelectListPreference generalPreference = (MultiSelectListPreference) getPreferenceManager().findPreference(getString(R.string.pref_category_value_general));

        businessPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("business", businessPreference);
                return false;
            }
        });
        entertainmentPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("entertainment", entertainmentPreference);
                return false;
            }
        });
        gamingPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("gaming", gamingPreference);
                return false;
            }
        });
        politicsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("politics", politicsPreference);
                return false;
            }
        });
        sportsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("sport", sportsPreference);
                return false;
            }
        });
        musicPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("music", musicPreference);
                return false;
            }
        });
        sciencePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("science-and-nature", sciencePreference);
                return false;
            }
        });
        technologyPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("technology", technologyPreference);
                return false;
            }
        });
        generalPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                setListPreference("general", generalPreference);
                return false;
            }
        });

    }

    public void setListPreference(String category, MultiSelectListPreference list) {
        String selection = NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "='" + category + "'";
        Cursor cursor = getContext().getContentResolver().query(NewsContract.SourceColumns.CONTENT_URI, null, selection, null, null);
        cursor.moveToFirst();
        int indexName = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_SOURCE_NAME);
        int indexID = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_SOURCE_ID);
        int indexStatus = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_COLUMN_STATUS);
        String[] entriesValues = new String[cursor.getCount()];
        String[] entries = new String[cursor.getCount()];
        int j = 0;
        String[] defaultValues = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            entries[i] = cursor.getString(indexName);
            entriesValues[i] = cursor.getString(indexID);
            if (cursor.getInt(indexStatus) == 2) {

                defaultValues[j] = cursor.getString(indexID);
                j++;
            }
            cursor.moveToNext();
        }
        list.setEntries(entries);
        list.setEntryValues(entriesValues);

        Set<String> b = new HashSet<>();
        b.addAll(Arrays.asList(defaultValues));
        if (!b.isEmpty()) {
            list.setValues(b);
        }
        cursor.close();
    }

}
