package com.nichesoftware.notes.contract;

/**
 * Created by n_che on 15/04/2016.
 */
public interface GcmContract {
    interface View {
        void notifyGcmSent(final String token);
    }

    interface ActionListener {

        interface RegistrationCompleteCallback {
            void onRegistrationCompleted(final String token, final boolean isRegistered);
        }

        /**
         * Initialize GCM (register device if needed, send token to server etc...)
         */
        void initializeGcm();

        /**
         * Register the device and retreive the GCM token
         * @param callback
         */
        void registerGcm(RegistrationCompleteCallback callback);

        /**
         * Invalidate the gcm token to force its refresh
         */
        void invalidateGcmToken();

        /**
         * Register the device token on the server
         */
        void registerDeviceOnServer();
    }
}
