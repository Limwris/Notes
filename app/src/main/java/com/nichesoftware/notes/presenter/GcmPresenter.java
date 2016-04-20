package com.nichesoftware.notes.presenter;

import android.content.Context;
import android.util.Log;

import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.contract.GcmContract;
import com.nichesoftware.notes.dataprovider.GcmDataProvider;
import com.nichesoftware.notes.utils.StringUtils;

/**
 * Created by n_che on 15/04/2016.
 */
public class GcmPresenter implements GcmContract.ActionListener {
    private static final String TAG = GcmPresenter.class.getSimpleName();

    /**
     * Android context
     */
    private Context context;

    /**
     * View
     */
    private GcmContract.View view;

    /**
     * Data provider
     */
    private GcmDataProvider provider;

    /**
     * Constructor
     * @param context
     * @param provider
     */
    public GcmPresenter(Context context, GcmDataProvider provider) {
        this.context = context;
        this.provider = provider;
    }

    @Override
    public void initializeGcm() {
        registerGcm(null);

        if (!provider.isTokenSent(context)) {
            registerDeviceOnServer();
        }
    }

    /**
     * Registering with GCM and obtaining the gcm registration id
     */
    @Override
    public void registerGcm(final RegistrationCompleteCallback callback) {
        final String token = provider.retreiveGcmToken(context);

        if (callback != null) {
            callback.onRegistrationCompleted(token, provider.isTokenSent(context));
        }
    }

    @Override
    public void invalidateGcmToken() {
        provider.invalidateGcmToken(context);
    }

    @Override
    public void registerDeviceOnServer() {
        final String token = provider.retreiveGcmToken(context);

        if (!StringUtils.isEmpty(token)) {
            // sending the registration id to our server
            provider.sendRegistrationToServer(token, new GcmDataProvider.OnRegistrationCompleted() {
                @Override
                public void onSuccess() {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "Token registered token successfully in server.");
                    }
                    provider.setTokenSent(context, true);
                }

                @Override
                public void onError() {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "Failed to registered token in server.");
                    }
                    provider.setTokenSent(context, false);
                }
            });
        }
    }

    @Override
    public void sendGcmMessage(final String message) {
        provider.sendGcmMessage(message, new GcmDataProvider.OnMessageDelivered() {
            @Override
            public void onSuccess() {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Message sent successfully.");
                }
            }

            @Override
            public void onError() {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "An error occurred when sending the message.");
                }
            }
        });
    }
}
