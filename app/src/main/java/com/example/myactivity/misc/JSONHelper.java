package com.example.myactivity.misc;

import android.content.Context;

import com.example.myactivity.structures.Project;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME_PROJECTS = "projects.json";

    public static boolean exportProjectsToJSON(Context context, List<Project> dataList) {

        Gson gson = new Gson();
        DataItemsProjects dataItems = new DataItemsProjects();
        dataItems.setProjects(dataList);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME_PROJECTS, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Project> importProjectsFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME_PROJECTS);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItemsProjects dataItems = gson.fromJson(streamReader, DataItemsProjects.class);
            return dataItems.getProjects();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    private static class DataItemsProjects {
        private List<Project> projects;

        List<Project> getProjects() {
            return projects;
        }
        void setProjects(List<Project> projects) {
            this.projects = projects;
        }
    }
}
