package com.example.student1.todolist.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.student1.todolist.ActivityRequest;
import com.example.student1.todolist.BundleKey;
import com.example.student1.todolist.GridLayoutAutoFitManager;
import com.example.student1.todolist.decorators.HorizontalDivider;
import com.example.student1.todolist.LayoutManagers;
import com.example.student1.todolist.R;
import com.example.student1.todolist.SPManager;
import com.example.student1.todolist.decorators.SpacesItemDecoration;
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
    private LayoutManagers currentLayout;
    private SPManager sharedPrefManager;
    private SpacesItemDecoration gridSpacesDecorator;
    private HorizontalDivider horizontalDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCreateTaskButton();

        dataSource = new SharedPrefDataSource(this);
        sharedPrefManager = new SPManager(this);

        initGridItemSpaces();

        initLinearDividers();

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
        currentLayout = sharedPrefManager.getLayoutManager();
        taskRecycler = (RecyclerView) findViewById(R.id.taskRecycler);
        initRecyclerLayout();
        taskListAdapter = new TaskListAdapter(dataSource.getTaskList());
        taskRecycler.setAdapter(taskListAdapter);
    }

    private void initGridItemSpaces(){
        gridSpacesDecorator = SpacesItemDecoration.newBuilder(this)
                .setBottomInDp(8)
                .build();
    }

    private void initLinearDividers(){
        horizontalDivider = new HorizontalDivider(this, R.drawable.horizontal_divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (currentLayout == LayoutManagers.GRID ||
                currentLayout == LayoutManagers.GRID_AUTO_FIT){
            MenuItem gridItem = menu.findItem(R.id.menu_grid_layout);
            gridItem.setTitle(currentLayout == LayoutManagers.GRID ?
                    R.string.grid_layout :
                    R.string.grid_auto_fit_layout);
            gridItem.setIcon(currentLayout == LayoutManagers.GRID ?
                    R.drawable.ic_grid_layout :
                    R.drawable.ic_grid_autofit);
            gridItem.setChecked(currentLayout != LayoutManagers.GRID);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_linear_layout:
                currentLayout = LayoutManagers.LINEAR;
                break;
            case R.id.menu_grid_layout:
                if (item.isChecked()){
                    currentLayout = LayoutManagers.GRID;
                    item.setIcon(R.drawable.ic_grid_layout);
                    item.setTitle(R.string.grid_layout);
                }else {
                    currentLayout = LayoutManagers.GRID_AUTO_FIT;
                    item.setIcon(R.drawable.ic_grid_autofit);
                    item.setTitle(R.string.grid_auto_fit_layout);
                }
                item.setChecked(!item.isChecked());
                break;
            case R.id.menu_staggered_layout:
                currentLayout = LayoutManagers.STAGGERED;
                break;
            default: return super.onOptionsItemSelected(item);
        }
        sharedPrefManager.setLayoutManager(currentLayout);
        initRecyclerLayout();
        return true;
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

    private void initRecyclerLayout(){
        int columnCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        taskRecycler.removeItemDecoration(gridSpacesDecorator);
        taskRecycler.removeItemDecoration(horizontalDivider);
        switch (currentLayout){
            case LINEAR:
                taskRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                taskRecycler.addItemDecoration(horizontalDivider);
                break;
            case GRID:
                taskRecycler.setLayoutManager(new GridLayoutManager(this, columnCount));
                taskRecycler.addItemDecoration(gridSpacesDecorator);
                break;
            case GRID_AUTO_FIT:
                taskRecycler.setLayoutManager(new GridLayoutAutoFitManager(this, 160));
                break;
            case STAGGERED:
                taskRecycler.setLayoutManager(new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
    }
}
