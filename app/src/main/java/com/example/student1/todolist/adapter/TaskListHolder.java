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

    private void initViews(){
        taskName = itemView.findViewById(R.id.task_name);
        taskDescription = itemView.findViewById(R.id.task_desc);
    }

    void onBind(Task task){
/*        if (Build.VERSION.SDK_INT < 23) {
            taskName.setTextAppearance(itemView.getContext(), task.isExpired() ? R.style.TaskItemColor_Expired : R.style.TaskItemColor);
        } else {
            taskName.setTextAppearance(task.isExpired() ? R.style.TaskItemColor_Expired : R.style.TaskItemColor);
        }*/
        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
    }


}
