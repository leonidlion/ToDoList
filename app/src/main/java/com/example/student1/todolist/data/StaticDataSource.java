package com.example.student1.todolist.data;


import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.example.student1.todolist.Task;

import java.util.ArrayList;

public class StaticDataSource implements IDataSource {
    private static final ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    @Override
    public boolean createTask(@NonNull Task task) {
        return tasks.add(task);
    }

    @Override
    public boolean updateTask(@NonNull Task task, @IntRange(from = 0, to = Integer.MAX_VALUE) int index) {
        boolean result = false;
        if (index >= 0 && index < tasks.size()){
            tasks.set(index, task);
            result = true;
        }
        return result;
    }
}
