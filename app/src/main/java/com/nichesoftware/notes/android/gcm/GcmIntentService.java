package com.nichesoftware.notes.android.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.Injection;
import com.nichesoftware.notes.contract.GcmContract;
import com.nichesoftware.notes.presenter.GcmPresenter;

/**
 * Created by n_che on 15/04/2016.
 */
public class GcmIntentService extends IntentService {
    private static final String TAG = GcmIntentService.class.getSimpleName();

    private GcmContract.ActionListener actionListener;
    /**
     * Constructeur
     */
    public GcmIntentService() {
        super(TAG);
        actionListener = new GcmPresenter(getApplicationContext(), Injection.provideGcmDataProvider());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String key = intent.getStringExtra(Config.KEY);
        switch (key) {
            default:
                // if key is not specified, register with GCM
                actionListener.invalidateGcmToken();
                actionListener.registerGcm(new GcmContract.ActionListener.RegistrationCompleteCallback() {
                    @Override
                    public void onRegistrationCompleted(String token, boolean isRegistered) {
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Registration status: " + isRegistered);
                            if (token != null) {
                                Log.d(TAG, "Registrated token: " + token);
                            }
                        }
                    }
                });
        }
    }
}
