package com.first.nishant.greapptracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.first.nishant.greapptracker.services.NotifyService;

import java.util.Calendar;

/**
 * Created by Nishant on 10/2/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    Calendar calendar = Calendar.getInstance();
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notifyService = new Intent(context, NotifyService.class);
        context.startService(notifyService);
    }
}
