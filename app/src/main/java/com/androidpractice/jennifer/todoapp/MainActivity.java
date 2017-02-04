package com.androidpractice.jennifer.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressWarnings("SpellCheckingInspection")
public class MainActivity extends Activity {

    ArrayList<ToDoItems> todoItemsList;
    CustomAdapter aToDoAdapter;
    PostsDatabaseHelper databaseHelper;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = PostsDatabaseHelper.getInstance(this);

        todoItemsList = databaseHelper.getAllToDoItems(); //readItems
        aToDoAdapter = new CustomAdapter(this, todoItemsList);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        /**
         *  Executed when long clicking on an item in the list, this will delete the item
         */
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItems deltodoitem = (ToDoItems) lvItems.getItemAtPosition(position);
                databaseHelper.deleteToDo(deltodoitem);
                todoItemsList.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });

        /**
         *  Executed when clicking on an item in the list, this will bring the user to the EditItemActivity to edit the item content, due date, and importance
         */
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                ToDoItems clickedtodoitem = (ToDoItems) lvItems.getItemAtPosition(position);

                ArrayList<String> items = new ArrayList();
                ArrayList<String> dueDates = new ArrayList();
                for (ToDoItems theItem : todoItemsList) {
                    items.add(theItem._toDoItem);
                    dueDates.add(theItem._dueDate);
                }

                i.putStringArrayListExtra("existingItems", items);  //All items in the list
                i.putStringArrayListExtra("existingDueDates", dueDates);  //All due dates in the list
                i.putExtra("changeItemPosition", position); //The position of the item to be changed
                i.putExtra("changeItem", clickedtodoitem._toDoItem); //The item text of the item to be changed
                i.putExtra("changeDate", clickedtodoitem._dueDate); //The due date of the item to be changed
                i.putExtra("changeImportance", clickedtodoitem._importance); //The importance of the item to be changed

                startActivityForResult(i, REQUEST_CODE);
            }
        });

    }

    /**
     * Executed when returning to the MainActivity page from the EditItemActivity page, this method stores the edited info into the database and show the edited content in the list
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String changeItem = data.getExtras().getString("changeItem");
            String changeDate = data.getExtras().getString("changeDate");
            String changeImportance = data.getExtras().getString("changeImportance");
            String editItemContent = data.getExtras().getString("editItemContent");
            int changeItemPosition = data.getExtras().getInt("changeItemPosition", -1);
            String selectedDateContent = data.getExtras().getString("selectedDateContent");
            String selectedImportance = data.getExtras().getString("selectedImportance");

            ToDoItems oldtodoitem = new ToDoItems(changeItem, changeDate, changeImportance);
            ToDoItems edittodoitem = new ToDoItems(editItemContent, selectedDateContent, selectedImportance);
            databaseHelper.editToDo(oldtodoitem, edittodoitem);

            aToDoAdapter.remove(aToDoAdapter.getItem(changeItemPosition));
            aToDoAdapter.insert(edittodoitem, changeItemPosition);
        }

    }

    /**
     * Executed when adding new item in the main activity
     */
    public void onAddIem(View view) {

        EditText etEditText = (EditText) findViewById(R.id.etEditText);
        String itemText = etEditText.getText().toString();

        if (itemText != null && itemText.length() > 0) {
            for (ToDoItems theItem : todoItemsList) {
                if (itemText.equals(theItem._toDoItem + theItem._dueDate)) {
                    Toast.makeText(this, "Entered Item Already Existed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            ToDoItems newToDoItem = new ToDoItems(itemText, "", "Low"); //Default item added has no due date and low importance
            aToDoAdapter.add(newToDoItem);
            etEditText.setText("");
            databaseHelper.addToDoItem(newToDoItem);

        } else {
            Toast.makeText(this, "Please Enter New Item", Toast.LENGTH_SHORT).show();
        }

    }

}
