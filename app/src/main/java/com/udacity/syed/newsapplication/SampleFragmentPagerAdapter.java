package com.udacity.syed.newsapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by shoiab on 2017-09-11.
 */

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int PAGE_COUNT;

    private ArrayList<String> tabTitles;
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void setTabs(int PAGE_COUNT, ArrayList<String> tabTitles) {
        this.PAGE_COUNT = PAGE_COUNT;
        this.tabTitles = tabTitles;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        PageFragment pageFragment = new PageFragment();
        pageFragment.setCategory(tabTitles.get(position));
        return pageFragment.newInstance(position + 1, tabTitles.get(position));
    }



    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position);
    }
}
