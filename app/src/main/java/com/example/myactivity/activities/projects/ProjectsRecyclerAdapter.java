package com.example.myactivity.activities.projects;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myactivity.R;
import com.example.myactivity.misc.JSONHelper;
import com.example.myactivity.structures.Project;

import java.util.List;

public class ProjectsRecyclerAdapter extends RecyclerView.Adapter<ProjectsRecyclerAdapter.MyViewHolder> {
    private List<Project> projects;
    private Context context;

    public ProjectsRecyclerAdapter(List<Project> projects, Context context) {
        this.projects = projects;
        this.context = context;
    }

    public void updateProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projects_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameView.setText(projects.get(position).getName());
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete project?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                projects.remove(position);
                                boolean result = JSONHelper.exportToJSON(context, projects);
                                if(result){
//                                  Toast.makeText(context, "Данные сохранены", Toast.LENGTH_LONG).show();
                                }
                                else{
//                                   Toast.makeText(context, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
                                }
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

            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView delButton;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.pr_item_name);
            delButton = (TextView) itemView.findViewById(R.id.pr_item_del_but);
            cardView = (CardView) itemView.findViewById(R.id.pr_item_card);
        }
    }
}
