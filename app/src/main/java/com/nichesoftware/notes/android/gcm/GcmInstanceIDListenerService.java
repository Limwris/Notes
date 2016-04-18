package com.nichesoftware.notes.android.gcm;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.nichesoftware.notes.BuildConfig;

/**
 * Created by n_che on 15/04/2016.
 */
public class GcmInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = GcmInstanceIDListenerService.class.getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    @Override
    public void onTokenRefresh() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onTokenRefresh");
        }
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, GcmIntentService.class);
        startService(intent);
    }
}
