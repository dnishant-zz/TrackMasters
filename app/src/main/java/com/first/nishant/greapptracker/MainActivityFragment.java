package com.first.nishant.greapptracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.first.nishant.greapptracker.data.UniversityContract;




/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{

    static final String[] UNIVERSITY_COL={UniversityContract.UniversityEntry._ID,
            UniversityContract.UniversityEntry.COL_UNIVERSITY_NAME,
            UniversityContract.UniversityEntry.COL_FIELD,
            UniversityContract.UniversityEntry.COL_APPLICATION_COST,
            UniversityContract.UniversityEntry.COL_APPLICATION_DEADLINE,
            UniversityContract.UniversityEntry.COL_CHECK,
            UniversityContract.UniversityEntry.COL_COST_CHECK};

    static final int COL_UNI_ID=0;
    static final int COL_UNI_NAME=1;
    static final int COL_UNI_FIELD=2;
    static final int COL_UNI_COST=3;
    static final int COL_UNI_DEADLINE=4;
    static final int COL_UNI_CHECK=5;
    static final int COL_UNI_COST_CHECK=6;
    private Cursor mUni;
    private Context mContext;
    private MainListAdapter universityAdapter;
    private ListView mlistView;
    private TextView txtAddApp;

    @Override
    public void onResume() {
        super.onResume();
        Cursor cur = getActivity().getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI, UNIVERSITY_COL, null, null, null);
        if(!cur.moveToFirst()){
            txtAddApp.setVisibility(View.VISIBLE);
            mlistView.setVisibility(View.GONE);
        }else{
            txtAddApp.setVisibility(View.GONE);
            mlistView.setVisibility(View.VISIBLE);
        }
        universityAdapter.swapCursor(cur);
        universityAdapter.notifyDataSetChanged(); //not sure about this
        }



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        mContext=getActivity();
        txtAddApp = (TextView) rootView.findViewById(R.id.txt_add_app);
        mlistView=(ListView) rootView.findViewById(R.id.university_list);
        mUni=getActivity().getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI, UNIVERSITY_COL, null, null, null);
        universityAdapter=new MainListAdapter(mContext,mUni,0);

        mlistView.setAdapter(universityAdapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mUni=getActivity().getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI, UNIVERSITY_COL, null, null, null);
                mUni.moveToPosition(position);
                String university_name = (String) mUni.getString(MainActivityFragment.COL_UNI_NAME);
                String course_name = (String) mUni.getString(MainActivityFragment.COL_UNI_FIELD);
                Intent intent = new Intent(getActivity(), UniversityDetailActivity.class);
                intent.putExtra("UNIVERSITY_NAME", university_name+"--"+course_name);
                startActivity(intent);
            }
        });

        mlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mUni=getActivity().getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI, UNIVERSITY_COL, null, null, null);
                final int p=position;
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUni.moveToPosition(p);
                        String uni = mUni.getString(MainActivityFragment.COL_UNI_NAME);;
                        String[] a = {uni};
                        getActivity().getContentResolver().delete(UniversityContract.UniversityEntry.CONTENT_URI,
                                UniversityContract.UniversityEntry.COL_UNIVERSITY_NAME + "=?", a);
                        dialog.dismiss();
                        onResume();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert= alertDialog.create();
                alert.show();
                return true;
            }
        });
        Button add=(Button)rootView.findViewById(R.id.add_app);
        Button budget=(Button)rootView.findViewById(R.id.budget);
        listenBudget(budget);
        listen(add);
        return rootView;
    }

    public void listenBudget(Button b){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BudgetActivity.class);
                startActivity(intent);
            }
        });
    }
    public void listen(Button b){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AddAplicationActivity.class);
                startActivity(intent);

            }
        });
    }

}
