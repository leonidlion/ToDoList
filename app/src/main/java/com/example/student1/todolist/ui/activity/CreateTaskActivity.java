package com.example.student1.todolist.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student1.todolist.BundleKey;
import com.example.student1.todolist.R;
import com.example.student1.todolist.Task;
import com.example.student1.todolist.dialogs.DatePickerFragment;

import java.util.Date;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener{
    private Task task;
    private TextInputLayout nameWrapper;
    private EditText nameEditText;
    private TextInputLayout descriptionWrapper;
    private EditText descriptionEditText;
    private TextView dateTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null &&
                bundle.containsKey(BundleKey.TASK.name())){
            task = bundle.getParcelable(BundleKey.TASK.name());
            initUI();
            setData();
        }else {
            Toast.makeText(getApplicationContext(), "Task not found", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    private void initUI(){
        nameWrapper = (TextInputLayout) findViewById(R.id.nameWrapper);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descriptionWrapper = (TextInputLayout) findViewById(R.id.descriptionWrapper);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
    }

    private void setData(){
        nameEditText.setText(task.getName());
        descriptionEditText.setText(task.getDescription());
        dateTextView.setText(task.getExpireDateString());
    }

    private void fillData(){
        task.setName(nameEditText.getText().toString());
        task.setDescription(descriptionEditText.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_task_save:
                saveTask();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveTask(){
        fillData();
        Intent intent = new Intent();
        intent.putExtra(BundleKey.TASK.name(), task);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSelected(Date date) {
        task.setExpireDate(date);
        dateTextView.setText(task.getExpireDateString());
    }
}
