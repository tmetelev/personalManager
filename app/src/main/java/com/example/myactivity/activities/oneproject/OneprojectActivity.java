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
import com.example.myactivity.activities.projects.ProjectsRecyclerAdapter;
import com.example.myactivity.misc.JSONHelper;
import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;

import java.util.ArrayList;
import java.util.List;

public class OneprojectActivity extends AppCompatActivity {
    private Button addButton;
    private EditText newNameText;
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private OneprojectRecyclerAdapter adapter;
    private List<Task> tasks = new ArrayList<>();
    private String dataFileName = "";
    private String selectedDate = "010122";
    private Task addableTask;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneproject);

        dataFileName = getIntent().getExtras().getString("file");

        addButton = findViewById(R.id.op_add_button);

        open();
        recyclerView = findViewById(R.id.op_recyclerView);
        adapter = new OneprojectRecyclerAdapter(tasks, dataFileName, this);
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
                        addableTask = new Task(newNameText.getText().toString(), selectedDate, "0000");
                        tasks = adapter.getTasks();
                        tasks.add(addableTask);
                        boolean result = JSONHelper.exportToJSON(context, tasks, dataFileName);
                        if(result){
//            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
                        }
                        else{
//            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
                        }
                        recyclerUpdate();
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

    public void open() {
        tasks = JSONHelper.importFromJSON(this, dataFileName);
        if(tasks !=null){
//            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
//            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
            tasks = new ArrayList<>();
        }
    }

    public void recyclerUpdate() {
        adapter.updateTasks(tasks);
        adapter.notifyDataSetChanged();
    }
}