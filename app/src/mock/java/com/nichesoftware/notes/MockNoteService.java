package com.nichesoftware.notes;

import com.nichesoftware.notes.model.Note;
import com.nichesoftware.notes.service.NoteServiceApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n_che on 06/04/2016.
 */
public class MockNoteService implements NoteServiceApi {
    private static List<Note> NOTES = new ArrayList<>();

    static {
        NOTES.add(new Note("Test1", "Note de test n°1"));
        NOTES.add(new Note("Test2", "Note de test n°2"));
        NOTES.add(new Note("Test3", "Note de test n°3"));
    }

    @Override
    public void getAllNotes(NoteServiceCallback<List<Note>> callback) {
        callback.onLoaded(NOTES);
    }

    @Override
    public void getNote(String noteId, NoteServiceCallback<Note> callback) {
        callback.onLoaded(new Note("Test detail", "Lorem ipsum... test du détail de la note..."));
    }

    @Override
    public void saveNote(Note note) {

    }
}
