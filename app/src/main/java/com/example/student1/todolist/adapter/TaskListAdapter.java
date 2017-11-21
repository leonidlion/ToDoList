package com.example.student1.todolist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.student1.todolist.R;
import com.example.student1.todolist.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListHolder> {
    private static final int NORMAL_TASK = 0;
    private static final int EXPIRED_TASK = 1;

    private List<Task> tasks;

    public TaskListAdapter(@NonNull ArrayList<Task> tasks){
        super();
        this.tasks = tasks;
    }

    @Override
    public TaskListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int styleResId;
        switch (viewType){
            case NORMAL_TASK:
                styleResId = R.style.TaskItemColor;
                break;
            case EXPIRED_TASK:
                styleResId = R.style.TaskItemColor_Expired;
                break;
            default: styleResId = R.style.TaskItemColor;
        }
        Context context = new ContextThemeWrapper(parent.getContext(), styleResId);
        return new TaskListHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskListHolder holder, int position) {
        holder.onBind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return tasks.get(position).isExpired() ? EXPIRED_TASK : NORMAL_TASK;
    }

}
