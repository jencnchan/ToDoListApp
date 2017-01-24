package com.androidpractice.jennifer.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("changeItemPosition", position);
                i.putExtra("changeItem", (String) lvItems.getItemAtPosition(position));
                startActivityForResult(i, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String changeItem = data.getExtras().getString("changeItem");
            int changeItemPosition = data.getExtras().getInt("changeItemPosition", -1);
            aToDoAdapter.remove(aToDoAdapter.getItem(changeItemPosition));
            aToDoAdapter.insert(changeItem, changeItemPosition);
            writeItems();
        }

    }

    private void readItems() {
        try {
            File filesDir = getFilesDir();
            File file = new File(filesDir, "todo.txt");
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        try {
            File filesDir = getFilesDir();
            File file = new File(filesDir, "todo.txt");
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddIem(View view) {
        EditText etEditText = (EditText) findViewById(R.id.etEditText);
        String itemText = etEditText.getText().toString();
        if (itemText != null && itemText.length() > 0) {
            aToDoAdapter.add(itemText);
            etEditText.setText("");
            writeItems();
        } else {
            Toast.makeText(this, "Please Enter New Item", Toast.LENGTH_SHORT).show();
        }
    }

}
