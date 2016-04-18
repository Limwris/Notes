package com.nichesoftware.notes;

import com.nichesoftware.notes.model.Note;
import com.nichesoftware.notes.service.NoteServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by n_che on 06/04/2016.
 */
public class NoteService implements NoteServiceApi {

    private NoteServiceEndpoint serviceEndpoint;

    public NoteService() {
        serviceEndpoint = new Retrofit.Builder()
                .baseUrl(ServiceConstant.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NoteServiceEndpoint.class);
    }

    @Override
    public void getAllNotes(final NoteServiceCallback<List<Note>> callback) {
        Call<List<Note>> call = serviceEndpoint.getNotes();
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                callback.onLoaded(null);
            }
        });
    }

    @Override
    public void getNote(String noteId, final NoteServiceCallback<Note> callback) {
        Call<Note> call = serviceEndpoint.getNote(noteId);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                callback.onLoaded(null);
            }
        });
    }

    @Override
    public void saveNote(Note note) {

    }
}
