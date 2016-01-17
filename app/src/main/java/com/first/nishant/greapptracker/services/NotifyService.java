package com.first.nishant.greapptracker.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.first.nishant.greapptracker.AddAplicationActivity;
import com.first.nishant.greapptracker.MainActivity;
import com.first.nishant.greapptracker.R;

/**
 * Created by Nishant on 10/2/2015.
 */
public class NotifyService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     ** Used to name the worker thread, important only for debugging.
     */

    public NotifyService() {
        super("worker1");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notify)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        mBuilder.setAutoCancel(true);
    // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, AddAplicationActivity.class);

    // The stack builder object will contain an artificial back stack for the
    // started Activity.
    // This ensures that navigating backward from the Activity leads out of
    // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
    // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }

}
