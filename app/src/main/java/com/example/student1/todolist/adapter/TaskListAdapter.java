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
    private static final int NORMAL_TASK = 0;
    private static final int EXPIRED_TASK = 1;

    private List<Task> tasks;

    public TaskListAdapter(@NonNull ArrayList<Task> tasks){
        super();
        this.tasks = tasks;
    }

    @Override
    public TaskListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = new ContextThemeWrapper(parent.getContext(), R.style.TaskItemColor_Expired);
        switch (viewType){
            case NORMAL_TASK:
                // change ll bg color and text appearance

                break;
            case EXPIRED_TASK:

                break;
        }
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

    @Override
    public int getItemViewType(int position) {
        return tasks.get(position).isExpired() ? EXPIRED_TASK : NORMAL_TASK;
    }

}
