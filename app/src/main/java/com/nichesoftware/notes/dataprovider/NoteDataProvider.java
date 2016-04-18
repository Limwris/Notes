package com.nichesoftware.notes.dataprovider;

import android.support.annotation.NonNull;

import com.nichesoftware.notes.model.Note;
import com.nichesoftware.notes.service.NoteServiceApi;

import java.util.List;

/**
 * Created by n_che on 05/04/2016.
 */
public class NoteDataProvider {
    /**
     * Liste des Notes en mémoire
     */
    protected List<Note> cachedNotes;

    /**
     * Interface du service
     */
    protected NoteServiceApi serviceApi;

    /**
     * Constructeur
     * @param serviceApi
     */
    public NoteDataProvider(@NonNull final NoteServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }

    public interface LoadNotesCallback {
        void onNotesLoaded(List<Note> notes);
    }

    public interface GetNoteCallback {
        void onNoteLoaded(Note note);
    }

    /**
     * Récupère l'ensemble des notes
     * @param callback
     */
    public void getNotes(@NonNull final LoadNotesCallback callback) {
        // Load from API only if needed.
        if (cachedNotes == null) {
            serviceApi.getAllNotes(new NoteServiceApi.NoteServiceCallback<List<Note>>() {
                @Override
                public void onLoaded(List<Note> note) {
                    cachedNotes = note;
                    callback.onNotesLoaded(cachedNotes);
                }
            });
        } else {
            callback.onNotesLoaded(cachedNotes);
        }
    }

    /**
     * Récupère la note dont l'ID est passé en paramètre
     * @param noteId
     * @param callback
     */
    public void getNote(@NonNull final String noteId, @NonNull final GetNoteCallback callback) {
        serviceApi.getNote(noteId, new NoteServiceApi.NoteServiceCallback<Note>() {
            @Override
            public void onLoaded(Note note) {
                callback.onNoteLoaded(note);
            }
        });
    }

    /**
     * Sauvegarde la note passée en paramètre
     * @param note
     */
    public void saveNote(@NonNull Note note) {
        serviceApi.saveNote(note);
        refreshData();
    }

    /**
     * Méthode appelée pour forcer le rafraîchissement des données lors du prochain appel
     */
    public void refreshData() {
        cachedNotes = null;
    }
}
