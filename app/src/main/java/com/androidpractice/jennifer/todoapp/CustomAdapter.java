package com.androidpractice.jennifer.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressWarnings("SpellCheckingInspection")
class CustomAdapter extends ArrayAdapter {

    ArrayList<ToDoItems> todoItems; //All of the items in the list

    CustomAdapter(Context context, ArrayList<ToDoItems> todoItems) {
        super(context, R.layout.custom_row, todoItems);
        this.todoItems = todoItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
        }

        ToDoItems clickedToDoItem = todoItems.get(position); //The item that was clicked on
        TextView todoItem = (TextView) convertView.findViewById(R.id.row_todoItem);
        TextView todoDueDate = (TextView) convertView.findViewById(R.id.row_dueDate);
        todoItem.setText(clickedToDoItem._toDoItem);
        todoDueDate.setText(clickedToDoItem._dueDate);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#E4F4FA")); //Color of odd number rows: extremely light soft blue
        } else {
            convertView.setBackgroundColor(Color.parseColor("#F8FBFD")); //Color of even number rows: soft blue
        }

        String itemImportance = clickedToDoItem._importance;
        if ("Medium".equals(itemImportance)) {
            todoItem.setTextColor(Color.parseColor("#FFA500")); //Orange
        } else if ("High".equals(itemImportance)) {
            todoItem.setTextColor(Color.RED);
        } else {
            todoItem.setTextColor(Color.parseColor("#008000")); //Green
        }

        return convertView;
    }

}
