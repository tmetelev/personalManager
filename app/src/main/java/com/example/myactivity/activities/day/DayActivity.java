package com.example.myactivity.activities.day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myactivity.R;
import com.example.myactivity.stuff.JSONHelper;
import com.example.myactivity.stuff.Utils;
import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;

import java.util.ArrayList;
import java.util.List;

public class DayActivity extends AppCompatActivity {
    private List<Project> projects;
    private List<Task> tasks;
    private RecyclerView recyclerView;
    private DayRecyclerAdapter adapter;
    private String date;

    protected void setTaskOfToday() {
//        String date = Utils.getToday();

        tasks = new ArrayList<>();
        for (Project project : projects) {
            List<Task> t = Utils.getTasksOfThisDay(this, project, date);
            if (t.size() != 0)
                tasks.addAll(t);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        date = getIntent().getStringExtra("date");
        setTitle("Timetable");


        projects = Utils.getProjects(this);

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
        adapter = new DayRecyclerAdapter(this, tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTaskOfToday();
        tasks = Utils.sortByTime(tasks);
        adapter.update(tasks);
    }
}