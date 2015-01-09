package com.algonquincollege.megh0011.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Brief description / purpose of the class.
 * 
 * @author Meghna Meghna (megh0011)
 * @version 1.0
 * 
 */
public class UpdateTodo extends Activity {
	SQLiteDatabase database;
	public static final String LOGTAG = "DataSource";
	private static final int ABOUT_DIALOG_ID = 0;

	// public ListView listview;
	private Dialog aboutDialog;
	todoDataSource datasource;
	private long updateText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatetodo);

		// creates the about dialog; in memory but not visible
		aboutDialog = onCreateDialog(ABOUT_DIALOG_ID);

		datasource = new todoDataSource(this);
		datasource.open();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			updateText = bundle.getLong("UpdatedData");
		}

		Button update1 = (Button) findViewById(R.id.updated);
		update1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				EditText updatetodo = (EditText) findViewById(R.id.editText1);
				String updateValue = updatetodo.getText().toString();
				if (!updateValue.isEmpty()) {

					datasource.updateTable(updateText, updateValue);
					Toast.makeText(UpdateTodo.this,
							"Hey your list item is updated ",
							Toast.LENGTH_SHORT).show();
					updatetodo.setText("");
					Log.i(LOGTAG, "Here is your updated value" + " "
							+ updateValue);
				}
			}
		});
		Button cancel = (Button) findViewById(R.id.cancelled);
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(intent);

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
