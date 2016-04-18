package com.nichesoftware.notes.model;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by n_che on 05/04/2016.
 */
public class Note {
    /**
     * Identifiant unique de la note
     */
    private String id;
    /**
     * Titre de la note
     */
    @Nullable
    private String title;
    /**
     * Description de la note
     */
    @Nullable
    private String description;

    /**
     * Constructeur
     * @param title
     * @param description
     */
    public Note(@Nullable String title, @Nullable String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
    }

    /**
     * Getter sur la description de la note
     * @return description
     */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Getter sur l'identifiant unique de la note
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter sur le titre de la note
     * @return title
     */
    @Nullable
    public String getTitle() {
        return title;
    }

    /**
     * Setter sur la description de la note
     * @param description
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /**
     * Setter sur l'identifiant unique de la note
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter sur le titre de la note
     * @param title
     */
    public void setTitle(@Nullable String title) {
        this.title = title;
    }
}
