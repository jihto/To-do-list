package com.example.final_project.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.DatabaseHelper;
import com.example.final_project.R;

import java.util.Calendar;


public class CreateListFragment extends Fragment {
    private DatePickerDialog datePickerDialog;
    private Button btnDate, btnToday;
    private RadioGroup radioGroup;
    private ImageButton btnCreate;
    private DatabaseHelper databaseHelper;
private EditText editTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_list, container, false);

        // Find views within the fragment's layout
        radioGroup = rootView.findViewById(R.id.radioGroup);
        editTitle = rootView.findViewById(R.id.editTitle);
        Button addButton = rootView.findViewById(R.id.btnSubTask);
        btnDate = rootView.findViewById(R.id.btnDatePicker);
        btnToday = rootView.findViewById(R.id.btnToday);
        btnCreate = rootView.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                createNewTask(editTitle.toString(), editTitle.toString());
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRadioButton();
            }
        });

        initDatePicker();
        btnDate.setText(getTodaysDate());
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToday.setTextColor(getResources().getColorStateList(R.color.outline_primary));
                btnDate.setTextColor(Color.BLACK);
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToday.setTextColor(Color.BLACK);
                btnDate.setTextColor(getResources().getColorStateList(R.color.outline_primary));
                datePickerDialog.show();
            }
        });
        return rootView;
    }

    private void createNewTask(String title,String task){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", task);

        long result = db.insert("tasks", null, values);

        if (result != -1) {
            Toast.makeText(getContext(), "Create new task successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Create new task failed", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(requireContext(), style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void todayPicker(View view) {
        // Implementation for todayPicker
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void addNewRadioButton() {
        LinearLayout newLinearLayout = new LinearLayout(requireContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10); // Add margins (adjust values as needed)
        newLinearLayout.setLayoutParams(layoutParams);
        newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create a new RadioButton
        RadioButton radioButton = new RadioButton(requireContext());
        radioButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        radioButton.setId(View.generateViewId()); // Generate a unique ID for the RadioButton

        // Create a new EditText
        EditText editText = new EditText(requireContext());
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint("Sub task");
        editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_edittext_bg));
        editText.setPadding(20, 20, 20, 20);
        // Set other attributes for the EditText as needed

        // Add RadioButton and EditText to the new LinearLayout
        newLinearLayout.addView(radioButton);
        newLinearLayout.addView(editText);

        // Add the new LinearLayout to the parent layout
        radioGroup.addView(newLinearLayout);
    }
}