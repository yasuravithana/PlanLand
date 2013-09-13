package com.gbpdma.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.gbpdma.R;
import com.gbpdma.R.layout;
import com.gbpdma.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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
    int indexWhenLongClicked;

    // LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    // DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_file);
	list = getListView();

	adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, listItems);//creating the adapter
	setListAdapter(adapter);//set the list adapter

	fillList();//populate the ListView

	//Bind onclick event handler
	list.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		startDraw((String) (list.getItemAtPosition(position)));//call method to start the map drawing activity
	    }
	});

	registerForContextMenu(list);
    }
    
    //method to populate ListView
    private void fillList()
    {
	File f = getFilesDir();//get the current directory as a file

	listItems.clear();
	
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	switch(item.getItemId())
	{
	case R.id.selectFileMenu_delete:
	    File file = new File(getFilesDir()+"/"+(String)list.getItemAtPosition(info.position)+".xml");
	    file.delete();
	    fillList();
	    break;
	    }
	return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	getMenuInflater().inflate(R.menu.select_file_menu, menu);
	super.onCreateContextMenu(menu, v, menuInfo);
    }
    

}
