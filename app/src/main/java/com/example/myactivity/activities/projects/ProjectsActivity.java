package com.example.myactivity.activities.projects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.stuff.JSONHelper;
import com.example.myactivity.stuff.Utils;
import com.example.myactivity.structures.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsActivity extends AppCompatActivity {

    private Button addButton;
    private EditText newNameText;
    private RecyclerView recyclerView;
    private ProjectsRecyclerAdapter adapter;
    private List<Project> projects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        setTitle("Projects");

        addButton = findViewById(R.id.pr_add_button);
        newNameText = findViewById(R.id.pr_new_pr_name);

        projects = Utils.getProjects(this);
        recyclerView = findViewById(R.id.pr_recyclerView);
        adapter = new ProjectsRecyclerAdapter(projects, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void add(View view) {
        String text = newNameText.getText().toString();
        if (text.length() > 0 && text != "projects") {
            projects = adapter.getProjects();
            Project project = new Project(text);
            projects.add(project);
            boolean result = JSONHelper.exportToJSON(this, projects);
//        if(result){
//            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
//        }
            recyclerUpdate();
            newNameText.setText("");
        } else {
            Toast.makeText(this, "Name your project!", Toast.LENGTH_LONG).show();
        }
    }

    public void recyclerUpdate() {
        adapter.updateProjects(projects);
        adapter.notifyDataSetChanged();
    }
}