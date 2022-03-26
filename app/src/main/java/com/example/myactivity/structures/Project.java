package com.example.myactivity.structures;

public class Project {

    private String name;
    private String dataFileName;

    public Project() {}

    public Project(String name){
        this.name = name;
        this.dataFileName = name + ".json";
    }

    public String getName() {
        return name;
    }

    public String getDataFileName() {
        return dataFileName;
    }
}
