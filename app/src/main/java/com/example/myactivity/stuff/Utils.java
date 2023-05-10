package com.example.myactivity.stuff;

import android.content.Context;

import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Utils {
    public static boolean ARE_TIME_SET = false;

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

    public static List<Project> getProjects(Context context) {
        List<Project> projects = JSONHelper.importFromJSON(context);
        if(projects!=null){
//            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
            return projects;
        }
        else{
//            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public static String getToday() {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        return df.format(Calendar.getInstance().getTime());
    }

    public static String getTodayInFormat() {
        DateFormat df = new SimpleDateFormat("ddMMyy");
        return df.format(Calendar.getInstance().getTime());
    }

    public static String getLastEntranceDate(Context context) {
        return JSONHelper.importFromJSON(context, 0).get(0);
    }

    public static void updateLastEntranceDate(Context context) {
        JSONHelper.exportToJSON(context, getToday());
    }

    public static List<Task> sortByTime(List<Task> tasks) {
        List<Task> tasks0 = tasks;
        List<Task> res = new ArrayList<>();
        for (int i = tasks0.size() - 1; i >= 0; i--) {
            int mini = 0;
            int min = 3000;
            for (int k = 0; k <= i; k++) {
                if (tasks0.get(k).getIntTime() < min) {
                    mini = k;
                    min = tasks0.get(k).getIntTime();
                }
            }
            res.add(tasks0.get(mini));
            tasks0.remove(mini);
        }
        return res;
    }

    public static List<Task> sortByDate(List<Task> tasks) {
        List<Task> tasks0 = tasks;
        List<Task> res = new ArrayList<>();
        for (int i = tasks0.size() - 1; i >= 0; i--) {
            int mini = 0;
            int min = 300000;
            for (int k = 0; k <= i; k++) {
                if (tasks0.get(k).getDateInFormat() < min) {
                    mini = k;
                    min = tasks0.get(k).getDateInFormat();
                }
            }
            res.add(tasks0.get(mini));
            tasks0.remove(mini);
        }
        return res;
    }
}
