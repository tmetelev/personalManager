package com.example.myactivity.activities.taskinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.stuff.JSONHelper;
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
    private TimePicker timePicker;

    private int hour = 12, minute = 0;

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

        setTitle(task.getTag() + "'s task");

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

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater li = LayoutInflater.from(context);
                View windowView = li.inflate(R.layout.set_time_dialog, null);
                builder.setView(windowView);
                timePicker = windowView.findViewById(R.id.set_time_timePicker);
                timePicker.setHour(12);
                timePicker.setMinute(0);
                timePicker.setIs24HourView(true);
                builder
                        .setTitle("Set task time")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hour = timePicker.getHour();
                                minute = timePicker.getMinute();
                                String hourString = Integer.toString(hour);
                                if (hourString.length() == 1) {
                                    hourString = "0" + hourString;
                                }
                                String minuteString = Integer.toString(minute);
                                if (minuteString.length() == 1) {
                                    minuteString = "0" + minuteString;
                                }
                                task.setTime(hourString + minuteString);
                                dialogInterface.cancel();
                                time.setText(task.getTime());
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
        });
    }
}