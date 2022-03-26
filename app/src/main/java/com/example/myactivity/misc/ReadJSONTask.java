package com.example.myactivity.misc;

import android.content.Context;

import com.example.myactivity.R;
import com.example.myactivity.structures.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ReadJSONTask {

    // Read the company.json file and convert it to a java object.
    public static Task readTaskJSONFile(Context context) throws IOException,JSONException {

        // Read content of company.json
        String jsonText = readText(context, R.raw.data);


        JSONObject jsonRoot = new JSONObject(jsonText);


        int id= jsonRoot.getInt("id");
        String name = jsonRoot.getString("name");
        String date = jsonRoot.getString("date");

        Task task = new Task(id, name, date);
        return task;
    }



    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

}
