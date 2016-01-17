package com.first.nishant.greapptracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Nishant on 7/16/2015.
 */
public class MainListAdapter extends CursorAdapter {
    public MainListAdapter(Context context,Cursor cursor,int flags ){
        super(context,cursor,flags);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.university_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder=(ViewHolder) view.getTag();
        String university_name=cursor.getString(MainActivityFragment.COL_UNI_NAME);
        String date=cursor.getString(MainActivityFragment.COL_UNI_DEADLINE);
        date = getFriendlyDate(date);
        String check=cursor.getString(MainActivityFragment.COL_UNI_CHECK);
        viewHolder.university.setText(university_name);
        viewHolder.deadline.setText(context.getString(R.string.date_format,date));
        if(check.equals("1111111111")){
            view.setBackgroundColor(context.getResources().getColor(R.color.green_back));
        }
        else{
            view.setBackgroundColor(context.getResources().getColor(R.color.white_back));
        }
    }

    /**
     * returns a string date in a more user friendly format
     * @param date
     * @return friendlyDate
     */
    private String getFriendlyDate(String date){
        String friendlyDate = null;
        String month = null;
        String[] dateSplit = date.split("-");
        int monthNum = Integer.parseInt(dateSplit[1]);
        switch(monthNum){
            case 1: month = "January";
                    break;
            case 2: month = "February";
                    break;
            case 3: month = "March";
                    break;
            case 4: month = "April";
                    break;
            case 5: month = "May";
                    break;
            case 6: month = "June";
                    break;
            case 7: month = "July";
                    break;
            case 8: month = "August";
                    break;
            case 9: month = "September";
                    break;
            case 10: month = "October";
                    break;
            case 11: month = "November";
                    break;
            case 12: month = "December";
                    break;
            default: break;
        }
        friendlyDate = month+" "+dateSplit[0]+", "+dateSplit[2];
        return friendlyDate;
    }
    static class ViewHolder {
        TextView university,deadline;
        ViewHolder(View view) {
            university = (TextView) view.findViewById(R.id.uni_list_item);
            deadline =(TextView) view.findViewById(R.id.uni_list_item_deadline);
        }
    }
}
