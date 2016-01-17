package com.first.nishant.greapptracker;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity{
    private SettingsActivityFragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(frag==null){
            FragmentManager fragmentManager=getFragmentManager();
            frag=new SettingsActivityFragment();
            fragmentManager.beginTransaction().add(R.id.container,frag).commit();
        }
    }
}
