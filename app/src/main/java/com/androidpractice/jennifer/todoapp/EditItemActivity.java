package com.androidpractice.jennifer.todoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditItemActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    EditText editItem;
    int changeItemPosition;
    String changeItem;
    Button btn_selectDate;
    TextView string_date;
    int day, month, year;
    int dayFinal, yearFinal;
    String monthFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        changeItemPosition = getIntent().getIntExtra("changeItemPosition", -1);
        changeItem = getIntent().getStringExtra("changeItem");
        editItem = (EditText) findViewById(R.id.editItem);
        editItem.setText(changeItem);
        editItem.setSelection(editItem.getText().length());

        btn_selectDate = (Button) findViewById(R.id.btn_selectDate);
        string_date = (TextView) findViewById(R.id.string_date);

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

    public void onSaveItem(View view) {
        String editItemContent = editItem.getText().toString();
        String selectedDateContent = string_date.getText().toString();
        if (editItemContent != null && editItemContent.length() > 0) {
            Intent data = new Intent();
            data.putExtra("changeItemPosition", changeItemPosition);
            data.putExtra("changeItem", editItemContent);
            data.putExtra("selectedDateContent", selectedDateContent);
            setResult(RESULT_OK, data);
            finish();
        } else {
            Toast.makeText(this, "Edited Content Can Not Be Blank", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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

        dayFinal = dayOfMonth;
        string_date.setText(monthFinal + " " + dayFinal + " , " + yearFinal);

    }
}
