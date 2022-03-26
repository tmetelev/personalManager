package com.example.myactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myactivity.misc.ReadJSONTask;
import com.example.myactivity.misc.WriteJSONTask;
import com.example.myactivity.structures.Task;

import java.io.FileOutputStream;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity {

    private TextView outputText;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.outputText = (TextView)this.findViewById(R.id.textView);
        this.button = (Button) this.findViewById(R.id.button);
        this.button2 = (Button) this.findViewById(R.id.button2);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runEx(view);
            }
        });

        this.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runEx2(view);
            }
        });
    }

    public void runEx(View view){
        try {

            Task company = ReadJSONTask.readTaskJSONFile(this);

            outputText.setText(company.toString());
        } catch(Exception e)  {
            outputText.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void runEx2(View view) {
        try {
            StringWriter data = new StringWriter();

            Task company = new Task(33, "polsekundy", "polsekundy");


            WriteJSONTask.writeJsonStream(data, company);

            String jsonText = data.toString();

            try(FileOutputStream fileOutputStream =
                        this.openFileOutput("data.json", Context.MODE_PRIVATE)) {
                fileOutputStream.write(jsonText.getBytes());

                outputText.setText(jsonText);
            } catch (Exception e) {
                e.printStackTrace();
                outputText.setText(e.getMessage());
            }
        } catch(Exception e)  {
            outputText.setText(e.getMessage());
            e.printStackTrace();
        }
    }
}