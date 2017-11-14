package com.example.student1.todolist.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.student1.todolist.Task;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefDataSource implements IDataSource {
    private static final String PREF_NAME = "SharedPrefDataSource";
    private static final String KEY_TASK = "KEY_TASK";

    private SharedPreferences preferences;
    private GsonBuilder gsonBuilder;
    private Type taskListType;
    private ArrayList<Task> taskList;

    public SharedPrefDataSource(Context context){
        if (context != null) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            gsonBuilder = new GsonBuilder();
            taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            if (!isTaskListEmpty()){
                taskList = convertJsonToList(preferences.getString(KEY_TASK, ""));
            } else {
                taskList = new ArrayList<>();
            }
        }
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    @Override
    public boolean createTask(@NonNull Task task) {
        boolean result;
        result = taskList.add(task);
        preferences.edit().putString(KEY_TASK, convertTaskListToJson(taskList)).apply();
        return result;
    }

    @Override
    public boolean updateTask(@NonNull Task task, @IntRange(from = 0, to = Integer.MAX_VALUE) int index) {
        boolean result = false;

        if (index >= 0 && index < taskList.size()) {
            taskList.set(index, task);
            preferences.edit().putString(KEY_TASK, convertTaskListToJson(taskList)).apply();
            result = true;
        }
        return result;
    }

    private String convertTaskListToJson(List<Task> task){
        return gsonBuilder.create().toJson(task, taskListType);
    }

    private ArrayList<Task> convertJsonToList(String json){
        return gsonBuilder.create().fromJson(json, taskListType);
    }

    private boolean isTaskListEmpty(){
        return TextUtils.isEmpty(preferences.getString(KEY_TASK, ""));
    }

}
