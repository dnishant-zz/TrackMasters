package com.first.nishant.greapptracker;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.first.nishant.greapptracker.data.UniversityContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class UniversityDetailActivityFragment extends Fragment {
CheckBox []checkList;
    TextView mprof1,mprof2,mprof3;
    SharedPreferences prefs;
    String[] mUniversity;
    String pro1,pro2,pro3;
    public UniversityDetailActivityFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Resume","here");
        pro1 = prefs.getString(getActivity().getString(R.string.pref_prof1_key), "Professor 1");
        pro2 = prefs.getString(getActivity().getString(R.string.pref_prof2_key), "Professor 2");
        pro3 = prefs.getString(getActivity().getString(R.string.pref_prof3_key), "Professor 3");
        mprof1.setText(pro1);
        mprof2.setText(pro2);
        mprof3.setText(pro3);

    }

    @Override
    public void onPause() {
        super.onPause();
        StringBuilder c=new StringBuilder("0000000000");
        for(int i=0;i<c.length();i++){
            if(checkList[i].isChecked()){
                c.setCharAt(i,'1');
            }
        }
        ContentValues cv=new ContentValues();
        cv.put(UniversityContract.UniversityEntry.COL_CHECK, c.toString());
        getActivity().getContentResolver().update(UniversityContract.UniversityEntry.CONTENT_URI,
                cv, UniversityContract.UniversityEntry.COL_UNIVERSITY_NAME+"=? and "+
                        UniversityContract.UniversityEntry.COL_FIELD+"=?",mUniversity );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_university_detail, container, false);
        checkList=new CheckBox[10];
        String uniCombined=getActivity().getIntent().getStringExtra("UNIVERSITY_NAME");
        mUniversity = uniCombined.split("--");
       // mUniversity= new String[]{uni};
        Cursor uni_cursor=getActivity().getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI,
                MainActivityFragment.UNIVERSITY_COL,UniversityContract.UniversityEntry.COL_UNIVERSITY_NAME+"=? and "+
                        UniversityContract.UniversityEntry.COL_FIELD+"=?"
                ,mUniversity,null);
        TextView title_uni=(TextView)rootView.findViewById(R.id.detail_uni_name);
        mprof1=(TextView)rootView.findViewById(R.id.prof_1);
        mprof2=(TextView)rootView.findViewById(R.id.prof_2);
        mprof3=(TextView)rootView.findViewById(R.id.prof_3);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pro1 = prefs.getString(getActivity().getString(R.string.pref_prof1_key), "Professor 1");
        pro2 = prefs.getString(getActivity().getString(R.string.pref_prof2_key), "Professor 2");
        pro3 = prefs.getString(getActivity().getString(R.string.pref_prof3_key), "Professor 3");
        mprof1.setText(pro1);
        mprof2.setText(pro2);
        mprof3.setText(pro3);
        mprof1.setOnClickListener(listener);
        mprof2.setOnClickListener(listener);
        mprof3.setOnClickListener(listener);
        TextView title_course=(TextView)rootView.findViewById(R.id.detail_course);
        uni_cursor.moveToFirst();
        title_uni.setText(uni_cursor.getString(MainActivityFragment.COL_UNI_NAME));
        title_course.setText(uni_cursor.getString(MainActivityFragment.COL_UNI_FIELD));
        checkList[0]=(CheckBox)rootView.findViewById(R.id.checkBox_sop);
        checkList[1]=(CheckBox)rootView.findViewById(R.id.checkBox_resume);
        checkList[2]=(CheckBox)rootView.findViewById(R.id.checkBox_transcripts);
        checkList[3]=(CheckBox)rootView.findViewById(R.id.checkBox_gre);
        checkList[4]=(CheckBox)rootView.findViewById(R.id.checkBox_toefl);
        checkList[5]=(CheckBox)rootView.findViewById(R.id.checkBox_courier);
        checkList[6]=(CheckBox)rootView.findViewById(R.id.checkBox_prof1);
        checkList[7]=(CheckBox)rootView.findViewById(R.id.checkBox_prof2);
        checkList[8]=(CheckBox)rootView.findViewById(R.id.checkBox_prof3);
        checkList[9]=(CheckBox)rootView.findViewById(R.id.checkBox_online_app);
        String c=uni_cursor.getString(MainActivityFragment.COL_UNI_CHECK);
        if(!(c.equals("0000000000"))){
            for(int i=0;i<c.length();i++){
                if(c.charAt(i)=='1'){
                    checkList[i].setChecked(true);
                }
            }
        }
        return rootView;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
    };
}
