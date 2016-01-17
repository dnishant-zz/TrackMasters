package com.first.nishant.greapptracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Nishant on 7/14/2015.
 */
public class BudgetAdapter extends CursorAdapter {
    SharedPreferences pref;
    String []arr_val;
    public BudgetAdapter(Context context, Cursor cursor, int flags){
        super(context,cursor,flags);
        pref= PreferenceManager.getDefaultSharedPreferences(context);
        arr_val=new String[3];
        arr_val[0]=pref.getString(context.getString(R.string.gre_cost_key),"27");
        arr_val[1]=pref.getString(context.getString(R.string.toefl_cost_key),"19");
        arr_val[2]=pref.getString(context.getString(R.string.courier_key), "12");

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.budget_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    ViewHolder viewHolder=(ViewHolder) view.getTag();
        String university_name=cursor.getString(MainActivityFragment.COL_UNI_NAME);
        viewHolder.university.setText(university_name);
        int val;
        int university_cost=cursor.getInt(MainActivityFragment.COL_UNI_COST);
        String check_cost=cursor.getString(MainActivityFragment.COL_UNI_COST_CHECK);
        for(int i=0;i<3;i++){
            if(check_cost.charAt(i)=='1'){
                String c=arr_val[i];
                val=Integer.valueOf(c);
                university_cost+=val;
            }
        }
        viewHolder.cost.setText(String.valueOf(university_cost));
    }

    static class ViewHolder {
        TextView university, cost;

        ViewHolder(View view) {
            university = (TextView) view.findViewById(R.id.budget_university);
            cost = (TextView) view.findViewById(R.id.budget_university_cost);
        }
    }
}
