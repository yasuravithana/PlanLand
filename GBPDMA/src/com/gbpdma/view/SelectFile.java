package com.gbpdma.view;

import java.io.File;
import java.util.ArrayList;

import com.gbpdma.R;
import com.gbpdma.R.layout;
import com.gbpdma.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author yasuravithana
 *
 */
public class SelectFile extends ListActivity {
  //this class is related to the activity which allows the user to select a file to open
    
    ListView list;//list  that displays the files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_file);
	list = getListView();

	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();

	// DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
	ArrayAdapter<String> adapter;

	adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, listItems);//creating the adapter
	setListAdapter(adapter);//set the list adapter

	File f = getFilesDir();//get the current directory as a file

	if (f.exists() && f.canRead()) {
	    String[] list = f.list();//get a list of file names in the directory
	    for (String s : list) {
		//adding XML files to the list view 
		if (s.endsWith(".xml")) {
		    listItems.add(s.substring(0, s.length() - 4));
		}
	    }
	    adapter.notifyDataSetChanged();//mark that the list has changed
	}

	//Bind onclick event handler
	list.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		startDraw((String) (list.getItemAtPosition(position)));//call method to start the map drawing activity
	    }
	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.select_file, menu);
	return true;
    }

    //method to start the map drawing activity
    private void startDraw(String fileName) {
	Intent intent = new Intent(this, StartDraw.class);
	Bundle b = new Bundle();//used to pass additional parameters
	b.putString("file", fileName);//send map name as an additional parameter
	intent.putExtras(b);
	startActivity(intent);
    }

}
