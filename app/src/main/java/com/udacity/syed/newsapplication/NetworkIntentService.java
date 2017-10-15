package com.udacity.syed.newsapplication;


import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.udacity.syed.newsapplication.data.NewsContract;

import cz.msebera.android.httpclient.Header;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class NetworkIntentService extends IntentService {

    public static final String LOG_TAG = "NetworkIntentService";
    public static final String INTENT_URL = "INTENT_URL";
    public static final String BASE_API = "https://newsapi.org/v1/articles?source=";
    public static final String API = "&apiKey=";
    public static final String KEY = "c99137e2134e412c8e65bad81280ada3";
    private final AsyncHttpClient aClient = new SyncHttpClient();
    public String category = "";
    // we use a synchronous call so that the service waits for the download to finish. In case of Async call the service will finish with an error without
    //waiting for async call back


    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(LOG_TAG, "onStart()");
        super.onStart(intent, startId);
    }


    @Override
    protected void onHandleIntent(final Intent intent) {

        category = intent.getStringExtra("category");
        String selection = String.format(NewsContract.SourceColumns.COLUMN_CATEGORY_NAME + "='" + category + "'  AND ");
        String selection2 = NewsContract.SourceColumns.COLUMN_COLUMN_STATUS + "= '" + NewsContract.SourceColumns.SOURCE_SELECTED + "'";
        Cursor cursor = getContentResolver().query(NewsContract.SourceColumns.CONTENT_URI, null, selection + selection2, null, null);

        int index = cursor.getColumnIndex(NewsContract.SourceColumns.COLUMN_SOURCE_ID);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            String source = cursor.getString(index);
            cursor.moveToNext();

            aClient.get(this, BASE_API + source + API + KEY, new AsyncHttpResponseHandler() {


                @Override
                public void onStart() {
                    // called before request is started
                    Log.d(LOG_TAG, "onStart");

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("resultValue", responseBody);
                    resultReceiver.send(Activity.RESULT_OK, bundle);// when we get the results back we notify the reciever and get the data back in our main activity
                    Log.d(LOG_TAG, "onSuccess");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
                    Bundle bundle = new Bundle();
                    bundle.putByteArray("resultValue", responseBody);
                    resultReceiver.send(statusCode, bundle);
                    Log.d(LOG_TAG, "failure");
                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried

                }
            });
        }
    }
}









