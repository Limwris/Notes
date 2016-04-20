package com.nichesoftware.notes;

import com.nichesoftware.notes.service.GcmServiceApi;

/**
 * Created by n_che on 15/04/2016.
 */
public class MockGcmService implements GcmServiceApi {
    @Override
    public void sendRegistrationToServer(String token, OnRegistrationCompleted callback) {
        // TODO
    }

    @Override
    public void sendBroadcastMessage(String message, OnMessageDelivered callback) {
        // Todo
    }
}
