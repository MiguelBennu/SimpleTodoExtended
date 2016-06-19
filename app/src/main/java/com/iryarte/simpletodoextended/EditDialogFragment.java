package com.iryarte.simpletodoextended;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * Created by miguel on 6/18/16.
 */
public class EditDialogFragment extends DialogFragment {

    //resources
    private EditText et_fgmtEditTask, et_fgmtEditNotes;
    //passed values
    int posReceived;
    private String oldTaskItem, oldPriorityItem, oldDateItem, oldNotesItem;
    //date button
    static Button btn_FgmtEditDate;
    //dialog buttons
    private Button btnCancel, btnConfirm;

    //Spiner
    ArrayAdapter<String> adapter;
    Spinner fgmtSpinPriotity;
    String etPrioritySelected;
    //Database
    Context ctx=getActivity();
    TodoDatabaseHandler handler;

    public EditDialogFragment (){

        // Empty constructor required for DialogFragment

    }

    public static EditDialogFragment newInstance(int pos, String task, String priority, String date, String notes) {

        EditDialogFragment frag = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putString("task", task);
        args.putString("priority", priority);
        args.putString("date", date);
        args.putString("notes", notes);
        frag.setArguments(args);
        return frag;

    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_edit_entry, container);



    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //assing passed values
        oldTaskItem = getArguments().getString("task");
        posReceived = getArguments().getInt("pos");
        oldPriorityItem = getArguments().getString("priority");
        oldDateItem = getArguments().getString("date");
        oldNotesItem =  getArguments().getString("notes");
        //find and assign resources
        et_fgmtEditTask = (EditText) view.findViewById(R.id.et_fgmtEditTask);
        btn_FgmtEditDate = (Button) view.findViewById(R.id.btn_FgmtEditDate);
        et_fgmtEditNotes =  (EditText) view.findViewById(R.id.et_fgmtEditNotes);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnConfirm = (Button) view.findViewById(R.id.btnConfirm);

        //The spinner
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_edit,getResources().getStringArray(R.array.priorityValues));
        //Getting the instance of AutoCompleteTextView
        fgmtSpinPriotity = (Spinner) view.findViewById(R.id.fgmtSpinPriotity);
        // Initialize and set Adapter
        fgmtSpinPriotity.setAdapter(adapter);

        //When spinner selected
        fgmtSpinPriotity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                etPrioritySelected = adapter.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //do nothing item is already selected
            }
        });
        //The date button  - call date fragment
        btn_FgmtEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialogs pickerDialogs = new PickerDialogs();
                pickerDialogs.show(getFragmentManager(),"date_picker");

                btn_FgmtEditDate = (Button) v.findViewById(R.id.btn_FgmtEditDate);
            }
        });


        //Populate items in layout
        et_fgmtEditTask.setText(oldTaskItem);
        //set selection
        et_fgmtEditTask.setSelection(et_fgmtEditTask.getText().length());
        //set focus
        et_fgmtEditTask.requestFocus();

        fgmtSpinPriotity.setSelection(adapter.getPosition(oldPriorityItem));

        btn_FgmtEditDate.setText(oldDateItem);

        et_fgmtEditNotes.setText(oldNotesItem);



        //on cancel dismiss dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnCancel = (Button) v.findViewById(R.id.btnCancel);
                dismiss();
            }
        });


        //on confirm wirite to Database
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnConfirm = (Button) v.findViewById(R.id.btnConfirm);

                //Retreive values set on fragment
                String taskItem = et_fgmtEditTask.getText().toString();
                String dueDate = (String) btn_FgmtEditDate.getText();
                String priorityItem = etPrioritySelected;
                String taskNotes =  et_fgmtEditNotes.getText().toString();



                handler = new TodoDatabaseHandler(getActivity());

                writeItems( TableData.TableInfo.TABLE_ITEMS_LIST,TableData.TableInfo.ILB, oldTaskItem,taskItem);
                updateItems( TableData.TableInfo.TABLE_ITEMS_LIST,TableData.TableInfo.ILP,TableData.TableInfo.ILB, taskItem,priorityItem);
                updateItems( TableData.TableInfo.TABLE_ITEMS_LIST,TableData.TableInfo.ILD,TableData.TableInfo.ILB, taskItem,dueDate);
                updateItems( TableData.TableInfo.TABLE_ITEMS_LIST,TableData.TableInfo.ILN,TableData.TableInfo.ILB, taskItem,taskNotes);


                Intent i = new Intent(getActivity(), MainActivity.class);  //your class
                startActivity(i);


                Toast.makeText(getActivity(), taskItem+" Updated", Toast.LENGTH_SHORT).show();


            }
        });


    }
    //Write
    void writeItems(String Table, String Column, String oldItem, String newItem) {

        handler = new TodoDatabaseHandler(getActivity());

        try {
            handler.updateEntry(handler,Table,Column, oldItem,newItem);
            Log.d("DB_OPS", "One value updated");

        } catch (Exception e) {
            Log.e("DB_OPS", "SQLiteException:" + e.getMessage());
        }
    }
    //Update
    void updateItems(String Table, String updateColumn, String identifyColumn, String identifier, String newItem) {

        handler = new TodoDatabaseHandler(getActivity());

        try {
            handler.updateEntryByIdentifier(handler,Table,updateColumn, identifyColumn, identifier, newItem);
            Log.d("DB_OPS", "One value updated");

        } catch (Exception e) {
            Log.e("DB_OPS", "SQLiteException:" + e.getMessage());
        }
    }

}
