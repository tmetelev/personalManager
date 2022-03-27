package com.example.myactivity.activities.oneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private RecyclerView recyclerView;
    private OneprojectRecyclerAdapter adapter;
    private List<Task> tasks = new ArrayList<>();
    private String dataFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneproject);

        dataFileName = getIntent().getExtras().getString("file");

        addButton = findViewById(R.id.op_add_button);
        newNameText = findViewById(R.id.op_new_t_name);

        open();
        recyclerView = findViewById(R.id.op_recyclerView);
        adapter = new OneprojectRecyclerAdapter(tasks, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void add(View view) {
        tasks = adapter.getTasks();
        Task project = new Task(newNameText.getText().toString(), "121122", "1200");
        tasks.add(project);
        boolean result = JSONHelper.exportToJSON(this, tasks, dataFileName);
        if(result){
//            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
//            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
        recyclerUpdate();
        newNameText.setText("");
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