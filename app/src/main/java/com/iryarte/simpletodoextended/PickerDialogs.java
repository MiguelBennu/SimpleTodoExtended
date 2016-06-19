package com.iryarte.simpletodoextended;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by miguel on 6/14/16.
 */
public class PickerDialogs extends android.support.v4.app.DialogFragment{
                     public Dialog onCreateDialog (Bundle savedInstanceState){
                         DateSettings dateSettings = new DateSettings(getActivity());
                         Calendar calendar =Calendar.getInstance();
                         int year =calendar.get(Calendar.YEAR);
                         int month =calendar.get(Calendar.MONTH);
                         int dayOfMonth =calendar.get(Calendar.DAY_OF_MONTH);
                         int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                         DatePickerDialog dialog;
                         dialog =  new DatePickerDialog(getActivity(),dateSettings,year,month,dayOfMonth);

                         return dialog;
                     }

}
