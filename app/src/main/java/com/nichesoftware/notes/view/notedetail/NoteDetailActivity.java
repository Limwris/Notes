package com.nichesoftware.notes.view.notedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nichesoftware.notes.R;

/**
 * Created by n_che on 08/04/2016.
 * TODO: Source: https://codelabs.developers.google.com/codelabs/android-testing/index.html
 */
public class NoteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "NOTE_ID";

    /**
     * Vue du détail de la note
     */
    private NoteDetailView noteDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        noteDetailView = (NoteDetailView) findViewById(R.id.note_detail_view);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.drawable.ic_back_up_navigation);
            }
        }

        // Get the requested note id
        String noteId = getIntent().getStringExtra(EXTRA_NOTE_ID);
        initView(noteId);
    }

    /**
     * Initialisation de la vue
     * @param noteId
     */
    private void initView(@Nullable final String noteId) {
        noteDetailView.compile(noteId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
