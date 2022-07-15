package com.example.myactivity.activities.settime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myactivity.R;
import com.example.myactivity.activities.day.DayRecyclerAdapter;
import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;
import com.example.myactivity.stuff.JSONHelper;
import com.example.myactivity.stuff.Utils;

import java.util.ArrayList;
import java.util.List;

public class SetTimeActivity extends AppCompatActivity {
    private List<Project> projects;
    private List<Task> tasks;
    private RecyclerView recyclerView;
    private SetTimeRecyclerAdapter adapter;

    protected void setTaskOfToday() {
        String date = Utils.getToday();

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
        setContentView(R.layout.activity_set_time);
        setTitle("Set time for your tasks");

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
        recyclerView = findViewById(R.id.set_time_recyclerView);
        adapter = new SetTimeRecyclerAdapter( this, tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}