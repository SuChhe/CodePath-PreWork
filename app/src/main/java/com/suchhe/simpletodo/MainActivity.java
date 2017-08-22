package com.suchhe.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {
    ListView lvItems;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    private int editPos;

    private final int REQUEST_CODE = 200; // Edit Item, launching an activity for a result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItem);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem (View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void setupListViewListener (){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapter, View item,int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                editPos = pos;
                launchCompositeView(items.get(pos));
            }
        });
    }

    public void launchCompositeView(String item) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("item", item);
        startActivityForResult(i, REQUEST_CODE);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File (filesDir, "toDoList.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File (filesDir, "toDoList.txt");
        try {
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String editedItem = data.getExtras().getString("editedItem");
            items.set(editPos,editedItem);
            writeItems();
            itemsAdapter.notifyDataSetChanged();
        }
    }

}