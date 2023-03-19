package com.example.myactivity.activities.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myactivity.R;
import com.example.myactivity.stuff.Utils;
import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private int[] valueOfDays = {0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int VALUE_OF_SHOWING_DAYS = 31;
    private static int VALUE_OF_PREVIOUS_DAYS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle("Calendar");

        List<Project> projects = Utils.getProjects(this);
        List<String> dates = getDates();
        List<String> trueDates = new ArrayList<>();
        List<List<List<Task>>> data = new ArrayList<>();
        for (String date : dates) {
            List<List<Task>> buf = new ArrayList<>();
            for (Project project : projects) {
                List<Task> t = Utils.getTasksOfThisDay(this, project, date);
                if (t.size() != 0)
                    buf.add(t);
            }
            if (buf.size() != 0) {
                trueDates.add(date);
                data.add(buf);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.c_recycler);
        CalendarRecyclerAdapter adapter = new CalendarRecyclerAdapter(data, trueDates, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    List<String> getDates() {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        String today = df.format(Calendar.getInstance().getTime());
        List<String> res = new ArrayList<>();
//        res.add(today);
        int day = Integer.parseInt(today.substring(4, 6));
        int month = Integer.parseInt(today.substring(2, 4));
        int year = Integer.parseInt(today.substring(0, 2));

        day -= VALUE_OF_PREVIOUS_DAYS;
        if (day < 1) {
            month--;
            day += valueOfDays[month];
        }
        for (int i = 0; i <= VALUE_OF_SHOWING_DAYS - 1; i++) {
            day++;
            if (day > valueOfDays[month]) {
                day = 1;
                month++;
            }
            if (month > 12) {
                month = 1;
                year++;
            }
            String sDay = Integer.toString(day);
            String sMonth = Integer.toString(month);
            if (sMonth.length() == 1)
                sMonth = "0" + sMonth;
            if (sDay.length() == 1)
                sDay = "0" + sDay;
            String ad = Integer.toString(year) + sMonth + sDay;
            res.add(ad);
        }
        return res;
    }
}