package com.example.myactivity.misc;

import android.content.Context;

import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME_PROJECTS = "projects.json";
    private static final String FILE_NAME_LAST_ENTRANCE = "basicinfo.json";

    public static boolean exportToJSON(Context context, List<Project> dataList) {
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

    public static boolean exportToJSON(Context context, List<Task> dataList, String fileName) {
        Gson gson = new Gson();
        DataItemsTasks dataItems = new DataItemsTasks();
        dataItems.setTasks(dataList);
        String jsonString = gson.toJson(dataItems);
        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean exportToJSON(Context context, String dat) {
        List<String> dataList = new ArrayList<>();
        dataList.add(dat);
        Gson gson = new Gson();
        DataItemsStrings dataItems = new DataItemsStrings();
        dataItems.setStrings(dataList);
        String jsonString = gson.toJson(dataItems);
        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME_LAST_ENTRANCE, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Project> importFromJSON(Context context) {
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

    public static List<Task> importFromJSON(Context context, String fileName) {
        try(FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItemsTasks dataItems = gson.fromJson(streamReader, DataItemsTasks.class);
            return dataItems.getTasks();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<String> importFromJSON(Context context, int empty) {
        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME_LAST_ENTRANCE);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItemsStrings dataItems = gson.fromJson(streamReader, DataItemsStrings.class);
            return dataItems.getStrings();
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

    private static class DataItemsTasks {
        private List<Task> tasks;

        List<Task> getTasks() {
            return tasks;
        }
        void setTasks(List<Task> tasks) {
            this.tasks = tasks;
        }
    }

    private static class DataItemsStrings {
        private List<String> strings;

        List<String> getStrings() {
            return strings;
        }
        void setStrings(List<String> strings) {
            this.strings = strings;
        }
    }
}
