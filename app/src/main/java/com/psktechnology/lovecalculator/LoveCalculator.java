package com.psktechnology.lovecalculator;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by OM on 2/16/2017.
 */

public class LoveCalculator extends Application {


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
