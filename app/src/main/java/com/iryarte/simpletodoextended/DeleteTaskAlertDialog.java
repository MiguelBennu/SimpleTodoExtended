package com.iryarte.simpletodoextended;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by miguel on 6/18/16.
 */
public class DeleteTaskAlertDialog extends DialogFragment {

    public DeleteTaskAlertDialog (){

        // Empty constructor required for DialogFragment

    }

    public static DeleteTaskAlertDialog newInstance(String task) {

        DeleteTaskAlertDialog frag = new DeleteTaskAlertDialog();
        Bundle args = new Bundle();
        args.putString("task", task);
        frag.setArguments(args);
        return frag;

    }



    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String task = getArguments().getString("task");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Delete task");
        alertDialogBuilder.setMessage("You are about to delete a task, are you sure you want to do this?");

        //On confirm deletion
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                //Access Database
                TodoDatabaseHandler handler;
                TodoCursorAdapter todoAdapter;

                handler = new TodoDatabaseHandler(getActivity());
                Cursor todoCursor = handler.getCursor(handler,TableData.TableInfo.TABLE_ITEMS_LIST );

                //Remove task
                handler.taskRemove (handler,TableData.TableInfo.TABLE_ITEMS_LIST,TableData.TableInfo.ILB,task);

                // Refresh main Activity
                Intent i = new Intent(getActivity(), MainActivity.class);  //your class
                startActivity(i);
                //Toast
                Toast.makeText(getActivity(), task+" Removed", Toast.LENGTH_SHORT).show();

            }

        });
        //On cancel
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }

        });


        return alertDialogBuilder.create();

    }

}
