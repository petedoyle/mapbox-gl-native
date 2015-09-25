package com.mapbox.mapboxgl.metrics;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class EventService extends IntentService {

    private static final String TAG = "EventService";

    public static final String ACTION_EVENT_STATUS = "com.mapbox.mapboxgl.metrics.EventService.ACTION_EVENT_STATUS";

    public static final String EXTRA_EVENT_DATA = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_EVENT_DATA";
    public static final String EXTRA_ACCESS_TOKEN = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_ACCESS_TOKEN";

    public static final String EXTRA_EVENT_STATUS = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_EVENT_STATUS";
    public static final String EXTRA_MESSAGE = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_MESSAGE";
    public static final String EXTRA_RESPONSE_DATA = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_RESPONSE_DATA";
    public static final String EXTRA_CODE = "com.mapbox.mapboxgl.metrics.EventService.EXTRA_CODE";

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED_IO = "FAILED_IO";
    public static final String STATUS_FAILED_HTTP = "FAILED_HTTP";

    static final String USER_AGENT = "MapboxEventsAndroid/1.0";
    static final String EVENTS_API_BASE = "https://api.tiles.mapbox.com";

    private OkHttpClient mHttpClient;

    public EventService() {
        super(TAG);

        mHttpClient = new OkHttpClient();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String data = intent.getStringExtra(EXTRA_EVENT_DATA);
        String accessToken = intent.getStringExtra(EXTRA_ACCESS_TOKEN);
        Log.v(TAG, "event received: access token = " + accessToken + ", data = " + data);

        // Post the data to the server
        String url = EVENTS_API_BASE + "/events/v1?access_token=" + accessToken;
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder().url(url).post(body).build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "Event post failed", e);
                String status = STATUS_FAILED_IO;

                // Send event post status
                Intent statusIntent = new Intent(ACTION_EVENT_STATUS);
                statusIntent.putExtra(EXTRA_EVENT_STATUS, status);
                LocalBroadcastManager.getInstance(EventService.this).sendBroadcast(statusIntent);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String status = response.isSuccessful() ? STATUS_SUCCESS : STATUS_FAILED_HTTP;
                int code = response.code();
                String message = response.message();
                String responseData = response.body().string();

                // Send event post status
                Intent statusIntent = new Intent(ACTION_EVENT_STATUS);
                statusIntent.putExtra(EXTRA_EVENT_STATUS, status);
                statusIntent.putExtra(EXTRA_CODE, code);
                statusIntent.putExtra(EXTRA_MESSAGE, message);
                statusIntent.putExtra(EXTRA_RESPONSE_DATA, responseData);
                LocalBroadcastManager.getInstance(EventService.this).sendBroadcast(statusIntent);
            }
        });

    }
}
