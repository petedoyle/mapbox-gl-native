package com.mapbox.mapboxgl.metrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mapbox.mapboxgl.utils.Utils;

public class EventManager {

    static final String TAG = "EventManager";

    static final int VERSION = 1;

    private static EventManager mInstance = null;

    private Context mContext;
    private String mAccessToken;

    public EventManager(@NonNull Context context, @NonNull String accessToken) {
        mContext = context;
        Utils.validateAccessToken(accessToken);
        mAccessToken = accessToken;

        // Register the status receiver
        LocalBroadcastManager.getInstance(context).registerReceiver(new StatusReceiver(), new IntentFilter(EventService.ACTION_EVENT_STATUS));
    }

    /*public static EventManager getInstance() {
        if (mInstance == null) {
            mInstance = new EventManager();
        }

        return mInstance;
    }*/

    public void pauseMetricsCollection() {

    }

    public void resumeMetricsCollection() {

    }

    public void pushEvent(@NonNull Event event) {
        String data = "foobar";
        Log.v(TAG, "sending event: " + data);
        Intent intent = new Intent(mContext, EventService.class);
        intent.putExtra(EventService.EXTRA_ACCESS_TOKEN, mAccessToken);
        intent.putExtra(EventService.EXTRA_EVENT_DATA, data);
        mContext.startService(intent);
    }

    public boolean isPushEnabled() {
        return true;
    }

    public void flush() {

    }

    public void validate() {

    }

    private class StatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra(EventService.EXTRA_EVENT_STATUS);
            int code = intent.getIntExtra(EventService.EXTRA_CODE, -1);
            String message = intent.getStringExtra(EventService.EXTRA_MESSAGE);
            String responseData = intent.getStringExtra(EventService.EXTRA_RESPONSE_DATA);
            Log.v(TAG, "status received: status = " + status + ", code = " + code + ", message = " + message + ", response = " + responseData);
        }
    }
}
