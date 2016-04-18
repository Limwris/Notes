package com.nichesoftware.notes;

import com.nichesoftware.notes.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by n_che on 12/04/2016.
 */
public interface NoteServiceEndpoint {
    @GET("note/{id}")
    Call<Note> getNote(@Path("id") final String id);

    @GET("notes")
    Call<List<Note>> getNotes();

    @POST("note/add")
    Call<Note> addNote(@Body Note note);
}
