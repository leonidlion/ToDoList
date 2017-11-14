package com.example.student1.todolist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.student1.todolist.R;
import com.example.student1.todolist.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListHolder> {
    private List<Task> tasks;

    public TaskListAdapter(@NonNull ArrayList<Task> tasks){
        super();
        this.tasks = tasks;
    }

    @Override
    public TaskListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskListHolder holder, int position) {
        holder.onBind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
