package com.iryarte.simpletodoextended;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by miguel on 6/12/16.
 */
public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));

       // int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));

       // getTextColor(priority);
        // Populate fields with extracted properties
        if (priority.equals("HIGH"))
        tvPriority.setTextColor(Color.RED);
        else if (priority.equals("MEDIUM"))
            tvPriority.setTextColor(Color.BLUE);

        else if (priority.equals("LOW"))
            tvPriority.setTextColor(Color.BLACK);

        else if (priority.equals("WISH"))
            tvPriority.setTextColor(Color.BLACK);

        //populate listview
        tvBody.setText(body);
        tvPriority.setText(priority);

    }


}
