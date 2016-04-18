package com.nichesoftware.notes.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.dataprovider.NoteDataProvider;
import com.nichesoftware.notes.model.Note;
import com.nichesoftware.notes.contract.NotesContract;

import java.util.List;

/**
 * Created by n_che on 06/04/2016.
 */
public class NotesPresenter implements NotesContract.UserActionListener {
    private static final String TAG = NotesPresenter.class.getSimpleName();

    /**
     * Data provider
     */
    private NoteDataProvider noteDataProvider;

    /**
     * View
     */
    private NotesContract.View notesView;

    /**
     * Constructeur
     * @param view
     * @param noteDataProvider
     */
    public NotesPresenter(@NonNull NotesContract.View view, @NonNull NoteDataProvider noteDataProvider) {
        this.noteDataProvider = noteDataProvider;
        this.notesView = view;
    }

    @Override
    public void loadNotes(boolean forceUpdate) {
        notesView.showLoader();

        if (forceUpdate) {
            noteDataProvider.refreshData();
        }

        noteDataProvider.getNotes(new NoteDataProvider.LoadNotesCallback() {
            @Override
            public void onNotesLoaded(List<Note> notes) {
                notesView.hideLoader();
                if (notes != null) {
                    notesView.showNotes(notes);
                } else {
                    // Gérer erreurs webservice
                }
            }
        });
    }

    @Override
    public void addNewNote() {
        notesView.addNote();
    }

    @Override
    public void openNoteDetail(@NonNull Note requestedNote) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, String.format("La note [id=%s, title=%s] a été cliquée...", requestedNote.getId(), requestedNote.getTitle()));
        }
        notesView.showNoteDetail(requestedNote.getId());
    }
}
