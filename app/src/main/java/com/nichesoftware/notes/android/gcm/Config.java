package com.nichesoftware.notes.android.gcm;

/**
 * Created by n_che on 15/04/2016.
 */
public final class Config {
    /**
     *
     */
    public static final String GCM_SENDER_ID = "notes-1282";

    public static final String KEY = "key";
    public static final String TOKEN = "token";

    // broadcast receiver intent filters
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    /**
     * Constructeur privé
     */
    private Config() {
        // Nothing
    }
}
