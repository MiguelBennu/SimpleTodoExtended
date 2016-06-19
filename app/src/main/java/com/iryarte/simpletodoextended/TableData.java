package com.iryarte.simpletodoextended;

import android.provider.BaseColumns;

/**
 * Created by miguel on 4/28/16.
 */
public class TableData {

    public TableData(){

    }

    public static abstract class  TableInfo implements BaseColumns {

  //Databases
        public static final String DATABASE_NAME = "todo_extended";


 //Tables list
        public static final String TABLE_TODO_LIST = "item_list";//Table1

 //For the adapter
        public static final String TABLE_ITEMS_LIST = "todo_items";

 //Variables for tables
        //TABLE_TODO_LIST = item_list
        public static final String INAM = "item_name";//TEXT

        //For the adapter
        public static final String IID = "_id";//TEXT
        public static final String ILB = "body";//TEXT
        public static final String ILP = "priority";//TEXT
        public static final String ILD = "date";//int
        public static final String ILN = "notes";//TEXT
    }


}
