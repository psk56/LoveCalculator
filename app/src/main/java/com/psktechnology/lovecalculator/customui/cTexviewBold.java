package com.psktechnology.lovecalculator.customui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by MyWindows on 18-Feb-17.
 */

public class cTexviewBold extends TextView {
    Context context;
    public cTexviewBold(Context context) {
        super(context);
        this.context=context;
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf");
        setTypeface(face);
    }

    public cTexviewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf");
        setTypeface(face);
    }

    public cTexviewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf");
        setTypeface(face);
    }
}