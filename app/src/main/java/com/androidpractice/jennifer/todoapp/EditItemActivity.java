package com.androidpractice.jennifer.todoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("SpellCheckingInspection")
public class EditItemActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    //Info of edited item
    EditText editItem;
    TextView string_date;

    //Info of the item to be changed
    int changeItemPosition;
    String changeItem;
    String changeDate;
    String changeImportance;

    //Info of all of the items is the list
    ArrayList<String> existingItems;
    ArrayList<String> existingDueDates;

    Button btn_selectDate;
    int day, month, year;
    int dayFinal, yearFinal;
    String monthFinal;
    Spinner spinner;
    ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Info ot the item to be changed
        changeItemPosition = getIntent().getIntExtra("changeItemPosition", -1);
        changeItem = getIntent().getStringExtra("changeItem");
        changeDate = getIntent().getStringExtra("changeDate");
        changeImportance = getIntent().getStringExtra("changeImportance");

        //Info of all of the items in the list
        existingItems = getIntent().getStringArrayListExtra("existingItems");
        existingDueDates = getIntent().getStringArrayListExtra("existingDueDates");

        //The EditText field
        editItem = (EditText) findViewById(R.id.editItem);
        editItem.setText(changeItem);
        editItem.setSelection(editItem.getText().length());

        //The TextView field for due date
        string_date = (TextView) findViewById(R.id.string_date);
        string_date.setText(changeDate);

        //The dropdownlist for importance
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.importance_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        if ("Medium".equals(changeImportance)) {
            spinner.setSelection(1);
        } else if ("High".equals(changeImportance)) {
            spinner.setSelection(2);
        } else { //Low
            spinner.setSelection(0);
        }

        //Clicking on the select button will show a date picking dialog
        btn_selectDate = (Button) findViewById(R.id.btn_selectDate);
        btn_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditItemActivity.this, EditItemActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    /**
     * Executed when the save button was clicked. This method checks if the same item already existed and returns the edited info to the MainActivity
     */
    public void onSaveItem(View view) {

        String editItemContent = editItem.getText().toString();
        String selectedDateContent = string_date.getText().toString();
        String selectedImportance = spinner.getSelectedItem().toString();

        if (editItemContent != null && editItemContent.length() > 0) {
            String changeItemAndDate = changeItem + changeDate; //The content and due date of the item to be changed
            String editedItemAndDate = editItemContent + selectedDateContent; //The content and due date of the item that was changed

            //Show toast message when the edited item has the same item content and due date with another existing item in the list, but not when the user is editing the item itself
            for (int i = 0; i < existingItems.size(); i++) {
                String existingItemsAndDates = existingItems.get(i) + existingDueDates.get(i); //The list of contents and due dates of all of the existing items
                if (!editedItemAndDate.equals(changeItemAndDate) && editedItemAndDate.equals(existingItemsAndDates)) {
                    Toast.makeText(this, "Edited Item Already Existed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Intent data = new Intent();
            data.putExtra("changeItemPosition", changeItemPosition);
            data.putExtra("changeItem", changeItem);
            data.putExtra("changeDate", changeDate);
            data.putExtra("changeImportance", changeImportance);
            data.putExtra("editItemContent", editItemContent);
            data.putExtra("selectedDateContent", selectedDateContent);
            data.putExtra("selectedImportance", selectedImportance);
            setResult(RESULT_OK, data);
            finish();

        } else { //The item content is required, however the due date can be blank
            Toast.makeText(this, "Edited Content Can Not Be Blank", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Sets the due date TextView field into the "Month Date, Year" format such as "Feb 13, 2017"
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dayFinal = dayOfMonth;
        yearFinal = year;

        switch (month) {
            case 0:
                monthFinal = "Jan";
                break;
            case 1:
                monthFinal = "Feb";
                break;
            case 2:
                monthFinal = "Mar";
                break;
            case 3:
                monthFinal = "Apr";
                break;
            case 4:
                monthFinal = "May";
                break;
            case 5:
                monthFinal = "Jun";
                break;
            case 6:
                monthFinal = "Jul";
                break;
            case 7:
                monthFinal = "Aug";
                break;
            case 8:
                monthFinal = "Sep";
                break;
            case 9:
                monthFinal = "Oct";
                break;
            case 10:
                monthFinal = "Nov";
                break;
            case 11:
                monthFinal = "Dec";
                break;
        }

        string_date.setText(monthFinal + " " + dayFinal + ", " + year);

    }

}
