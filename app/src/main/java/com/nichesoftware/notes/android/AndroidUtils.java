package com.nichesoftware.notes.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.R;

import java.util.List;

/**
 * Created by n_che on 15/04/2016.
 */
public final class AndroidUtils {
    private static final String TAG = AndroidUtils.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Private constructor
     */
    private AndroidUtils() {
        // Nothing
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    /**
     * Methods that checks if the Play Services are available
     * @param activity
     * @return
     */
    public static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                if (activity != null) {
                    apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                }
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "This device is not supported. Google Play Services not installed!");
                    Toast.makeText(activity, "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                }
            }
            return false;
        }
        return true;
    }

    public interface GcmTokenCallback {
        void getGcmToken(final String token);
    }
    /**
     * Retreive the GCM token from Google
     * @param context
     * @return
     */
    public static void retreiveGcmToken(final Context context, final GcmTokenCallback callback) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Retreive GCM Token...");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = null;

                try {
                    InstanceID instanceID = InstanceID.getInstance(context);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "Instance retreived...");
                    }
                    token = instanceID.getToken(context.getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "Token retreived...");
                    }
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "Token cannot be retreived", e);
                    }
                } finally {
                    callback.getGcmToken(token);
                }
            }
        }).start();
    }
}
