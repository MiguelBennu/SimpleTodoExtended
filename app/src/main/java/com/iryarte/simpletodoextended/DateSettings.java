package com.iryarte.simpletodoextended;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by miguel on 6/14/16.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener{


    Context context;
    static int YEAR, MONTH, DAY_MONTH, DAY_WEEK;

    public DateSettings (Context context){

             this.context = context;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

/*      //test it works
        Toast .makeText(context, "Selected date: "+ monthOfYear+ " / " +dayOfMonth+" / "+year, Toast.LENGTH_LONG).show();
*/
        String yearS = Integer.toString(year);
        String monthS = Integer.toString(monthOfYear);
        String dayOfMonthS = Integer.toString(dayOfMonth);

        //Update buttons
        AddItemActivity.setDate.setText(dayOfMonthS +" / "+monthS+" / "+yearS);
        EditDialogFragment.btn_FgmtEditDate.setText(dayOfMonthS +" / "+monthS+" / "+yearS);
    }
}
