package com.iryarte.simpletodoextended;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {


    TodoDatabaseHandler handler;
    Context ctx=this;
   //String[] priorityValues = { "MEDIUM","HIGH","LOW","IF NOTHING ELSE"};
    ArrayAdapter<String> adapter;
    Spinner etPriorityAdd;
    String etPrioritySelected;
    static EditText etTaskItem;
    static Button setDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //handler - DB access
        handler = new TodoDatabaseHandler(ctx);

        //Create Array Adapter
        adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_add,getResources().getStringArray(R.array.priorityValues));



        //Getting the instance of AutoCompleteTextView
        etPriorityAdd = (Spinner) findViewById(R.id.etPriorityAdd);
        // Initialize and set Adapter
        etPriorityAdd.setAdapter(adapter);

        //Getting the instance of Button
        setDate = (Button) findViewById(R.id.btnAddDate);
        //retreiving Today's date instance to initialize button and as default value into database
        Calendar calendar = Calendar.getInstance();
        //declare format
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
        //set current date on button
        setDate.setText(mdformat.format(calendar.getTime()));


        //When spinner selected
        etPriorityAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                etPrioritySelected = adapter.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //Default value if no priority selected
                etPrioritySelected = getResources().getStringArray(R.array.priorityValues)[3];
            }
        });

    }

    //call the date picker dialog
    public void setDate(View view) {

     PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getSupportFragmentManager(),"date_picker");

        setDate = (Button) findViewById(R.id.btnAddDate);
    }

    public void onAddTask(View v) {
        // get text from taks field
        EditText etTaskItem = (EditText) findViewById(R.id.etTaskAdd);
        EditText etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
        String taskItem = etTaskItem.getText().toString();
        String taskNotes =  etTaskNotes.getText().toString();
       // get text in Priority spinner
        String priorityItem = etPrioritySelected;

        // get text in Button
        String dueDate = (String) setDate.getText();
        //add new task into DB
        writeItems(taskItem, priorityItem, dueDate, taskNotes);

        Intent data = new Intent();

        // Pass relevant data back as a result
        data.putExtra("TASK", taskItem);
        data.putExtra("PRIORITY", priorityItem);
        data.putExtra("CODE", 20); // ints work too

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response

        //return to main activity
        finish(); // closes the activity, pass data to parent

    }

    private void writeItems(String taskItem, String priorityItem, String dateItem, String itemNotes) {

        try {

            handler.newItemEntry(handler,TableData.TableInfo.TABLE_ITEMS_LIST,TableData.TableInfo.ILB, taskItem,TableData.TableInfo.ILP, priorityItem, TableData.TableInfo.ILD, dateItem, TableData.TableInfo.ILN, itemNotes);

            Log.d("DB_OPS", "One row inserted");

        } catch (Exception e) {
            Log.e("DB_OPS", "SQLiteException:" + e.getMessage());
        }
    }



}
