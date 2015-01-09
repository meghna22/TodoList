package com.algonquincollege.megh0011.todolist;

import java.util.List;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Brief description / purpose of the class.
 * 
 * @author Meghna Meghna (megh0011)
 * @version 1.0
 * 
 */

public class MainActivity extends ListActivity {
	public static final String LOGTAG = "Data";
	private static final int ABOUT_DIALOG_ID = 0;
	public long IDs;

	// public ListView listview;
	public ArrayAdapter<todoTable> adapter;
	private Dialog aboutDialog;
	todoDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		IDs = -1;
		// creates the about dialog; in memory but not visible
		aboutDialog = onCreateDialog(ABOUT_DIALOG_ID);

		datasource = new todoDataSource(this);
		datasource.open();

		List<todoTable> values = datasource.getAllComments();
		Log.i(LOGTAG, "Here is your value" + " " + values);
		if (!values.isEmpty()) {

			ArrayAdapter<todoTable> adapter = new ArrayAdapter<todoTable>(this,
					android.R.layout.simple_list_item_1, values);
			setListAdapter(adapter);
			Toast.makeText(this, "Here is your todo list ", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(this, "Sorry the todo list is empty",
					Toast.LENGTH_SHORT).show();
		}

		Button next = (Button) findViewById(R.id.add);
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(),
						CreateTodo.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		Button update = (Button) findViewById(R.id.update);
		update.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (IDs != -1) {
					todoTable todotable = null;
					todotable = (todoTable) getListAdapter().getItem((int) IDs);
					long databaseID = todotable.gettodoID();
					Intent intent = new Intent(getApplicationContext(),
							UpdateTodo.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("UpdatedData", databaseID);
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this,
							"Sorry there is nothing to update",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// String selectedLab = ((TextView) view).getText().toString();
				IDs = id;
			}
		});

		Button delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				@SuppressWarnings("unchecked")
				ArrayAdapter<todoTable> adapter = (ArrayAdapter<todoTable>) getListAdapter();
				// listview.getAdapter();
				if (IDs > -1) {
					todoTable todotable = null;
					todotable = (todoTable) getListAdapter().getItem((int) IDs);
					datasource.deleteTable(todotable);
					adapter.remove(todotable);
					IDs = -1;
				} else {
					Toast.makeText(MainActivity.this,
							"Sorry there is nothing to delete",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		ListView listView1 = getListView();
		listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				@SuppressWarnings("unchecked")
				ArrayAdapter<todoTable> adapter = (ArrayAdapter<todoTable>) getListAdapter();
				todoTable selectedValue = (todoTable) getListAdapter().getItem(
						position);
				adapter.remove(selectedValue);
				if (position > 0) {
					adapter.insert(selectedValue, position - 1);
				} else {
					adapter.insert(selectedValue, parent.getCount());
				}
				return false;
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Builder dialogBuilder = new AlertDialog.Builder(this);

		switch (id) {

		// Create About dialog
		case ABOUT_DIALOG_ID:
			// Notice: message chaining
			dialogBuilder.setCancelable(false).setTitle(R.string.action_about)
					.setMessage(R.string.author);
			// Dismiss the about dialog when the OK button is clicked.
			// The about dialog is still in memory; just not visible.
			// (Implementation: anonymous inner class)
			dialogBuilder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int button) {
							dialog.dismiss();
						}
					});
		}

		return dialogBuilder.create();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
			aboutDialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
