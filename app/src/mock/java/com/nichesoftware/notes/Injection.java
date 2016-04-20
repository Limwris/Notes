package com.nichesoftware.notes;

import com.nichesoftware.notes.dataprovider.GcmDataProvider;
import com.nichesoftware.notes.dataprovider.NoteDataProvider;

/**
 * Created by n_che on 06/04/2016.
 */
public class Injection {
    public static NoteDataProvider provideNoteDataProvider() {
        return new NoteDataProvider(new MockNoteService());
    }

    public static GcmDataProvider provideGcmDataProvider() {
        return new GcmDataProvider(new MockGcmService());
    }
}
