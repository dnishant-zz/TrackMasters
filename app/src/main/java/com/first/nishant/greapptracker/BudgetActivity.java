package com.first.nishant.greapptracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.first.nishant.greapptracker.data.UniversityContract;


public class BudgetActivity extends AppCompatActivity {
BudgetAdapter mBudgetAdapter;
    Cursor budget_cursor;
    int mtotal=0;
    String[] arr_val;
    Context mContext=this;
    String[] selectionArgs;

    @Override
    protected void onResume() {
        super.onResume();
        mtotal=0;
        budget_cursor = this.getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI, MainActivityFragment.UNIVERSITY_COL, null, null, null);
        if (budget_cursor.moveToFirst()) {
            do {
                mtotal += budget_cursor.getInt(MainActivityFragment.COL_UNI_COST);
                String check_cost=budget_cursor.getString(MainActivityFragment.COL_UNI_COST_CHECK);
                for(int i=0;i<3;i++){
                    if(check_cost.charAt(i)=='1'){
                        String c=arr_val[i];
                        int val=Integer.valueOf(c);
                        mtotal+=val;
                    }
                }

            }
            while (budget_cursor.moveToNext());
        }
        TextView tv = (TextView) findViewById(R.id.total_exp);
        tv.setText(String.valueOf(mtotal));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        arr_val=new String[3];
       SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        arr_val[0]=pref.getString(this.getString(R.string.gre_cost_key),"27");
        arr_val[1]=pref.getString(this.getString(R.string.toefl_cost_key),"19");
        arr_val[2]=pref.getString(this.getString(R.string.courier_key), "12");
        budget_cursor = this.getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI, MainActivityFragment.UNIVERSITY_COL, null, null, null);
        mBudgetAdapter = new BudgetAdapter(this, budget_cursor, 0);
        ListView budgetList = (ListView) findViewById(R.id.budget_list);
        budgetList.setAdapter(mBudgetAdapter);
        longClickListener(budgetList);
    }

    public void longClickListener(ListView budgetList)
    {
        budgetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                budget_cursor = mContext.getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI, MainActivityFragment.UNIVERSITY_COL, null, null, null);
                budget_cursor.moveToPosition(position);
                selectionArgs=new String[]{budget_cursor.getString(MainActivityFragment.COL_UNI_NAME)};
                String a= budget_cursor.getString(MainActivityFragment.COL_UNI_COST_CHECK);
                boolean []arr=new boolean[3];
                for(int i=0;i<3;i++){
                    if(a.charAt(i)=='0'){
                        arr[i]=false;
                    }
                    else{
                        arr[i]=true;
                    }
                }
                final StringBuilder check_cost = new StringBuilder(a);
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cost included").
                        setMultiChoiceItems(R.array.cost, arr, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                    check_cost.setCharAt(which,'1');
                                }
                                else{
                                    check_cost.setCharAt(which, '0');
                                }
                            }
                        });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues cv = new ContentValues();
                        cv.put(UniversityContract.UniversityEntry.COL_COST_CHECK, check_cost.toString());
                        getContentResolver().update(UniversityContract.UniversityEntry.CONTENT_URI, cv,
                                UniversityContract.UniversityEntry.COL_UNIVERSITY_NAME + "=?", selectionArgs);
                        Cursor c = getContentResolver().query(UniversityContract.UniversityEntry.CONTENT_URI,
                                MainActivityFragment.UNIVERSITY_COL, null, null, null);
                        mBudgetAdapter.swapCursor(c);
                        mBudgetAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        onResume();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget, menu);
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
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

