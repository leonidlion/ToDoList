package com.example.student1.todolist.data;


import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.example.student1.todolist.Task;

import java.util.ArrayList;

public interface IDataSource {
    ArrayList<Task> getTaskList();
    boolean createTask(@NonNull Task task);
    boolean updateTask(@NonNull Task task, @IntRange(from = 0, to = Integer.MAX_VALUE) int index);
}
