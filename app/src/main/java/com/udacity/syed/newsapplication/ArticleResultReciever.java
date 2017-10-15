package com.udacity.syed.newsapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by shoiab on 2017-10-12.
 */

public class ArticleResultReciever extends ResultReceiver {


    private Receiver receiver;

    /**
     * Creates a new ResultReceive to receive results from the intent service. This connects the service and the activity
     */


    public ArticleResultReciever(Handler handler) {
        super(handler);
    }

    // Setter for assigning the receiver
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    // Delegate method which passes the result to the receiver if the receiver has been assigned
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }

    // Defines our event interface for communication
    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}

