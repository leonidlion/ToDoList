package com.example.student1.todolist;


public abstract class TaskObject {

    private String description;
    private TaskStatus status;

    public enum TaskStatus {
        NEW,
        DONE
    }

    public TaskObject() {
        status = TaskStatus.NEW;
    }

    public boolean isDone(){
        return status == TaskStatus.DONE;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
