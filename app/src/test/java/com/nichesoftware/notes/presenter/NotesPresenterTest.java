package com.nichesoftware.notes.presenter;

import com.nichesoftware.notes.dataprovider.NoteDataProvider;
import com.nichesoftware.notes.model.Note;
import com.nichesoftware.notes.view.notes.NotesView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by n_che on 07/04/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class NotesPresenterTest {
    /**
     * System Under Test
     */
    private NotesPresenter sut;

    /**
     * Data provider
     */
    @Mock
    private NoteDataProvider dataProvider;

    /**
     * View
     */
    @Mock
    private NotesView notesView;

    /**
     *
     */
    @Captor
    private ArgumentCaptor<NoteDataProvider.LoadNotesCallback> loadNotesCallbackCaptor;

    @Before
    public void setUp() {
        sut = new NotesPresenter(notesView, dataProvider);
    }

    @After
    public void tearDown() {
        sut = null;
    }

    @Test
    public void clickOnFab_addNewNote() {
        // Arrange - Nothing

        // Act
        sut.addNewNote();

        // Assert
        verify(notesView, times(1)).addNote();
    }

    @Test
    public void clickOnNote_openNoteDetail() {
        // Arrange
        Note requestedNote = new Note("Requested note title", "requested note description");

        // Act
        sut.openNoteDetail(requestedNote);

        // Assert
        verify(notesView).showNoteDetail(anyString());
    }

    @Test
    public void loadNotesFromRepository() {
        // Arrange - Nothing

        // Act
        sut.loadNotes(true);


    }
}
