package com.algonquincollege.megh0011.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Brief description / purpose of the class.
 * 
 * @author Meghna Meghna (megh0011)
 * @version 1.0
 * 
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "MySQLiteHelper";
	
	public static final String DATABASE_NAME = "todolist.db";
	public static final String TABLE_NAME = "todolist";
	public static final String COLUMN_ID = "todoID";
	public static final String COLUMN_TEXT = "todo";
	

   private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
   
   
   private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_NAME + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TEXT + " TEXT NOT NULL )";
			
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		Log.i(LOGTAG, "Table has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
	    		"Upgrading database from version " + oldVersion + " to "
	    					+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

} 


