package com.nichesoftware.notes.contract;

import android.support.annotation.NonNull;

import com.nichesoftware.notes.model.Note;

import java.util.List;

/**
 * Created by n_che on 05/04/2016.
 */
public interface NotesContract {
    interface View {
        /**
         * Affiche le détail de la note passée en paramètre (id)
         * @param noteId   Identifiant de la note à afficher
         */
        void showNoteDetail(@NonNull String noteId);

        /**
         * Affiche les notes passées en paramètre
         * @param notes
         */
        void showNotes(List<Note> notes);

        /**
         * Affiche la fenêtre de création d'une nouvelle note
         */
        void addNote();

        /**
         * Affiche le loader
         */
        void showLoader();

        /**
         * Supprime le loader
         */
        void hideLoader();
    }
     interface UserActionListener {
         /**
          * Action permettant de charger les notes
          * @param forceUpdate   Flag indiquant si la mise à jour doit être forcée
          */
         void loadNotes(boolean forceUpdate);

         /**
          * Action permettant l'ajout d'une note
          */
         void addNewNote();

         /**
          * Action permettant d'ouvrir le détail d'une note
          * @param requestedNote   Note à ouvrir
          */
         void openNoteDetail(@NonNull Note requestedNote);
     }
}
