package com.udacity.syed.newsapplication;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.syed.newsapplication.data.NewsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    public static final String DATA_FETCHED = "com.udacity.syed.newsapplication.DATA_FETCHED";
    private static final String ACTION_SIMPLEAPPWIDGET = "ACTION_BROADCASTWIDGET";
    static int count = 0;
    List<String> category = new ArrayList<>();
    ;

    /*
     * this method is called every 30 mins as specified on widgetinfo.xml
     * this method is also called on every phone reboot
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        /*int[] appWidgetIds holds ids of multiple instance of your widget
		 * meaning you are placing more than one widgets on your homescreen*/
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.new_app_widget);
        String selection = NewsContract.CategoryColumns.COLUMN_STATUS + "=" + NewsContract.CategoryColumns.CATEGORY_SELECTED;
        Uri uri = NewsContract.CategoryColumns.CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        int index = cursor.getColumnIndex(NewsContract.CategoryColumns.COLUMN_NAME);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                category.add(cursor.getString(index));
                cursor.moveToNext();
            }

            cursor.close();
            String hello = "NEWS HEADLINES";
            remoteViews.setTextViewText(R.id.category_title_widget, hello);


            for (int i = 0; i < N; ++i) {
                Intent serviceIntent = new Intent(context, RemoteFetchService.class);
                serviceIntent.putExtra("category", category.get(count));
                serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        appWidgetIds[i]);
                context.startService(serviceIntent);
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.new_app_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
        return remoteViews;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(DATA_FETCHED)) {

            int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);


            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

    }
}
