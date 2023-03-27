package com.example.myactivity.activities.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myactivity.R;
import com.example.myactivity.structures.Task;

import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<List<List<Task>>> data;
    private List<String> dates;

    public CalendarRecyclerAdapter(List<List<List<Task>>> data, List<String> dates, Context context) {
        this.context = context;
        this.data = data;
        this.dates = dates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String date = dates.get(position);
        if (date.length() > 6) {
            date = dateToFormat(date) + " - Today";
        } else {
            date = dateToFormat(date);
        }
        holder.dateView.setText(date);
        holder.dateView.setTextSize(22);
        String res = "";
        List<List<Task>> dayData = data.get(position);
        for (List<Task> dat : dayData) {
            res += dat.get(0).getTag() + "\n";
            for (Task task : dat) {
                boolean stat = task.getStatus();
                String status = "-";
                if (stat) {
                    status = "o";
                }
                res += "  " + status + " " + task.getName() + "\n";
            }
        }
        holder.dataView.setText(res);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    private String dateToFormat(String date) {
        return date.substring(4, 6) + "." + date.substring(2, 4) + "." + date.substring(0, 2);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView dateView;
        private TextView dataView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.c_item_date);
            dataView = (TextView) itemView.findViewById(R.id.c_data);
        }
    }
}
