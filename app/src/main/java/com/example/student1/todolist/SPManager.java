package com.example.student1.todolist;


import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {
    private static final String KEY_LAYOUT_MANAGER = "KEY_LAYOUT_MANAGER";
    private static final String PREF_NAME = "SPManager";
    private SharedPreferences preferences;

    public SPManager(Context context){
        if (context != null)
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public void setLayoutManager(LayoutManagers manager){
        preferences.edit().putString(KEY_LAYOUT_MANAGER, manager.name()).apply();
    }

    public LayoutManagers getLayoutManager(){
        return LayoutManagers
                .valueOf(preferences.getString(KEY_LAYOUT_MANAGER, LayoutManagers.LINEAR.name()));
    }
}
