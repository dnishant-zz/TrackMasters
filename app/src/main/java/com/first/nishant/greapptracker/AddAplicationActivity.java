package com.first.nishant.greapptracker;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.first.nishant.greapptracker.data.UniversityContract;

import java.util.Calendar;


public class AddAplicationActivity extends AppCompatActivity {
    EditText mUni_cost,mUni_deadline;
    AutoCompleteTextView mUni_name,mUni_field;
    String name,field,deadline;
    String []courses={"Aerospace/Aeronautical Engineering","Biomedical/Biotechnical","Chemical Engineering","Civil Engineering",
    "Electrical/Electronics/Telecommunication Engineering","Engineering Management (MEM)","Enviroment Engineering",
    "Financial Engineering","Industrial Engineering","Mechanical Engineering"," Management Information System(MIS)",
    "Material Science and Engineering","Computer Science"};

    String colleges[]={"Arizona State University ( ASU )",
            "Auburn University, Alabama",
            "Boston University",
            "Brigham Young University",
            "Brown University",
            "California Institute of Technology ( CalTech )",
            "Carnegie Mellon University ( CMU )",
            "Case Western Reserve University, Cleveland, Ohio",
            "City University of New York, NYC ( CUNY / CCNY )",
            "Clarkson University",
            "Clemson University",
            "Cleveland State University",
            "Colorado State University",
            "Columbia University in the city of New York",
            "Cornell University, Ithaca, New York",
            "Dartmouth University, Hanover, New Hampshire",
            "Drexel University",
            "Duke University, North Carolina",
            "Florida State University",
            "George Mason University ( GMU ), Fairfax County, Virginia",
            "George Washington University ( GWU ), Washington DC",
            "Georgia Institute of Technology ( GaTech )",
            "Harvard University",
            "Howard University",
            "Illinois Institute of Technology ( IIT ) - Chicago, Illinois",
            "Indiana University at Bloomington ( IUB )",
            "Indiana University-Purdue University Indianapolis",
            "Iowa State University",
            "Johns Hopkins University ( JHU ), Baltimore, Maryland",
            "Kent State University",
            "Lehigh University",
            "Louisiana State University",
            "Marquette University",
            "Massachussetts Institute of Technology ( MIT )",
            "Michigan State University ( MSU )",
            "Michigan Technological University ( MTU )",
            "Missouri University of Science and Technology",
            "Mississippi State University",
            "Montana State University, Bozeman",
            "New Jersey Institute of Technology ( NJIT )",
            "New Mexico State University",
            "New Mexico Institute of Mining & Technology",
            "North Carolina State University ( NCSU )",
            "Northeastern University ( NEU ), Boston",
            "Northwestern University",
            "Ohio State University ( OSU ), Ohio",
            "Ohio University ( Russ )",
            "Oklahoma State University",
            "Pennsylvania State University ( Penn State )",
            "Polytechnic Institute of New York University ( NYU Poly ), New York",
            "Princeton University, New Jersey",
            "Purdue University, West Lafayette, Indiana",
            "Rensselaer Polytechnic Institute ( RPI ), New York",
            "Rice University",
            "Rochester Institute of Technology ( RIT ), New York",
            "Rutgers, The State University of New Jersey",
            "Rutgers State University - New Brunswick",
            "San Diego State University, California",
            "San Jose State University ( SJSU ), California",
            "Stanford University, California",
            "State University New York ( SUNY ), Buffalo",
            "State University of New York ( SUNY ), Stony Brook",
            "Stevens Institute of Technology",
            "Southern Methodist University ( SMU )",
            "Syracuse University",
            "Texas A&M University ( TAMU ), College Station",
            "Texas Tech University",
            "University of Arizona ( U of A ), Tucson",
            "University of California, Berkeley ( UCB )",
            "University of California, Davis ( UCD )",
            "University of California, Irvine ( UCI )",
            "University of California - Los Angeles ( UCLA )",
            "University of California - Santa Barbara ( UCSB )",
            "University of California San Diego ( UCSD )",
            "University of Cincinnati ( UCinn )",
            "University of Colorado at Boulder",
            "University of Connecticut ( UConn ), Storrs",
            "University of Delaware, Newark, Delaware",
            "University of Florida ( UFL ), Gainesville",
            "University of Idaho",
            "University of Illinois at Chicago ( UIC )",
            "University of Illinois - Urbana Champaign ( UIUC )",
            "University of Iowa",
            "University of Maryland - College Park ( UMCP )",
            "University of Maryland Baltimore County",
            "University of Massachusetts - Amherst ( UMass )",
            "University of Michigan, Ann Arbor ( UMich )",
            "University of Minnesota - Twin Cities ( UMTC )",
            "University of North Carolina - Chapel Hill ( UNC )",
            "University of North Carolina at Charlotte ( UNCC )",
            "University of Pennsylvania ( UPenn )",
            "University of Pittsburgh ( UPitt or Pitt ), Pennsylvania",
            "University of Southern California ( USC )",
            "University of Texas at Arlington ( UTA )",
            "University of Texas - Austin",
            "University of Texas at Dallas ( UTD )",
            "University of Utah, Salt Lake City",
            "University of Washington, Seattle ( UWash )",
            "University of Wisconsin, Madison ( UWM )",
            "Virginia Polytechnic Institute and State University ( V.Tech )",
            "Yale University"};
    int cost=0;
    Context mContext=this;
    Button add;
    int mYear,mMonth,mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aplication);
        mUni_name=(AutoCompleteTextView)findViewById(R.id.uni_name);
        mUni_cost=(EditText)findViewById(R.id.uni_cost);
        mUni_field=(AutoCompleteTextView)findViewById(R.id.uni_field);
        mUni_deadline=(EditText)findViewById(R.id.uni_deadline);
        ArrayAdapter<String> field_adap=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,courses);
        mUni_field.setThreshold(1);
        mUni_field.setAdapter(field_adap);

        ArrayAdapter<String> uni_name_adap=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,colleges);
        mUni_name.setThreshold(1);
        mUni_name.setAdapter(uni_name_adap);
        mUni_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(mContext,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                mUni_deadline.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        mUni_deadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    // Process to get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    // Launch Date Picker Dialog
                    DatePickerDialog dpd = new DatePickerDialog(mContext,R.style.DialogTheme,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // Display Selected date in textbox
                                    mUni_deadline.setText(dayOfMonth + "-"
                                            + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();
                }
            }
        });
        add=(Button)findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=mUni_name.getText().toString();
                if((mUni_cost.getText().toString()).equals("")){
                    cost=0;
                }
                else{
                    cost=Integer.parseInt(mUni_cost.getText().toString());
                }
                field=mUni_field.getText().toString();
                deadline=mUni_deadline.getText().toString();
                ContentValues contentValues=new ContentValues();
                contentValues.put(UniversityContract.UniversityEntry.COL_UNIVERSITY_NAME, name);
                contentValues.put(UniversityContract.UniversityEntry.COL_APPLICATION_COST, cost);
                contentValues.put(UniversityContract.UniversityEntry.COL_APPLICATION_DEADLINE, deadline);
                contentValues.put(UniversityContract.UniversityEntry.COL_FIELD, field);
                if(field.equals("")||name.equals("")||deadline.equals("")||cost==0){
                    Toast.makeText(mContext,"Please fill all the fields",Toast.LENGTH_LONG).show();
                }
                else {
                    Uri insertedrows = mContext.getContentResolver().insert(UniversityContract.UniversityEntry.CONTENT_URI, contentValues);


                    finish();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_aplication, menu);
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
