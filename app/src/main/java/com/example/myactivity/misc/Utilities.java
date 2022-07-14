package com.example.myactivity.misc;

import android.content.Context;

import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static List<Task> getTasksOfThisDay(Context context, Project project, String date) {
        int d = Integer.parseInt(date);
        String dataFileName = project.getDataFileName();
        List<Task> tasks0 = JSONHelper.importFromJSON(context, dataFileName);
        List<Task> res = new ArrayList<>();
        for (int i = 0; i < tasks0.size(); i++) {
            int s = tasks0.get(i).getDateInFormat();
            if (d == s) {
                res.add(tasks0.get(i));
            }
        }
        return res;
    }
}
