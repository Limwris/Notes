package com.nichesoftware.notes;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by n_che on 15/04/2016.
 */
public interface GcmServiceEndpoint {
    @POST("gcm/register/{registerId}")
    Call<Boolean> registerDevice(@Path("registerId") final String registerId);

    @POST("gcm/send/{message}")
    Call<Boolean> sendMessage(@Path("message") final String message);
}
