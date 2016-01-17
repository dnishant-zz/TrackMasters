package com.first.nishant.greapptracker;

import android.app.Application;
import android.app.PendingIntent;

import java.util.Calendar;

/**
 * Created by Nishant on 10/2/2015.
 */
public class TrackMasterApplication extends Application {
    Calendar calendar;
    PendingIntent pendingIntent;
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
