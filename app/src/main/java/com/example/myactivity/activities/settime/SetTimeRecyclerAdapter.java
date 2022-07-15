package com.example.myactivity.activities.settime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

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

    public void update(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameView.setText(tasks.get(position).getName());
        holder.timeView.setText(tasks.get(position).getTime());
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.set_time_name);
            cardView = (CardView) itemView.findViewById(R.id.set_time_card);
            timeView = (TextView) itemView.findViewById(R.id.set_time_time);
            tagView = (TextView) itemView.findViewById(R.id.set_time_item_tag);
        }
    }
}
