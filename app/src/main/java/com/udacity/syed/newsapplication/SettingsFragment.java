package com.udacity.syed.newsapplication;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v4.app.Fragment;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.udacity.syed.newsapplication.data.NewsContract;
import com.udacity.syed.newsapplication.data.NewsProvider;


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

        SwitchPreferenceCompat businessSwitch = (SwitchPreferenceCompat)  getPreferenceManager().findPreference("business");
        SwitchPreferenceCompat  enterSwitch = (SwitchPreferenceCompat)  getPreferenceManager().findPreference("entertainment");
        SwitchPreferenceCompat  gamingSwitch= (SwitchPreferenceCompat)  getPreferenceManager().findPreference("gaming");
        SwitchPreferenceCompat politicsSwitch = (SwitchPreferenceCompat)  getPreferenceManager().findPreference("politics");
        SwitchPreferenceCompat sportsSwitch= (SwitchPreferenceCompat)  getPreferenceManager().findPreference("sports" );
        SwitchPreferenceCompat  musicSwitch= (SwitchPreferenceCompat)  getPreferenceManager().findPreference("music");
        SwitchPreferenceCompat generalSwitch= (SwitchPreferenceCompat)  getPreferenceManager().findPreference("general");
        SwitchPreferenceCompat  scienceSwitch= (SwitchPreferenceCompat)  getPreferenceManager().findPreference("science");
        SwitchPreferenceCompat techSwitch= (SwitchPreferenceCompat)  getPreferenceManager().findPreference("technology");

        String selection = NewsContract.CategoryColumns.COLUMN_STATUS +"=1";
        Cursor cursor= getContext().getContentResolver().query(NewsProvider.Categories.CONTENT_URI,null,selection,null,null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(NewsContract.CategoryColumns.COLUMN_NAME);
        for(int i=0;i<cursor.getCount();i++){
            String selectedCategory = cursor.getString(index);
            switch (selectedCategory) {
                case "business": businessSwitch.setChecked(true); break;
                case "entertainment":enterSwitch.setChecked(true); break;
                case  "gaming": gamingSwitch.setChecked(true); break;
                case "politics":politicsSwitch.setChecked(true);break;
                case "sports": sportsSwitch.setChecked(true); break;
                case "music": musicSwitch.setChecked(true); break;
                case "general": generalSwitch.setChecked(true); break;
                case "science": scienceSwitch.setChecked(true); break;
                case "technology":techSwitch.setChecked(true); break;
                default: break;
            }
            cursor.moveToNext();
        }

    }


}
