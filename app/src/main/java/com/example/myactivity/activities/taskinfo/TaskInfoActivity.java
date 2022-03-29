package com.example.myactivity.activities.taskinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.misc.JSONHelper;
import com.example.myactivity.structures.Task;

import java.util.List;

public class TaskInfoActivity extends AppCompatActivity {

    private Task task = new Task();
    private List<Task> tasks;
    private int pos;
    private String dataFileName;
    private TextView name;
    private TextView date;
    private TextView time;
    private Switch statusSwitch;
    private EditText comment;
    private Button saveButton;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        name = (TextView) findViewById(R.id.ti_name);
        date = (TextView) findViewById(R.id.ti_date);
        time = (TextView) findViewById(R.id.ti_time);
        comment = (EditText) findViewById(R.id.ti_comment);
        saveButton = (Button) findViewById(R.id.ti_save_button);
        statusSwitch = (Switch) findViewById(R.id.ti_switch);

        Intent intent = getIntent();
        dataFileName = intent.getStringExtra("name");
        pos = intent.getIntExtra("pos", 0);
        tasks = JSONHelper.importFromJSON(this, dataFileName);
        task = tasks.get(pos);

        setTitle(task.getName() + "'s task");

        name.setText(task.getName());
        date.setText(task.getDate());
        time.setText(task.getTime());
        statusSwitch.setChecked(task.getStatus());
        comment.setText(task.getComment());

        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                task.setStatus(b);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setComment(comment.getText().toString());
                tasks.set(pos, task);
                boolean res = JSONHelper.exportToJSON(context, tasks, dataFileName);
                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();
            }
        });
    }
}