package com.algonquincollege.megh0011.todolist;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

public class todoDataSource {

	public static final String LOGTAG = "todoDataSource";

	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;

	private static final String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_TEXT };

	public todoDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public todoTable createTable(String todo) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TEXT, todo);
		long insertId = database
				.insert(MySQLiteHelper.TABLE_NAME, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		Log.i(LOGTAG, "Hey you inserted the item" + " " + values);
		cursor.moveToFirst();
		todoTable newtodoTable = cursorTotodo(cursor);
		cursor.close();
		return newtodoTable;
	}

	public void deleteTable(todoTable todo) {
		long id = todo.gettodoID();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
		Log.i(LOGTAG, "Hey you have deleted the items" + " " + todo);
	}

	public void updateTable(long id, String todo) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TEXT, todo);

		database.update(MySQLiteHelper.TABLE_NAME, values,
				MySQLiteHelper.COLUMN_ID + " = " + id, null);

	}

	public List<todoTable> getAllComments() {
		List<todoTable> comments = new ArrayList<todoTable>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			todoTable comment = cursorTotodo(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return comments;
	}

	private todoTable cursorTotodo(Cursor cursor) {
		todoTable todo = new todoTable();
		todo.settodoID(cursor.getLong(0));
		todo.settodo(cursor.getString(1));
		return todo;
	}
}
