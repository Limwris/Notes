package com.nichesoftware.notes.view.statistics;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nichesoftware.notes.R;


/**
 * Created by n_che on 06/04/2016.
 * TODO: Action bar customization:  - http://android-developers.blogspot.fr/2011/04/customizing-action-bar.html
 *                                  - http://codetheory.in/difference-between-setdisplayhomeasupenabled-sethomebuttonenabled-and-setdisplayshowhomeenabled/
 *                                  - http://www.androidhive.info/2013/11/android-working-with-action-bar/
 *                                  - https://blog.stylingandroid.com/styling-the-actionbar-part-1/
 *                                  - https://blog.stylingandroid.com/actionbarcompat-part-1/
 */
public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistics);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(R.string.statistics_title);
                // Enabling action bar Up / Back navigation
                ab.setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationIcon(R.drawable.ic_back_up_navigation);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
