package com.example.myactivity.activities.taskinfo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.structures.Task;

public class TaskInfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView date;
    private TextView time;
    private Switch statusSwitch;
    private boolean status;
    private EditText comment;
    private Button saveButton;

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




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String com = comment.getText().toString();
//                TaskInfoActivity
            }
        });
    }
}