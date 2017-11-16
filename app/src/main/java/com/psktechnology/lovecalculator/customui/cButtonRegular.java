package com.psktechnology.lovecalculator.customui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by OM on 2/17/2017.
 */

public class cButtonRegular extends Button {
    Context context;
    public cButtonRegular(Context context) {
        super(context);
        this.context=context;
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/MyriadPro-Regular.ttf");
        setTypeface(face);
    }

    public cButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/MyriadPro-Regular.ttf");
        setTypeface(face);
    }

    public cButtonRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/MyriadPro-Regular.ttf");
        setTypeface(face);
    }
}