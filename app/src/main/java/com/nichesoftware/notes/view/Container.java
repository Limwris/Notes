package com.nichesoftware.notes.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by n_che on 05/04/2016.
 */
public class Container extends FrameLayout {

    public Container(Context context) {
        super(context);
        init(context);
    }

    public Container(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Container(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Container(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected void init(Context context) {

    }
}
