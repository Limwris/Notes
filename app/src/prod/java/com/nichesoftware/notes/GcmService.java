package com.nichesoftware.notes;

import com.nichesoftware.notes.service.GcmServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by n_che on 15/04/2016.
 */
public class GcmService implements GcmServiceApi {
    private GcmServiceEndpoint serviceEndpoint;

    /**
     * Constructor
     */
    public GcmService() {
        serviceEndpoint = new Retrofit.Builder()
                .baseUrl(ServiceConstant.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GcmServiceEndpoint.class);
    }

    @Override
    public void sendRegistrationToServer(final String token, final OnRegistrationCompleted callback) {
        Call<Boolean> call = serviceEndpoint.registerDevice(token);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                final Boolean result = response.body();
                if (result) {
                    callback.onSuccess();
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void sendBroadcastMessage(final String message, final OnMessageDelivered callback) {
        Call<Boolean> call = serviceEndpoint.sendMessage(message);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                final Boolean result = response.body();
                if (result) {
                    callback.onSuccess();
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onError();
            }
        });
    }
}
