package com.example.myactivity.activities.mainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myactivity.R;
import com.example.myactivity.activities.calendar.CalendarActivity;
import com.example.myactivity.activities.day.DayActivity;
import com.example.myactivity.activities.projects.ProjectsActivity;
import com.example.myactivity.activities.settime.SetTimeActivity;
import com.example.myactivity.stuff.Utils;

public class MainMenuActivity extends AppCompatActivity {
    private boolean firstEntranceOfToday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        int todayDate = Integer.parseInt(Utils.getToday());
        int lastDate = Integer.parseInt(Utils.getLastEntranceDate(this));

        if (todayDate != lastDate) {
//            Toast.makeText(this, "base", Toast.LENGTH_SHORT).show();
            firstEntranceOfToday = true;
        }
        Utils.updateLastEntranceDate(this);

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
        if (firstEntranceOfToday && !Utils.ARE_TIME_SET) {
            Intent intent = new Intent(this, SetTimeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DayActivity.class);
            intent.putExtra("date", Utils.getToday());
            startActivity(intent);
        }
    }
}