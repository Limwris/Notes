package com.nichesoftware.notes.view.notedetail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nichesoftware.notes.Injection;
import com.nichesoftware.notes.R;
import com.nichesoftware.notes.contract.NoteDetailContract;
import com.nichesoftware.notes.presenter.NoteDetailPresenter;
import com.nichesoftware.notes.view.Container;

/**
 * Created by n_che on 08/04/2016.
 */
public class NoteDetailView extends Container implements NoteDetailContract.View {

    /**
     * Listener sur les actions de l'utilisateur
     */
    private NoteDetailContract.UserActionListener actionsListener;

    /**
     * Titre de la note
     */
    private TextView detailTitle;

    /**
     * Description de la note
     */
    private TextView detailDescription;

    /**
     * Constructeur
     * @param context
     * @param attrs
     */
    public NoteDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {

        actionsListener = new NoteDetailPresenter(this, Injection.provideNoteDataProvider());

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.view_detail_note, this, true);
        detailTitle = (TextView) root.findViewById(R.id.note_detail_title);
        detailDescription = (TextView) root.findViewById(R.id.note_detail_description);
    }

    public void compile(@Nullable final String noteId) {
        actionsListener.openNote(noteId);
    }

    @Override
    public void showMissingNote() {
        detailTitle.setText("");
        detailDescription.setText(getContext().getString(R.string.no_data));
    }

    @Override
    public void showTitle(@Nullable String title) {
        detailTitle.setVisibility(View.VISIBLE);
        detailTitle.setText(title);
    }

    @Override
    public void hideTitle() {
        detailTitle.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(@Nullable String description) {
        detailDescription.setVisibility(View.VISIBLE);
        detailDescription.setText(description);
    }

    @Override
    public void hideDescription() {
        detailDescription.setVisibility(View.GONE);
    }

    @Override
    public void showLoader() {
        detailTitle.setText("");
        detailDescription.setText(getContext().getString(R.string.loading));
    }

    @Override
    public void hideLoader() {
        // Nothing
    }
}
