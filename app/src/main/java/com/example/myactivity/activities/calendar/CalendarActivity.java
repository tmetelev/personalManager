package com.example.myactivity.activities.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myactivity.R;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle("Calendar");
    }
}