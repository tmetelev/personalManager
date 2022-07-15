package com.example.myactivity.activities.mainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.activities.calendar.CalendarActivity;
import com.example.myactivity.activities.day.DayActivity;
import com.example.myactivity.activities.projects.ProjectsActivity;
import com.example.myactivity.misc.Utilities;

public class MainMenuActivity extends AppCompatActivity {
    private boolean firstEntranceOfToday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        int todayDate = Integer.parseInt(Utilities.getToday());
        int lastDate = Integer.parseInt(Utilities.getLastEntranceDate(this));

        if (todayDate != lastDate) {
            Toast.makeText(this, "base", Toast.LENGTH_SHORT).show();
            firstEntranceOfToday = true;
        }
        Utilities.updateLastEntranceDate(this);

    }

    public void goToProjects(View view) {
        Intent intent = new Intent(this, ProjectsActivity.class);
        startActivity(intent);
    }

    public void goToCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void goToTimetable(View view) {
        Intent intent = new Intent(this, DayActivity.class);
        startActivity(intent);
    }
}