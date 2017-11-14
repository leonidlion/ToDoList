package com.example.student1.todolist.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.student1.todolist.ActivityRequest;
import com.example.student1.todolist.BundleKey;
import com.example.student1.todolist.R;
import com.example.student1.todolist.Task;
import com.example.student1.todolist.adapter.TaskListAdapter;
import com.example.student1.todolist.data.IDataSource;
import com.example.student1.todolist.data.SharedPrefDataSource;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton createTaskButton;
    private IDataSource dataSource;
    private RecyclerView taskRecycler;
    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCreateTaskButton();

        dataSource = new SharedPrefDataSource(this);

        initTaskRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int size = dataSource.getTaskList().size();
        Toast.makeText(this, String.format(Locale.getDefault(), "%d task%s", size, size > 0 ?
                "s" :
                ""), Toast.LENGTH_SHORT).show();
    }

    private void initCreateTaskButton(){
        createTaskButton = (FloatingActionButton) findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                intent.putExtra(BundleKey.TASK.name(), task);
                startActivityForResult(intent, ActivityRequest.CREATE_TASK.ordinal());
            }
        });
    }

    private void initTaskRecycler(){
        taskRecycler = (RecyclerView) findViewById(R.id.taskRecycler);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        taskListAdapter = new TaskListAdapter(dataSource.getTaskList());
        taskRecycler.setAdapter(taskListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (ActivityRequest.values()[requestCode]){
            case CREATE_TASK:
                if (resultCode == Activity.RESULT_OK){
                    Task task = data.getParcelableExtra(BundleKey.TASK.name());
                    if(task != null) {
                        dataSource.createTask(task);
                        taskListAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}
