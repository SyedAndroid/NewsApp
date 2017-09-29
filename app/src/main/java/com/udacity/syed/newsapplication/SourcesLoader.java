package com.udacity.syed.newsapplication;

/**
 * Created by shoiab on 2017-09-16.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.udacity.syed.newsapplication.utils.JSONUtils;
import com.udacity.syed.newsapplication.utils.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by syed on 2017-09-01.
 */

public class SourcesLoader extends AsyncTaskLoader<List<Source>> {

    public static final String BASE_API = "https://newsapi.org/v1/sources";
    String source;

    public SourcesLoader(Context context) {
        super(context);

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Source> loadInBackground() {

        URL url = NetworkUtil.createUrl(BASE_API);
        ArrayList<Source> sources = new ArrayList<>();
        try {
            String response = NetworkUtil.getResponseFromHttpUrl(url);
            sources = JSONUtils.parseSourceJSONString(response);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return sources;
    }
}

