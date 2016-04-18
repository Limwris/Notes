package com.nichesoftware.notes.contract;

import android.support.annotation.Nullable;

/**
 * Created by n_che on 06/04/2016.
 */
public interface NoteDetailContract {
    interface View {

        /**
         * Affiche la vue d'erreur
         */
        void showMissingNote();

        /**
         * Affiche le titre
         * @param title
         */
        void showTitle(@Nullable final String title);

        /**
         * Cache le titre
         */
        void hideTitle();

        /**
         * Affiche la description
         * @param description
         */
        void showDescription(@Nullable final String description);

        /**
         * Cache la description
         */
        void hideDescription();

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
         * Ouvre la note dont l'id est passé en paramètre
         * @param noteId
         */
        void openNote(@Nullable final String noteId);
    }
}
