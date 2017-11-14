package com.example.student1.todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.student1.todolist.R;
import com.example.student1.todolist.Task;


class TaskListHolder extends RecyclerView.ViewHolder {
    private TextView taskName;
    private TextView taskDescription;

    TaskListHolder(View itemView) {
        super(itemView);
        initViews();
    }

    void onBind(Task task){
        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
    }

    private void initViews(){
        taskName = itemView.findViewById(R.id.task_name);
        taskDescription = itemView.findViewById(R.id.task_desc);
    }
}
