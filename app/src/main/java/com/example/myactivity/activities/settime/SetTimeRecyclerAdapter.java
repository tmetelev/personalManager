package com.example.myactivity.activities.settime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myactivity.R;
import com.example.myactivity.activities.taskinfo.TaskInfoActivity;
import com.example.myactivity.structures.Task;
import com.example.myactivity.stuff.JSONHelper;

import java.util.List;

public class SetTimeRecyclerAdapter extends RecyclerView.Adapter<SetTimeRecyclerAdapter.MyViewHolder> {
    private List<Task> tasks;
    private Context context;

    public SetTimeRecyclerAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_time_item, parent, false);
        return new MyViewHolder(view);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    private TimePicker timePicker;
    private int hour = 12, minute = 0;

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameView.setText(tasks.get(position).getName());
        holder.timeView.setText(tasks.get(position).getTime());
        holder.tagView.setText(tasks.get(position).getTag());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater li = LayoutInflater.from(context);
                View windowView = li.inflate(R.layout.set_time_dialog, null);
                builder.setView(windowView);
                timePicker = windowView.findViewById(R.id.set_time_timePicker);
                timePicker.setHour(12);
                timePicker.setMinute(0);
                timePicker.setIs24HourView(true);
                builder
                        .setTitle("Set task time")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hour = timePicker.getHour();
                                minute = timePicker.getMinute();
                                String hourString = Integer.toString(hour);
                                if (hourString.length() == 1) {
                                    hourString = "0" + hourString;
                                }
                                String minuteString = Integer.toString(minute);
                                if (minuteString.length() == 1) {
                                    minuteString = "0" + minuteString;
                                }
                                Task bufTask = tasks.get(position);
                                bufTask.setTime(hourString + minuteString);
                                tasks.set(position,bufTask);
                                notifyDataSetChanged();
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private CardView cardView;
        private TextView timeView;
        private TextView tagView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.set_time_name);
            cardView = (CardView) itemView.findViewById(R.id.set_time_card);
            timeView = (TextView) itemView.findViewById(R.id.set_time_time);
            tagView = (TextView) itemView.findViewById(R.id.set_time_item_tag);
        }
    }
}
