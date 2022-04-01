package com.example.myactivity.structures;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private int id;
    private String name;
    private String date;
    private String time;
    private boolean status;
    private String comment;
    private String tag;

    public Task() {

    }

    public Task(String name, String date, String tag) {
        this.name = name;
        this.date = date;
        this.time = "0000";
        this.tag = tag;
        this.status = false;
    }

    public String toString() {
        return Integer.toString(id) + " " + name + " " + date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date.substring(0, 2) + "." + date.substring(2, 4) + "." + date.substring(4, 6);
    }

    public String getTime() {
        return time.substring(0, 2) + ":" + time.substring(2, 4);
    }

    public boolean getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTag() {
        return tag;
    }

    public static List<Task> sortByDate(List<Task> tasks0) {
        List<Task> res = new ArrayList<>();

        return res;
    }
}
