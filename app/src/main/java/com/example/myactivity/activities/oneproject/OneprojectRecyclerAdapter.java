package com.example.myactivity.activities.oneproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myactivity.R;
import com.example.myactivity.activities.taskinfo.TaskInfoActivity;
import com.example.myactivity.stuff.JSONHelper;
import com.example.myactivity.structures.Task;
import com.example.myactivity.stuff.Utils;

import java.util.List;

public class OneprojectRecyclerAdapter extends RecyclerView.Adapter<OneprojectRecyclerAdapter.MyViewHolder> {
    private List<Task> tasks;
    private Context context;
    private String dataFileName;

    public OneprojectRecyclerAdapter(String dataFileName, Context context) {
        this.context = context;
        this.dataFileName = dataFileName;
    }

    public void update() {
        tasks = JSONHelper.importFromJSON(context, dataFileName);
        tasks = Utils.sortByDate(tasks);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.oneproject_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameView.setText(tasks.get(position).getName());
        holder.dateView.setText(tasks.get(position).getDate());
//        holder.timeView.setText(tasks.get(position).getTime());       // May be added in future
        holder.timeView.setText("");
        boolean stat = tasks.get(position).getStatus();
        if (stat) {
            holder.statusView.setText("Y");
            holder.statusView.setTextColor(Color.parseColor("#00FF00"));
        } else {
            holder.statusView.setText("X");
            holder.statusView.setTextColor(Color.parseColor("#FF0000"));
        }
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete task?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tasks.remove(position);
                                boolean result = JSONHelper.exportToJSON(context, tasks, dataFileName);
//                                if (result) {
//                                    Toast.makeText(context, "Данные сохранены", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(context, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
//                                }
                                update();
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskInfoActivity.class);
                intent.putExtra("name", dataFileName);
                intent.putExtra("pos", position);
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
        private TextView delButton;
        private CardView cardView;
        private TextView timeView;
        private TextView dateView;
        private TextView statusView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.op_item_name);
            delButton = (TextView) itemView.findViewById(R.id.op_item_del_button);
            cardView = (CardView) itemView.findViewById(R.id.op_item_card);
            timeView = (TextView) itemView.findViewById(R.id.op_item_time);
            dateView = (TextView) itemView.findViewById(R.id.op_item_date);
            statusView = (TextView) itemView.findViewById(R.id.op_item_status);
        }
    }
}
