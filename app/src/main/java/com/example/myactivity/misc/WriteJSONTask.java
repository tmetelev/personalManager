package com.example.myactivity.misc;

import android.util.JsonWriter;

import com.example.myactivity.structures.Task;
import java.io.IOException;
import java.io.Writer;

public class WriteJSONTask {


    public static void writeJsonStream(Writer output, Task company ) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(output);

        jsonWriter.beginObject();// begin root

        jsonWriter.name("id").value(company.getId());
        jsonWriter.name("name").value(company.getName());
        jsonWriter.name("date").value("12.23.45");

        // end root
        jsonWriter.endObject();
    }
}