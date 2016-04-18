package com.nichesoftware.notes.service;

import com.nichesoftware.notes.model.Note;

import java.util.List;

/**
 * Created by n_che on 05/04/2016.
 */
public interface NoteServiceApi {
    interface NoteServiceCallback<T> {
        void onLoaded(T note);
    }

    void getAllNotes(NoteServiceCallback<List<Note>> callback);

    void getNote(final String noteId, NoteServiceCallback<Note> callback);

    void saveNote(final Note note);
}
