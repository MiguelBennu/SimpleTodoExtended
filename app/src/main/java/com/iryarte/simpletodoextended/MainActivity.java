package com.iryarte.simpletodoextended;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



import org.apache.commons.io.FileUtils;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvItems;

    private final int REQUEST_CODE = 20;
    private int CURRENT_POSITION;
    Context ctx =this;
    boolean dbExists = false;

    TodoDatabaseHandler handler;
    TodoCursorAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        handler = new TodoDatabaseHandler(this);
        // Get access to the underlying writeable database
        Cursor todoCursor = handler.getCursor(handler,TableData.TableInfo.TABLE_ITEMS_LIST );
        // Find ListView to populate
        lvItems = (ListView) findViewById(R.id.lvItems);
        // Setup cursor adapter using cursor from last step
        todoAdapter = new TodoCursorAdapter(this, todoCursor);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);
        // ready to listen
        setupListViewListener();

    }

    // On add item
    public void onAddItem(View v) {

         Intent i = new Intent(MainActivity.this, com.iryarte.simpletodoextended.AddItemActivity.class);
         startActivityForResult(i, REQUEST_CODE);

    }
    //set up list view listener
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView adapter,
                                                   View item, int pos, long id) {

                        String task = findItem(TableData.TableInfo.ILB, pos);
                        showAlertDialog(task);

                        return true;

                    }

                });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {

                        String task = findItem(TableData.TableInfo.ILB, pos);
                        String priority = findItem(TableData.TableInfo.ILP, pos);
                        String date = findItem(TableData.TableInfo.ILD, pos);
                        String notes = findItem(TableData.TableInfo.ILN, pos);
                        //call fragment and pass data
                        showEditDialog(pos,task, priority, date, notes);

                    }

                });
    }

    //find an item in the database based on list view position
    private String findItem(String Column, int pos) {
        //Declare and Initialize String
        String item="";

        try {
            item = handler.retriveValueByPos(handler, TableData.TableInfo.TABLE_ITEMS_LIST, Column, pos);
        } catch (Exception e) {
            Log.i("UNABLE TO IMPORT: ","items file");
        }
        return item;
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String taskItem = data.getExtras().getString("TASK");
            String priority = data.getExtras().getString("PRIORITY");
            int CODE = data.getExtras().getInt("CODE", 0);

            Cursor newCursor = handler.getCursor(handler,TableData.TableInfo.TABLE_ITEMS_LIST );

            todoAdapter.changeCursor(newCursor);

              Toast.makeText(this, taskItem+"  addition succesful " , Toast.LENGTH_SHORT).show();
        }
    }
    //Show custom Task edit dialog fragment on short click
    private void showEditDialog(int pos, String task, String priority, String date, String notes) {

        //declare fragment manager
        FragmentManager fm = getSupportFragmentManager();
        //pass variables to Edit dialog fragment class
        EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(pos,task, priority, date, notes);
        //Show dialog
        editDialogFragment.show(fm, "fragment_edit_name");

    }
    //show aler dialog on long click
    private void showAlertDialog(String task) {
        //declare fragment manager
        FragmentManager fm = getSupportFragmentManager();
        //pass variables to Edit dialog fragment class
        DeleteTaskAlertDialog alertDialog = DeleteTaskAlertDialog.newInstance(task);
        //Show dialog
        alertDialog.show(fm, "fragment_alert");

    }

}
