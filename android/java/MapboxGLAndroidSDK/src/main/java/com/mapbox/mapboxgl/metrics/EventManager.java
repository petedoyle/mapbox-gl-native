package com.mapbox.mapboxgl.metrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class EventManager {

    static final String TAG = "EventManager";

    static final int VERSION = 1;
    static final String USER_AGENT = "MapboxEventsAndroid/1.0";
    static final String EVENTS_API_BASE = "https://api.tiles.mapbox.com";

    private static EventManager mInstance = null;

    private Context mContext;

    public EventManager(Context context) {
        mContext = context;

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

    public void pushEvent(Event event) {
        String data = "foobar";
        Log.v(TAG, "sending event: " + data);
        Intent intent = new Intent(mContext, EventService.class);
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
            Log.v(TAG, "status received: " + status);
        }
    }
}
