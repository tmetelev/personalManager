package com.example.myactivity.activities.day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.activities.calendar.CalendarRecyclerAdapter;
import com.example.myactivity.misc.JSONHelper;
import com.example.myactivity.misc.Utilities;
import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayActivity extends AppCompatActivity {
    private List<Project> projects;
    private List<Task> tasks;
    private RecyclerView recyclerView;
    private DayRecyclerAdapter adapter;

    protected void setTaskOfToday() {
        String date = Utilities.getToday();

        tasks = new ArrayList<>();
        for (Project project : projects) {
            List<Task> t = Utilities.getTasksOfThisDay(this, project, date);
            if (t.size() != 0)
                tasks.addAll(t);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        setTitle("Timetable");

        projects = Utilities.getProjects(this);

        for (Project project : projects){
            List<Task> tasksBuf = JSONHelper.importFromJSON(this, project.getDataFileName());
            for (int i = 0; i < tasksBuf.size(); i++) {
                tasksBuf.get(i).setId(i);
            }
            JSONHelper.exportToJSON(this, tasksBuf, project.getDataFileName());
        }

        setTaskOfToday();

//        Toast.makeText(this, Integer.toString(tasks.size()), Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.day_recyclerView);
        adapter = new DayRecyclerAdapter( this, tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTaskOfToday();
        adapter.update(tasks);
    }
}