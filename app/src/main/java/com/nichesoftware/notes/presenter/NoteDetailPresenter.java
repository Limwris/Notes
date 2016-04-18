package com.nichesoftware.notes.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.dataprovider.NoteDataProvider;
import com.nichesoftware.notes.model.Note;
import com.nichesoftware.notes.utils.StringUtils;
import com.nichesoftware.notes.contract.NoteDetailContract;

/**
 * Created by n_che on 08/04/2016.
 */
public class NoteDetailPresenter implements NoteDetailContract.UserActionListener {
    private static final String TAG = NoteDetailPresenter.class.getSimpleName();

    /**
     * Data provider
     */
    private final NoteDataProvider noteDataProvider;

    /**
     * Vue
     */
    private final NoteDetailContract.View notesDetailView;

    public NoteDetailPresenter(@NonNull final NoteDetailContract.View notesDetailView,
                                @NonNull final NoteDataProvider noteDataProvider) {
        this.noteDataProvider = noteDataProvider;
        this.notesDetailView = notesDetailView;
    }

    @Override
    public void openNote(@Nullable final String noteId) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Chargement de la note " + noteId);
        }
        // Affiche la vue d'erreur si l'id est nul
        if (StringUtils.isEmpty(noteId)) {
            notesDetailView.showMissingNote();
            return;
        }

        notesDetailView.showLoader();

        noteDataProvider.getNote(noteId, new NoteDataProvider.GetNoteCallback() {
            @Override
            public void onNoteLoaded(Note note) {
                notesDetailView.hideLoader();
                if (note != null) {
                    // Affiche la note
                    showNote(note);
                } else {
                    // Affiche la vue d'erreur
                    notesDetailView.showMissingNote();
                }
            }
        });
    }

    /**
     * Affiche les informations disponibles de la note passé en paramètre
     * @param note
     */
    private void showNote(@NonNull Note note) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Afficahge de la note " + note.getId());
        }
        final String title = note.getTitle();
        final String description = note.getDescription();

        if (StringUtils.isEmpty(title)) {
            notesDetailView.hideTitle();
        } else {
            notesDetailView.showTitle(title);
        }

        if (StringUtils.isEmpty(description)) {
            notesDetailView.hideDescription();
        } else {
            notesDetailView.showDescription(description);
        }
    }
}
