package com.iryarte.simpletodoextended;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by miguel on 6/12/16.
 */
public class TodoDatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    String CREATE_ITEMS_LIST_TABLE = "CREATE TABLE " + TableData.TableInfo.TABLE_ITEMS_LIST +
            "(" +
            TableData.TableInfo.IID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TableData.TableInfo.ILB + " TEXT NOT NULL, " +
            TableData.TableInfo.ILP + " TEXT NOT NULL," + // Define a primary key
            TableData.TableInfo.ILD + " TEXT NOT NULL," + // Define a primary key
            TableData.TableInfo.ILN + " TEXT " +

            ")";

    public TodoDatabaseHandler(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("DB_OPS", "Database created: "+TableData.TableInfo.DATABASE_NAME);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_ITEMS_LIST_TABLE);
        Log.d("DB_OPS", TableData.TableInfo.TABLE_ITEMS_LIST +" T_I_L Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_ITEMS_LIST);
        onCreate(db);

    }


    public void newItemEntry(TodoDatabaseHandler dop, String table_name, String new_bodyItem_name, String new_bodyItem_value, String new_priorityItem_name, String new_priorityValue,String new_dateItem_name, String new_dateItem_Value, String new_noteItem_name, String new_noteItem_Value )
    {
        SQLiteDatabase SQ = dop.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(new_bodyItem_name, new_bodyItem_value);
        cv.put(new_priorityItem_name,new_priorityValue);
        cv.put(new_dateItem_name,new_dateItem_Value);
        cv.put(new_noteItem_name,new_noteItem_Value);

        long k = SQ.insert(table_name, null, cv);
        Log.d("DB_OPS", "One row inserted");
    }

    public void updateEntry(TodoDatabaseHandler dop, String tableName, String dbItemName, String oldItemName, String newItemName)
    {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        String selection = dbItemName + " LIKE ?";
        String args[] = {oldItemName};
        ContentValues values = new ContentValues();
        values.put(dbItemName,  newItemName);
        SQ.update(tableName, values, selection, args);
        Log.d("DB_OPS", "One row updated");
    }

    public void updateEntryByIdentifier(TodoDatabaseHandler dop, String tableName, String dbItemName, String dbIdentifierName, String identifier, String newItemName)
    {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        String selection = dbIdentifierName + " LIKE ?";
        String args[] = {identifier};
        ContentValues values = new ContentValues();
        values.put(dbItemName,  newItemName);
        SQ.update(tableName, values, selection, args);
        Log.d("DB_OPS", "One row updated");
    }

    public void taskRemove (TodoDatabaseHandler dop, String tableName, String newItemName, String newItemValue)
    {
        String selection = newItemName + " LIKE ? ";
        String args[] = {newItemValue};
        SQLiteDatabase SQ = dop.getWritableDatabase();
        SQ.delete(tableName, selection, args);
        Log.d("DB_OPS", "One row deleted");
    }

    //funciton used for flagging
    public Boolean CheckExist(TodoDatabaseHandler dop, String tableName){

        Boolean flag;
        SQLiteDatabase SQ = dop.getReadableDatabase();
        try
        {
            SQ.query(tableName, null, null,null, null, null, null, null);
            flag = true;
            Log.d("DB_OPS", "DB Exists");
        }
        catch (Exception e) {
            Log.d("DB_OPS", "DB does not Exist");
            flag = false;
        }

        return flag;

    }

    public Cursor getCursor(TodoDatabaseHandler dop, String tableName){

        Cursor todoCursor;
        SQLiteDatabase SQ = dop.getWritableDatabase();
        todoCursor=SQ.rawQuery("SELECT * FROM " + tableName, null);

        return todoCursor;
    }

    //function used for troubleshooting
    public long numRows(TodoDatabaseHandler dop, String tableName){

        long numRows;
        SQLiteDatabase SQ = dop.getReadableDatabase();
        numRows = DatabaseUtils.queryNumEntries(SQ, tableName);

        return numRows;
    }

    public String retriveValueByPos(TodoDatabaseHandler dop, String tableName, String desiredColumn1, int pos){

        //ArrayList<String> items = new ArrayList<String>();
        String item="";

        Cursor CR;
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String select []= {"*"};
         try {
            CR = SQ.query(tableName, select, null, null, null, null, null, null);
            int i=0;
            while (i<pos+1) {
                CR.moveToNext();
                item = CR.getString(CR.getColumnIndex(desiredColumn1));
                //items.add(item);
                i++;
            }
        }
        catch (Exception e){
            Log.d("DB_OPS", "Unable to retreive array");
        }
        return  item;
    }


}
