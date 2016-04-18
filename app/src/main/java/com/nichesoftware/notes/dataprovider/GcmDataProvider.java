package com.nichesoftware.notes.dataprovider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.android.AndroidUtils;
import com.nichesoftware.notes.android.gcm.Config;
import com.nichesoftware.notes.service.GcmServiceApi;
import com.nichesoftware.notes.utils.StringUtils;

/**
 * Created by n_che on 15/04/2016.
 */
public class GcmDataProvider {
    private static final String TAG = GcmDataProvider.class.getSimpleName();

    public interface OnRegistrationCompleted {
        void onSuccess();
        void onError();
    }

    private GcmServiceApi serviceApi;

    /**
     * Constructor
     * @param serviceApi
     */
    public GcmDataProvider(GcmServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }

    /**
     * Retrieve the GCM token from google server if necessary
     * @param context
     * @return token
     */
    public String retreiveGcmToken(final Context context) {

        final String retVal = getGcmToken(context);

        if (!StringUtils.isEmpty(retVal)) {
            return retVal;
        } else {
            registerGcm(context);
        }

        return getGcmToken(context);
    }

    public void registerGcm(final Context context) {

        // Retreive GCM token from google server
        AndroidUtils.retreiveGcmToken(context, new AndroidUtils.GcmTokenCallback() {
            @Override
            public void getGcmToken(String token) {

                if (!StringUtils.isEmpty(token)) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "GCM Registration Token: " + token);
                    }

                    // save the token
                    saveGcmToken(token, context);

                    // sending the registration id to our server
                    sendRegistrationToServer(token, new GcmDataProvider.OnRegistrationCompleted() {
                        @Override
                        public void onSuccess() {
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, "Token registered token successfully in server.");
                            }
                            setTokenSent(context, true);
                        }

                        @Override
                        public void onError() {
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, "Failed to registered token in server.");
                            }
                            setTokenSent(context, false);
                        }
                    });

                } else {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "Failed to complete token refresh.");
                    }

                    setTokenSent(context, false);
                }

            }
        });
    }

    public void saveGcmToken(final String token, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(Config.TOKEN, token).apply();
    }

    public void invalidateGcmToken(Context context) {
        saveGcmToken(null, context);
    }

    public String getGcmToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Config.TOKEN, null);
    }

    public void setTokenSent(Context context, final boolean sent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(Config.SENT_TOKEN_TO_SERVER, sent).apply();
    }

    public boolean isTokenSent(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(Config.SENT_TOKEN_TO_SERVER, false);
    }

    public void sendRegistrationToServer(final String token, final OnRegistrationCompleted callback) {
        serviceApi.sendRegistrationToServer(token, new GcmServiceApi.OnRegistrationCompleted() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onError() {
                if (callback != null) {
                    callback.onError();
                }
            }
        });
    }
}
