package com.udacity.syed.newsapplication;

/**
 * Created by shoiab on 2017-09-16.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.syed.newsapplication.data.NewsContract;
import com.udacity.syed.newsapplication.data.NewsProvider;
import com.udacity.syed.newsapplication.utils.JSONUtils;
import com.udacity.syed.newsapplication.utils.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by syed on 2017-09-01.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    public static final String BASE_API = "https://newsapi.org/v1/articles?source=";
    public static final String API = "&apiKey=";
    public static final String KEY = "c99137e2134e412c8e65bad81280ada3";
    String category;

    public ArticleLoader(Context context, String category) {
        super(context);
        this.category = category;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        ArrayList<Article> articlesList = new ArrayList<>();
        String selection = String.format(NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "='" + category + "'");
        Cursor cursor = getContext().getContentResolver().query(NewsProvider.Sources.CONTENT_URI, null, selection, null, null);

        int index = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_SOURCE_ID);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            String source = cursor.getString(index);
            cursor.moveToNext();
            URL url = NetworkUtil.createUrl(BASE_API + source + API + KEY);
            ArrayList<Article> articles = new ArrayList<>();
            try {
                String response = NetworkUtil.getResponseFromHttpUrl(url);
                articles = JSONUtils.parseArticleJSONString(response);
                articlesList.addAll(articles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return articlesList;
    }
}

