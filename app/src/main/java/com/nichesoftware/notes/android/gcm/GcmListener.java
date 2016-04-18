package com.nichesoftware.notes.android.gcm;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.R;

/**
 * Created by n_che on 15/04/2016.
 */
public class GcmListener extends GcmListenerService {
    private static final String TAG = GcmListener.class.getSimpleName();

    /**
     * Called when message is received.
     *
     * @param from   SenderID of the sender.
     * @param bundle Data bundle containing message data as key/value pairs.
     *               For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle bundle) {
        final String title = bundle.getString("title");
        final String message = bundle.getString("message");
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "message: " + message);
        }
        createNotification(title, message);
    }

    /**
     * Création et envoi d'une notification
     * @param title
     * @param message
     */
    private void createNotification(final String title, final String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message);
        notificationManager.notify(1, builder.build());
    }
}
