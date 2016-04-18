package com.nichesoftware.notes.service;

/**
 * Created by n_che on 15/04/2016.
 */
public interface GcmServiceApi {
    interface OnRegistrationCompleted {
        void onSuccess();
        void onError();
    }

    void sendRegistrationToServer(final String token, final OnRegistrationCompleted callback);
}
