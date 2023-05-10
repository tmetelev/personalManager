package com.example.myactivity.activities.oneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.stuff.JSONHelper;
import com.example.myactivity.structures.Task;

import java.util.List;

public class OneprojectActivity extends AppCompatActivity {
    private Button addButton;
    private Button clearButton;
    private EditText newNameText;
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private OneprojectRecyclerAdapter adapter;
    private String dataFileName = "";
    private String selectedDate = "010122";
    private Task addableTask;
    private final Context context = this;
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneproject);
        projectName = getIntent().getStringExtra("name");
        setTitle(projectName);

        dataFileName = getIntent().getExtras().getString("file");

        addButton = findViewById(R.id.op_add_button);

        recyclerView = findViewById(R.id.op_recyclerView);
        adapter = new OneprojectRecyclerAdapter(dataFileName, this);
        adapter.update();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void add(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater li = LayoutInflater.from(this);
        View windowView = li.inflate(R.layout.oneproject_add_dialog, null);
        builder.setView(windowView);
        newNameText = windowView.findViewById(R.id.op_add_name);
        calendarView = windowView.findViewById(R.id.op_add_calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String mYear = Integer.toString(year).substring(2, 4);;
                String mMonth = Integer.toString(month + 1);
                if (mMonth.length() == 1)
                    mMonth = "0" + mMonth;
                String mDay = Integer.toString(dayOfMonth);
                if (mDay.length() == 1)
                    mDay = "0" + mDay;
                selectedDate = mDay + mMonth + mYear;
//                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
            }
        });
        builder
                .setTitle("Add new task")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = newNameText.getText().toString();
                        addableTask = new Task(text, selectedDate, projectName);
                        if (text.length() > 0){
                            List<Task> tasks = JSONHelper.importFromJSON(context, dataFileName);
                            tasks.add(addableTask);
                            JSONHelper.exportToJSON(context, tasks, dataFileName);
                            adapter.update();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Name your task!", Toast.LENGTH_LONG).show();
                        }
//                        if(result){
//                            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
//                        }
//                        else{
//                            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
//                        }
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void clear(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update();
        adapter.notifyDataSetChanged();
    }
}