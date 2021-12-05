package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amplifyframework.datastore.generated.model.Task;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks = new ArrayList<>();

    public TaskAdapter(List<Task> tasks) {

        this.tasks = tasks;
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public Task task;
        public View itemView;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = tasks.get(position);
        TextView title = holder.itemView.findViewById(R.id.titleFragment);
        TextView desc = holder.itemView.findViewById(R.id.descFragment);
        TextView state = holder.itemView.findViewById(R.id.stateFragment);
        TextView team = holder.itemView.findViewById(R.id.teamFragment);
        title.setText(holder.task.getTitle());
        desc.setText(holder.task.getDesc());
        state.setText(holder.task.getState());
        team.setText(holder.task.getTeamName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("tasks", Context.MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("title", holder.task.getTitle());
                editor.putString("desc", holder.task.getDesc());
                editor.putString("state", holder.task.getState());
                editor.putString("file",holder.task.getFile());
                editor.putString("location", holder.task.getLocation());
                editor.apply();
                Intent intent = new Intent(view.getContext(), TaskDetail.class);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

//    static class TaskDiff extends DiffUtil.ItemCallback<Task> {
//
//        @Override
//        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
//            return oldItem == newItem;
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
//            return oldItem.getUid() == newItem.getUid();
//        }
//    }

}
