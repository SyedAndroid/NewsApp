package com.udacity.syed.newsapplication;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.udacity.syed.newsapplication.data.NewsContract;
import com.udacity.syed.newsapplication.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by shoiab on 2017-10-12.
 */

public class RemoteFetchService extends Service {

    public static final String BASE_API = "https://newsapi.org/v1/articles?source=";
    public static final String API = "&apiKey=";
    public static final String KEY = "c99137e2134e412c8e65bad81280ada3";
    public static ArrayList<ListItem> listItemList;
    ArrayList<Article> articles;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private String remoteJsonUrl = "";
    private AQuery aquery;
    private String categoryName;
    private int count;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /*
     * Retrieve appwidget id from intent it is needed to update widget later
     * initialize our AQuery class
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        categoryName = intent.getStringExtra("category");
        aquery = new AQuery(getBaseContext());
        listItemList = new ArrayList<>();

        String selection2 = NewsContract.SourceColumns.COLUMN_COLUMN_STATUS + "= '" + NewsContract.SourceColumns.SOURCE_SELECTED + "'";
        Cursor cursor = getContentResolver().query(NewsContract.SourceColumns.CONTENT_URI, null, selection2, null, null);

        int index = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_SOURCE_ID);
        cursor.moveToFirst();
        count = cursor.getCount();
        for (int i = 0; i < cursor.getCount(); i++) {

            String source = cursor.getString(index);
            cursor.moveToNext();
            remoteJsonUrl = BASE_API + source + API + KEY;
            articles = new ArrayList<>();
            fetchDataFromWeb();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * method which fetches data(json) from web aquery takes params
     * remoteJsonUrl = from where data to be fetched String.class = return
     * format of data once fetched i.e. in which format the fetched data be
     * returned AjaxCallback = class to notify with data once it is fetched
     */
    private void fetchDataFromWeb() {

        aquery.ajax(remoteJsonUrl, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String result, AjaxStatus status) {
                processResult(result);
                super.callback(url, result, status);
            }
        });
    }

    /**
     * Json parsing of result and populating ArrayList<ListItem> as per json
     * data retrieved from the string
     */
    private void processResult(String result) {
        articles = new ArrayList<>();
        articles = JSONUtils.parseArticleJSONString(result);

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            ListItem listItem = new ListItem();
            listItem.heading = article.getTitle();
            listItem.content = article.getAuthor();
            Log.i("Heading", listItem.heading);
            Log.i("Content", listItem.content);
            listItemList.add(listItem);
        }
        count--;
        if (count == 0) {

            populateWidget();
        }
    }

    /**
     * Method which sends broadcast to WidgetProvider
     * so that widget is notified to do necessary action
     * and here action == WidgetProvider.DATA_FETCHED
     */
    private void populateWidget() {

        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(NewAppWidget.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        sendBroadcast(widgetUpdateIntent);

        this.stopSelf();
    }
}
