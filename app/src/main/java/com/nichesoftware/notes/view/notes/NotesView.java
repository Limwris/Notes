package com.nichesoftware.notes.view.notes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nichesoftware.notes.BuildConfig;
import com.nichesoftware.notes.Injection;
import com.nichesoftware.notes.R;
import com.nichesoftware.notes.android.AndroidUtils;
import com.nichesoftware.notes.contract.NotesContract;
import com.nichesoftware.notes.model.Note;
import com.nichesoftware.notes.presenter.GcmPresenter;
import com.nichesoftware.notes.presenter.NotesPresenter;
import com.nichesoftware.notes.utils.Assertion;
import com.nichesoftware.notes.view.Container;
import com.nichesoftware.notes.view.addnote.AddNoteActivity;
import com.nichesoftware.notes.view.notedetail.NoteDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n_che on 05/04/2016.
 */
public class NotesView extends Container implements NotesContract.View {
    private static final String TAG = NotesView.class.getSimpleName();

    /**
     * Adapter lié à la RecyclerView
     */
    private NotesAdapter notesAdapter;
    /**
     * Listener sur les actions de l'utilisateur
     */
    private NotesContract.UserActionListener actionsListener;

    /**
     * Listener for clicks on notes in the RecyclerView.
     */
    private NoteItemListener itemListener;

    public NotesView(Context context) {
        super(context);
    }

    public NotesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NotesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context) {

        actionsListener = new NotesPresenter(this, Injection.provideNoteDataProvider());

        // Tentative de récupération du token
        if (AndroidUtils.checkPlayServices(getActivity())) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Play Services available...");
            }
            GcmPresenter gcmPresenter = new GcmPresenter(context, Injection.provideGcmDataProvider());
            gcmPresenter.initializeGcm();
        } else {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Play Services not available...");
            }
        }

        itemListener = new NoteItemListener() {
            @Override
            public void onNoteClick(Note clickedNote) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Clic détecté sur la note " + clickedNote.getId());
                }
                actionsListener.openNoteDetail(clickedNote);
            }
        };

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.view_notes, this, true);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.notes_list);
        notesAdapter = new NotesAdapter(new ArrayList<Note>(0), itemListener);
        recyclerView.setAdapter(notesAdapter);
        int numColumns = getContext().getResources().getInteger(R.integer.num_notes_columns);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorAccent),
                ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actionsListener.loadNotes(true);
            }
        });

        // Charge les notes à l'ouverture de l'activité
        actionsListener.loadNotes(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Action sur le FAB -> La vue doit être attachée à la fenêtre pour être certain
        // que la vue ait accès à l'activité parente et que le FAB soit inflate correctement
        Activity activity = getActivity();
        if (activity != null) {
            activity.findViewById(R.id.fab_add_notes).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "onClick FAB");
                    }
                    actionsListener.addNewNote();
                }
            });
        }
    }

    @Override
    public void showNoteDetail(@NonNull String noteId) {
        Intent intent = new Intent(getContext(), NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, noteId);
        getContext().startActivity(intent);
    }

    @Override
    public void showNotes(List<Note> notes) {
        notesAdapter.replaceData(notes);
    }

    @Override
    public void addNote() {
        Activity activity = getActivity();
        if (activity != null) {
            Intent intent = new Intent(getContext(), AddNoteActivity.class);
            activity.startActivityForResult(intent, AddNoteActivity.REQUEST_ADD_NOTE);
        }
    }

    @Override
    public void showLoader() {
        setRefreshIndicator(true);
    }

    @Override
    public void hideLoader() {
        setRefreshIndicator(false);
    }

    private void setRefreshIndicator(final boolean doShow) {
        final SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(doShow);
            }
        });
    }

    private static class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

        /**
         * Données (liste de notes)
         */
        private List<Note> notes;

        /**
         * Listener sur le clic de la note
         */
        private NoteItemListener itemListener;

        /**
         * Constructeur
         * @param notes
         * @param itemListener
         */
        public NotesAdapter(List<Note> notes, NoteItemListener itemListener) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "NoteAdapter");
            }
            setList(notes);
            this.itemListener = itemListener;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "NoteAdapter - itemListener: " + itemListener);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.item_note, parent, false);

            return new ViewHolder(noteView, itemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Note note = notes.get(position);

            viewHolder.title.setText(note.getTitle());
            viewHolder.description.setText(note.getDescription());
        }

        public void replaceData(List<Note> notes) {
            setList(notes);
            notifyDataSetChanged();
        }

        private void setList(List<Note> notes) {
            this.notes = Assertion.checkNotNull(notes);
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        public Note getItem(int position) {
            return notes.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements android.view.View.OnClickListener {

            /**
             * TextView du titre de la note
             */
            public TextView title;

            /**
             * TextView de la description de la note
             */
            public TextView description;

            /**
             * Listener sur le clic de la note
             */
            private NoteItemListener noteItemListener;

            /**
             * Constructeur
             * @param itemView
             * @param listener
             */
            public ViewHolder(View itemView, NoteItemListener listener) {
                super(itemView);
                noteItemListener = listener;
                title = (TextView) itemView.findViewById(R.id.note_detail_title);
                description = (TextView) itemView.findViewById(R.id.note_detail_description);
//                itemView.setOnClickListener(this);
                itemView.findViewById(R.id.mainHolder).setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Clic détecté dans la liste à la position: " + position);
                }
                Note note = getItem(position);
                if (noteItemListener != null) {
                    noteItemListener.onNoteClick(note);
                }

            }
        }
    }

    /**
     * Interface du listener du clic sur une note
     */
    public interface NoteItemListener {
        void onNoteClick(Note clickedNote);
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
