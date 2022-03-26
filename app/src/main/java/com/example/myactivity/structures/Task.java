package com.example.myactivity.structures;

public class Task {

    private int id;
    private String name;
    private String date;

    public Task() {

    }

    public Task(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
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
}
