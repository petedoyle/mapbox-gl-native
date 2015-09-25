package com.mapbox.mapboxgl.metrics;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

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
    static final String EVENTS_API_SERVER = "api.tiles.mapbox.com";
    static final String EVENTS_API_TEST_SERVER = "cloudfront-staging.tilestream.net";

    static final String CERTIFICATE_GEOTRUST = "api_mapbox_com-digicert.der";
    static final String CERTIFICATE_DIGICERT = "api_mapbox_com-geotrust.der";
    static final String CERTIFICATE_TEST = "star_tilestream_net.der";

    private OkHttpClient mHttpClient;

    private boolean mUseTestServer = false;

    public EventService() {
        super(TAG);

        mUseTestServer = true;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String data = intent.getStringExtra(EXTRA_EVENT_DATA);
        String accessToken = intent.getStringExtra(EXTRA_ACCESS_TOKEN);
        Log.v(TAG, "event received: access token = " + accessToken + ", data = " + data);

        // Create HTTP client with strict HTTPS requirements and certificate pinning
        if (mHttpClient == null) {
            mHttpClient = new OkHttpClient();
            mHttpClient.setConnectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS));
            ArrayList<Certificate> certificates = new ArrayList<>();
            if (mUseTestServer) {
                Log.v(TAG, "Using test event API server!");
                certificates.add(loadCertificateFromAsset(CERTIFICATE_TEST));
            } else {
                certificates.add(loadCertificateFromAsset(CERTIFICATE_DIGICERT));
                certificates.add(loadCertificateFromAsset(CERTIFICATE_GEOTRUST));
            }
            SSLContext sslContext = sslContextForTrustedCertificates(certificates);
            mHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        }

        // Post the data to the server
        String server = mUseTestServer ? EVENTS_API_TEST_SERVER : EVENTS_API_SERVER;
        String url = "https://" + server + "/events/v1?access_token=" + accessToken;
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder().addHeader("User-Agent", USER_AGENT).url(url).post(body).build();
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

    private Certificate loadCertificateFromAsset(String fileName) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream in = getAssets().open(fileName);
            Certificate certificate = certificateFactory.generateCertificate(in);
            in.close();
            return certificate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    private SSLContext sslContextForTrustedCertificates(Collection<? extends Certificate> certificates) {
        try {
            // Put the certificates a key store.
            char[] password = "password".toCharArray(); // Any password will work.
            KeyStore keyStore = newEmptyKeyStore(password);
            int index = 0;
            for (Certificate certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificate);
            }

            // Wrap it up in an SSL context.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            return sslContext;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
