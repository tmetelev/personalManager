package com.example.myactivity.activities.day;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myactivity.R;
import com.example.myactivity.activities.taskinfo.TaskInfoActivity;
import com.example.myactivity.misc.JSONHelper;
import com.example.myactivity.structures.Project;
import com.example.myactivity.structures.Task;

import java.util.List;

public class DayRecyclerAdapter extends RecyclerView.Adapter<DayRecyclerAdapter.MyViewHolder> {
    private List<Task> tasks;
    private Context context;

    public DayRecyclerAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameView.setText(tasks.get(position).getName());
        holder.timeView.setText(tasks.get(position).getTime());
        holder.aSwitch.setChecked(tasks.get(position).getStatus());
        holder.tagView.setText(tasks.get(position).getTag());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskInfoActivity.class);
                intent.putExtra("name", tasks.get(position).getTag() + ".json");
                intent.putExtra("pos", tasks.get(position).getId());
                context.startActivity(intent);
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
        private Switch aSwitch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.day_item_name);
            cardView = (CardView) itemView.findViewById(R.id.day_item_card);
            timeView = (TextView) itemView.findViewById(R.id.day_item_time);
            aSwitch = (Switch) itemView.findViewById(R.id.day_switch);
            tagView = (TextView) itemView.findViewById(R.id.day_item_tag);
        }
    }
}
