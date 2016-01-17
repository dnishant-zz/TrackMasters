package com.first.nishant.greapptracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.first.nishant.greapptracker.receivers.AlarmReceiver;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.lang.reflect.Field;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ShowcaseView showcaseView;
    private int counter=0;
    Calendar calendar;
    PendingIntent pendingIntent;
    private Target t1,t2,t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = new ViewTarget(R.id.add_app,this);
        t2 = new ViewTarget(R.id.budget, this);
        t3 = new ViewTarget(R.id.settings_dummy, this);


        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(t1)
                .setContentTitle("Add an application")
                .setContentText("Add the details of the universities you are applying to.")
                .setOnClickListener(this)
                .singleShot(1)
                .build();
        showcaseView.setButtonText("Got It!!");
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        showcaseView.setButtonPosition(lps);

        //Hack Code
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 30);
        Intent broadcastIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (counter){
            case 0: showcaseView.setShowcase(t2, true);
                    showcaseView.setContentTitle("Track Expenditure");
                    showcaseView.setContentText("Check the amount (in $) spent on Applications");
                    counter++;
                    break;
            case 1: showcaseView.setShowcase(t3, true);
                    showcaseView.setContentTitle("Settings");
                    showcaseView.setContentText("Here you can change the names of the professors who give the Recommendations for you");
                    counter++;
                    break;
            case 2: showcaseView.hide();
                    counter++;
                    break;

        }
    }
}
